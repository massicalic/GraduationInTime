
import numpy
from sklearn.neighbors import KNeighborsClassifier
import warnings

def c_area(b,h):
    area=b*h*numpy.pi
    model_knn = KNeighborsClassifier(n_neighbors=1)
    warnings.filterwarnings("ignore")
    return area
