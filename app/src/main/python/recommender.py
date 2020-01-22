

from utils import load_signatures_graduates, get_weighted_recommendations, get_similar_students


# differences between two students
def get_recommendations(sign_q, sign_x):
    # create a list of zeros
    ret_list = [0] * len(sign_q)
    for index, subject in enumerate(zip(sign_q, sign_x)):
        if subject[0] == 0 and subject[1] > 0:
            #fatto da sign_x ma non da sign_q
            ret_list[index] = 1
    return ret_list

def recommendation():
    signature_tmp = [1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    mat_sel = 600006

    dict_signatures_graduates, list_all_subject = load_signatures_graduates(grades_to_bool=True)
    distances = get_similar_students(signature_tmp, dict_signatures_graduates, mat_sel)
    list_recommendations, num_recommendations = get_weighted_recommendations(distances, signature_tmp,
                                                                             dict_signatures_graduates, list_all_subject)
    recommended_exam = []
    i = 0
    for weight,exam in list_recommendations:
        #print(weight, exam)
        recommended_exam.insert(i, str(weight) + exam)
        i += 1

    return recommended_exam

