import numpy as np

from scipy.stats import pearsonr
from scipy.spatial.distance import euclidean
from scipy.spatial.distance import cosine
from sklearn.preprocessing import StandardScaler

n=100
x1 = np.random.random_integers(0,10,(n,1))
x2 = np.random.random_integers(0,10,(n,1))
x3 = np.random.random_integers(0,10,(n,1))

tmp = pearsonr(x1, x2)
p12 = 1 - pearsonr(x1, x2)[0][0]
p13 = 1 - pearsonr(x1, x3)[0][0]
p23 = 1 - pearsonr(x2, x3)[0][0]

tmp1 = euclidean(x1, x2)
tmp1 = tmp1**2
tmp1 /=n
tmp1 /=2
d12 = (euclidean(x1, x2)**2) / (2*n)
d13 = (euclidean(x1, x3)**2) / (2*n)
d23 = (euclidean(x2, x3)**2) / (2*n)