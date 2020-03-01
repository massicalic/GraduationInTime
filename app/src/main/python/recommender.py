

from utils import load_signatures_graduates, get_similar_students, get_weighted_recommendations, load_months_graduates, \
    get_sorted_subjects, months_to_date

def recommendation(sign, mat, enroll):

    signature_tmp = sign
    mat_sel = mat

    dict_signatures_graduates, list_all_subject = load_signatures_graduates(grades_to_bool=False)
    sorted_distances = get_similar_students(signature_tmp, dict_signatures_graduates, mat_sel)
    list_recommendations, num_recommendations = get_weighted_recommendations(sorted_distances,
                                                                             signature_tmp,
                                                                             dict_signatures_graduates,
                                                                             list_all_subject,
                                                                             threshold_distance=10)

    print("** student %d compared with %d graduated students" % (mat_sel, num_recommendations))

    print("Recommended exams to do:")
    for weight, exam in list_recommendations:
        print("%.2f" % weight, exam)


    # prendo solo il primo studente più simile ma si possono prendere i primi N e per ognuno descrivere un percorso
    most_similar_matricole = sorted_distances[0][1]
    dict_months, list_all_subject = load_months_graduates()
    months_per_subject = dict_months[most_similar_matricole]
    sorted_dict = get_sorted_subjects(months_per_subject, list_all_subject)

    recommended_exam = []
    i = 0

    for k, v in sorted_dict.items():
        if v > -1:
            #print(k, "::", v, ', data: ', months_to_date(v, enroll))
            recommended_exam.insert(i, str(k) + "::" + str(months_to_date(v, enroll)))
            i += 1




    #recommended_exam = []
    #i = 0
    #for weight,exam in list_recommendations:
        #print(weight, exam)
        #recommended_exam.insert(i, str(weight) + exam)
        #i += 1

    return recommended_exam

