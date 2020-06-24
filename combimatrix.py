from itertools import product
def function(list):
	#a=2d array
	list.append(list)
	#all possible combinations are 
	b=list(product(*list))
	return b
	
