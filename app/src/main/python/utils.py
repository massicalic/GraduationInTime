import pandas as pd
import csv
from os.path import dirname, join
from com.chaquo.python import Python
from operator import add
import collections

SIGNATURES_GRADES_CSV = "data/graduate_signatures.csv"
SIGNATURES_MONTHS_CSV = "data/graduate_signatures_months.csv"

def abs_distance(q, s):
    """
    Variant of manhattan distance

    q is the query signature (list of 0 / 1)
    s is the signature of a graduate student

    Return the distance between the set of exams made by a query student and the exams of another student.
    """
    return sum(abs(x - y) for x, y in zip(q, s) if x > 0)

# differences between two students
def compare_two_students(sign_q, sign_x):
    # create a list of zeros
    ret_list = [0] * len(sign_q)
    for index, subject in enumerate(zip(sign_q, sign_x)):
        if subject[0] == 0 and subject[1] > 0:
            # esame fatto da sign_x ma non da sign_q
            ret_list[index] = 1
    return ret_list

# returns recommended exams to do, associated with a percentage
def get_weighted_recommendations(dist, signature, dict_signatures_grad, list_exams, threshold_distance=0):
    index = 0
    sum_list = [0] * len(list_exams)
    # correct possible minimum threshol errors
    if threshold_distance < dist[0][0]:
        threshold_distance = dist[index][0]
        print("threshold_distance =", threshold_distance)
    while index < len(dist) and dist[index][0] <= threshold_distance:
        mat_comp = dist[index][1]
        list_exams_to_do = compare_two_students(signature, dict_signatures_grad[mat_comp])
        # sum all recommendations
        sum_list = list(map(add, sum_list, list_exams_to_do))
        index += 1

    num_rec = index
    list_rec = []
    for i, p in enumerate(sum_list):
        if p > 0:
            list_rec.append((p / num_rec, list_exams[i]))
    list_rec.sort(reverse=True)
    return list_rec, num_rec

def get_signature_matricola_no_grades(matricola, df_career_loc, list_subjects):
    list_votes_per_subject = []
    matricola_selected = df_career_loc['Matricola'] == matricola
    df_career_matricola = df_career_loc[matricola_selected]
    for subject in list_subjects:  # scorro la lista delle materie
        # recupero esame fatto ed il voto
        df_subject_vote = df_career_matricola[df_career_matricola['des ad'] == subject][['des ad', 'Voto']]
        if len(df_subject_vote) > 0:
            list_votes_per_subject.append(1)  # fatto
        else:
            list_votes_per_subject.append(0)  # non fatto
    return list_votes_per_subject

def get_signature_matricola_grades(matricola, df_career_loc, list_subjects):
    list_votes_per_subject = []
    matricola_selected = df_career_loc['Matricola'] == matricola
    df_career_matricola = df_career_loc[matricola_selected]
    for subject in list_subjects:  # scorro la lista delle materie
        # recupero esame fatto ed il voto
        df_subject_vote = df_career_matricola[df_career_matricola['des ad'] == subject][['des ad', 'Voto']]
        if len(df_subject_vote) > 0:
            voto = df_subject_vote.iloc[0]['Voto']
            if math.isnan(voto) :
                voto = 30 # gli esami che sono solo APPROVATI li porto a 30(superato)
            list_votes_per_subject.append(int(voto))  # fatto
        else:
            list_votes_per_subject.append(0)  # non fatto
    return list_votes_per_subject

def remove_subjects_with_low_students(df, th=0.05):
    # number of not null in each column
    count = df.count()
    # number of nonzeros in each column
    count_non_zero = df.astype(bool).sum(axis=0)
    # iterating the columns
    list_column_to_be_removed = []
    for idx, col in enumerate(df.columns):
        percentage = count_non_zero[idx] / count[idx]
        #print(idx, col, percentage)
        if percentage < th:
            list_column_to_be_removed.append(col)
            print(percentage, col)
    #print(list_column_to_be_removed)
    # Delete multiple columns from the dataframe
    df2 = df.drop(list_column_to_be_removed, axis=1)
    return df2

def get_sorted_subjects(months_per_subject, list_all_subject):
    disordered_dic = {subject: months for months, subject in zip(months_per_subject, list_all_subject)}
    sorted_dict = collections.OrderedDict(sorted(disordered_dic.items(), key=lambda kv: kv[1]))

    return sorted_dict

def months_to_date(months, aa_immatricolazione):
    label_month = ['Gennaio', 'Gennaio', 'Febbraio', 'Febbraio', 'Giugno', 'Giugno', 'Giugno', 'Luglio', 'Luglio',
                   'Settembre', 'Settembre', 'Settembre']
    month = months % 12
    year = aa_immatricolazione + months // 12
    return label_month[month] + ', %d' % year

def get_months_matricola_grades(matricola, df_career, list_all_subject):
    list_months_per_subject = []
    # recupero la carriera dello studente
    df_career_matr = df_career[df_career['Matricola'] == eval(str(matricola))]
    anno_iscrizione = df_career_matr['Aa Iscr Id'].tolist()[0]
    # recupero la data in cui lo studente ha sostenuti gli esami
    for subject in list_all_subject:
        subject_date_sup = df_career_matr[df_career_matr['des ad'] == subject]['Data Sup']
        if not subject_date_sup.empty:
            timestamp = pd.to_datetime(subject_date_sup.tolist()[0])  # converto la data
            month = timestamp.month  # recupero il mese
            year = timestamp.year  # recupero l'anno
            # numero totale di mesi dopo l'immatricolazione in cui l'same è stato superato
            total_months = (year - anno_iscrizione) * 12 + month
            list_months_per_subject.append(int(total_months))  # fatto
        else:
            list_months_per_subject.append(-1)  # non fatto
        # print(matricola, list_months_per_subject[-1], subject)
    return list_months_per_subject


def export_careers_as_signatures():
    df_career = pd.read_csv(join(dirname(__file__), 'career_data.csv'))
    df_graduates = pd.read_excel(join(dirname(__file__), 'student_data.xlsx'))
    df_graduates = df_graduates[df_graduates['Data Laurea'].notnull()]

    # recupero la lista di tutti gli esami
    list_all_subject = df_career['des ad'].drop_duplicates().tolist()

    # recupero la lista di tutte le matricole dei laureati
    matricole = df_graduates['Matricola'].unique().tolist()

    # creo un dizionario con key=matricola e value=signature
    d = {m: get_signature_matricola_grades(m, df_career, list_all_subject) for m in matricole}

    # export signatures to file
    df_signaures = pd.DataFrame.from_dict(d, orient='index', columns=list_all_subject)
    # remove all subjects with very low number of students
    df_signaures = remove_subjects_with_low_students(df_signaures, th=0.05)

    df_signaures.to_csv(SIGNATURES_GRADES_CSV)
    print("Table saved as ", SIGNATURES_GRADES_CSV)

    # crea un file CSV contenete il numero di mesi trascorsi dall'anno di immatricolazione fino alla data dell'esame
def export_careers_as_months():
    dict_signatures_graduates, list_all_subject = load_signatures_graduates(grades_to_bool=False)
    df_career = pd.read_csv(join(dirname(__file__), 'career_data.csv'))

    # creo un dizionario con key=matricola e value=months_signature
    d = {m: get_months_matricola_grades(m, df_career, list_all_subject) for m in dict_signatures_graduates}
    # export signatures to file
    df_signaures = pd.DataFrame.from_dict(d, orient='index', columns=list_all_subject)
    df_signaures.to_csv(SIGNATURES_MONTHS_CSV)
    print("Table saved as ", SIGNATURES_MONTHS_CSV)


def load_signatures_graduates(grades_to_bool=False):
    with open(join(dirname(__file__), 'graduate_signatures.csv')) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        list_all_subject = []
        dict_signatures = {}
        for row in csv_reader:
            if line_count == 0:
                list_all_subject = row[1:]
                print("%d columns" % len(list_all_subject))
                line_count += 1
            else:
                tmp_list = [int(i) for i in row[1:]]
                dict_signatures[int(row[0])] = tmp_list
                line_count += 1
        print('Loaded %d signatures.' % (line_count - 1))
        if grades_to_bool:
            # converts grades in 0/1
            dict_signatures = {x: [1 if (a > 0) else 0 for a in y] for (x, y) in dict_signatures.items()}

    return dict_signatures, list_all_subject

def load_months_graduates():
    with open(join(dirname(__file__), 'graduate_signatures_months.csv')) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        list_all_subject = []
        dict_months = {}
        for row in csv_reader:
            if line_count == 0:
                list_all_subject = row[1:]
                print("%d subjects" % len(list_all_subject))
                line_count += 1
            else:
                tmp_list = [int(i) for i in row[1:]]
                dict_months[int(row[0])] = tmp_list
                line_count += 1
        print('Loaded months for %d graduates matricole.' % (line_count - 1))

    return dict_months, list_all_subject

# get a similarity measure between a user and all the graduate students
def get_similar_students(signature_user, all_sign, mat_sel):
    distances = []
    for m in all_sign:
        if m != mat_sel:
            dist = abs_distance(signature_user, all_sign[m])
            distances.append((dist, m))
    distances.sort()
    return distances

if __name__ == '__main__':
    export_careers_as_months()
    # dict_signatures, list_all_subject = load_months_graduates()
    # for k, v in dict_signatures.items():
    #     print(k, v)

    export_careers_as_signatures()
    # print("created CSV file")
    # dict_signatures, list_all_subject = load_signatures_graduates(SIGNATURES_GRADES_CSV)
    # print(list_all_subject)
    # for k, v in dict_signatures.items():
    #     print(k, v)
