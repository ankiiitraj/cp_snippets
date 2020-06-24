from itertools import product
#a=2d array
a=[[1,2,3],[1,2,3]]
#all possible combinations are 
b=list(product(*a))
print(b)
