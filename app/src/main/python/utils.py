import pandas as pd
import csv
from os.path import dirname, join
from com.chaquo.python import Python
from operator import add


def abs_distance(q, s):
    """
    Variant of manhattan distance

    q is the query signature (list of 0 / 1)
    s is the signature of a graduate student

    Return the distance between the set of exams made by a query student and the exams of another student.
    """
    return sum(abs(x-y) for x, y in zip(q, s) if x==1)

# differences between two students
def compare_two_students(sign_q, sign_x):
    # create a list of zeros
    ret_list = [0] * len(sign_q)
    for index, subject in enumerate(zip(sign_q, sign_x)):
        if subject[0] == 0 and subject[1] > 0:
            # esame fatto da sign_x ma non da sign_q
            ret_list[index] = 1
    return ret_list

# return recommended exams to do associated with a percentage
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

def get_signature_matricola(matricola, df_career_loc, list_subjects):
    list_votes_per_subject = []
    matricola_selected = df_career_loc['Matricola'] == matricola
    df_career_matricola = df_career_loc[matricola_selected]
    for subject in list_subjects : #scorro la lista delle materie
        # recupero esame fatto ed il voto
        df_subject_vote = df_career_matricola[df_career_matricola['des ad'] == subject][['des ad', 'Voto']]
        if len(df_subject_vote) > 0 :
            list_votes_per_subject.append(1) # fatto
        else:
            list_votes_per_subject.append(0) # non fatto
    return list_votes_per_subject


def export_careers_as_signatures():
    df_career = pd.read_csv(join(dirname(__file__), 'career_data.csv'))
    df_graduates = pd.read_excel(join(dirname(__file__), 'student_data.xlsx'))
    df_graduates = df_graduates[df_graduates['Data Laurea'].notnull()]

    # recupero la lista di tutti gli esami
    list_all_subject = df_career['des ad'].drop_duplicates().tolist()

    # recupero la lista di tutte le matricole dei laureati
    matricole = df_graduates['Matricola'].unique().tolist()
    # creo un dizionario con key=matricola e value=signature
    d={m:get_signature_matricola(m, df_career, list_all_subject) for m in matricole}

    # export signatures to file
    df_signaures = pd.DataFrame.from_dict(d, orient = 'index' )
    files_dir = str(Python.getPlatform().getApplication().getFilesDir())
    df_signaures.to_csv(files_dir+'graduate_signatures.csv')


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

# get a similarity measure between a user and all the graduate students
def get_similar_students(signature_user, all_sign, mat_sel):
    distances = []
    for m in all_sign:
        if m != mat_sel:
            dist = abs_distance(signature_user, all_sign[m])
            distances.append((dist, m))
    distances.sort()
    return distances

def generate():
    dict_signatures, list_all_subject = load_signatures_graduates()
    print(list_all_subject)
    for k, v in dict_signatures.items():
        print (k, v)