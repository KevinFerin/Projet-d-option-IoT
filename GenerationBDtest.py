# -*- coding: utf-8 -*-
"""
Created on Wed Dec 19 10:24:35 2018

@author: Kevin
"""

import pandas as pd 
from datetime import datetime 
from datetime import timedelta
import numpy as np 


time = datetime.strptime('01/01/2018 00:00', '%d/%m/%Y %H:%M')

M=[]


for i in range (525600) : 
    d = np.random.normal(20,2.5)
    M.append([time,d])
    time= time+timedelta(minutes=1)
    i+=1

BDMinute = pd.DataFrame(data=M,columns =['time','value'])
BDMinute.to_csv(r"D:\Utilisateurs\Documents\MinesNantes\FI4\Projet D'option\BDminute.csv")
timeHeure = datetime.strptime('01/01/2018 01', '%d/%m/%Y %H')

H=[]
k=0
for i in range(8760) :
    mean = 0
    count=0
    while ((k<525600)and (M[k][0] < timeHeure)) : 
        mean+=M[k][1]
        count+=1
        k+=1
    H.append([timeHeure-timedelta(hours=1),mean/count])
    timeHeure=timeHeure+timedelta(hours=1)
    i+=1
    

BDHeure = pd.DataFrame(data=H,columns =['time','value'])
BDHeure.to_csv(r"D:\Utilisateurs\Documents\MinesNantes\FI4\Projet D'option\BDHeure.csv")

timeDay = datetime.strptime('02/01/2018', '%d/%m/%Y')

D=[]
k=0
for i in range(365) :
    mean = 0
    count=0
    while ((k<525600)and (M[k][0] <= timeDay)) : 
        mean+=M[k][1]
        count+=1
        k+=1
    D.append([timeDay-timedelta(days=1),mean/count])
    timeDay=timeDay+timedelta(days=1)
    i+=1
    

BDDay = pd.DataFrame(data=D,columns =['time','value'])
BDDay.to_csv(r"D:\Utilisateurs\Documents\MinesNantes\FI4\Projet D'option\BDDay.csv")