import pandas as pd

data = pd.read_csv("currencies.csv") 

i = 0
while i < len(data):
    query = "INSERT INTO tbl_Currency VALUES ('"+data['Code'][i]+"','"+data['Name'][i]+"',"+str(data['RateInEUR'][i])+", CURRENT_TIMESTAMP)"
    print(query)
    i += 1


