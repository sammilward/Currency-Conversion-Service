import pandas as pd

CodesAndRates = pd.read_csv("RatesOfAllCurrenciesToBaseEUR.csv") 
CodesAndNames = pd.read_csv("AllCurrencies.csv") 

i = 0
while i < len(CodesAndRates):
    Code = CodesAndRates['Code'][i]
    Rate = CodesAndRates['RateInEUR'][i]
    # print(Code)
    # print(Rate)
    Name = "NA"
    j = 0
    i += 1
    while j < len(CodesAndNames):
        Code2 = CodesAndNames['Code'][j]
        if (Code == Code2):
            Name = CodesAndNames['CurrencyName'][j]
            query = "INSERT INTO tbl_Currency VALUES ('"+Code+"','"+Name+"',"+str(Rate)+", CURRENT_TIMESTAMP)"
            print(query)
            break
        j += 1



