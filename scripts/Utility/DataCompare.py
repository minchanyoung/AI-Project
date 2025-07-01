import os
import pandas as pd
import json
from FileControl import export_csv_Part_Data

encoding = "utf-8"


# 해당 폴더 내 csv를 전부 읽어서 성별 나이 학력 직종으로 추적하고 나눠서 json으로 저장함
def trace_target_data(directory, compareCodeList, collectCodeList):
    # 해당 directory안에 파일이름들 읽음
    fileList = os.listdir(directory)
    # data[compareCodeList][List][collectCodeList]
    # dict<List, List<List>>

    # dictionary형로 저장 <str(성별 나이 학력 직종), list[list[회차, 나이, 월급]]
    dictionary = dict()
    
    # 파일마다 반복
    for fileIndex in range(len(fileList)):
        stringNum = f"{fileIndex+1}".zfill(2) # 2자리 숫자형태로 만듬
        df = pd.read_csv(f"{directory}/{fileList[fileIndex]}")
        
        print(f"{fileIndex} - {df.last_valid_index()}")
        for index in range(df.last_valid_index()):
            key = "" # 여기에 str(성별 나이 학력 직종) 형태로 담음
            # if(fileIndex == 19 and index == 4174):
            #     asd = 321
            for collect in compareCodeList:
                column = f"p{stringNum}{collect}"
                if(collect == "0107"): # 나이의 경우
                    col = df[column][index]
                    num = int(col) - fileIndex # 중요 이걸로 첫 관측된 나이 추적함
                    if num < 20: # 20세 이하는 거름
                        num = int(col)
                        
                    key += str(num).zfill(2)
                else:

                    num = int(df[column][index])
                    if(num < 0): # 간혹 -1들어가는거 처리함
                        num = 0
                    key += str(num)

            # if('2-1' in key):
            #     a = 123
            inValue = [] # 이거 안에 [회차, 나이, 월급]
            inValue.append("v"+f"{fileIndex+1}".zfill(2)) # 회차
            for collect in collectCodeList: # 나이 월급. 입력받은 리스트 사용함
                column = f"p{stringNum}{collect}"
                inValue.append(int(df[column][index]))

            outValue = None
            if(key in dictionary): # 이미 넣은게 있는경우. 추적중인 대상은 여기서 다뤄짐
                outValue = dictionary[key]

                if((inValue in outValue) == False): # 동명이인 처리용 (그냥 만나이라 착간한거 일수도 있지만 남겨둠)
                    outValue.append(inValue)
            else: # 새로 넣는 경우
                outValue = []
                outValue.append(inValue)
                dictionary[key] = outValue
    print(dictionary)
    
    dictionary["keys"] = [*dictionary.keys()] # 다 넣고 키값도 저장해둠

    with open("./resources/Preprocess/Datas.json","w", encoding="utf-8") as f:
        json.dump(dictionary, f, indent=2)

compareCodeList = ["0101", "0107", "0110", "0350"]
collectCodeList = ["0107", "1642"]

#성별,나이,학력,직군 순으로 합쳐진 str 분리
def keyListup(keyValue):
    dataList = []
    gender = keyValue[0]
    age = keyValue[1:3]
    edu = keyValue[3:4]
    job = keyValue[4:]

    dataList.append(gender)
    dataList.append(age)
    dataList.append(edu)
    dataList.append(job)
    
    # if(int(job) > 999):
    #     for i in dataList:
    #         print(i)
    return dataList

#성별,나이,학력,직군 분리 후 
def keyDecoder(keyValue):
    kList = keyListup(keyValue)
    gender = kList[0]
    age = kList[1]
    edu = kList[2]
    job = kList[3]

    if(gender == "1"):
        gender = "남자"
    else:
        gender = "여자"
    
    if(edu == "9"):
        edu = "박사"
    elif(edu == "8"):
        edu = "석사"
    elif(edu == "7"):
        edu = "학사"
    elif(edu == "6"):
        edu = "전문학사"
    elif(edu == "5"):
        edu = "고등학교"
    elif(edu == "4"):
        edu = "중학교"
    elif(edu == "3"):
        edu = "초등학교"
    elif(edu == "2"):
        edu = "무학"
    elif(edu == "1"):
        edu = "미취학"
    elif(edu == "0"):
        edu = "무응답"


    print(f"성별: {gender}")
    print(f"나이: {age}")
    print(f"최종학력: {edu}")
    print(f"직업 {job} - {jobDecoder(job)}")
    return kList
    
# 코드에 해당하는 직업 찾아냄
def jobDecoder(jobKey):
    jobWord = "판독불가"
    if(int(jobKey) < 0):
        print(jobWord)
        return
    # dir의 파일들 전부확인함 판본별로 번호가 다름 - 다만 5차 번호 선에서 끝나는듯함
    dir = "./resources/Preprocess/jobConvert"
    fileNames = os.listdir(dir)
    jobKey = int(jobKey)

    for f in fileNames:
        path = f"{dir}/{f}"
        df = pd.read_csv(path, encoding=encoding)  

        # df로 읽고 index에서 xxx형태의 코드랑 맞는지 확인함
        c = df["구코드"]
        index = df.index[c == jobKey]
        jobWord = df["구항목명"][index] # 코드에 맞는 문자열 추출
        break

    decodeData = "Error" # 만약 저기서 제대로 처리못하면 Error가 반환됨
    try:
        decodeData = jobWord.iloc(0)[0] # 일반적으로 xxx는 처리됨
    except Exception as e: # xx형태로 입력해야 되는데 xxx형태로 적어서 생기는 문제 처리
        w = str(jobKey)
        if(w[len(w)-1] == '0'):
            decodeData = jobDecoder(w[:len(w)-1]) # 보통 찾는데 없으면 재귀문제가 발생할 수 있긴함
    return decodeData

# 추적한 대상들을 직종코드 별로 분류함
def reclassificate_trace_data(path):
    with open(path, "r", encoding="utf-8") as f:
        loaded = json.load(f)

    jobAndCareer = dict()
    
    loadedKeys = loaded["keys"]
    # asd = 0
    for k in loadedKeys:
        # asd += 1
        if(len(loaded[k][0]) < 1): # 자료 없는건 거름
            continue
        infoList = keyListup(k) # 키값들 분리해서 보관
        if((infoList[3] in jobAndCareer.keys()) == True): # 3번은 직종코드 dictionary에 저장된게 있는지 확인함.
            careerList = jobAndCareer[infoList[3]][0]
            careerList.append(loaded[k])
            # keyDecoder(k)
        else:
            tempList = []
            tempList.append(loaded[k])
            jobAndCareer[infoList[3]] = [tempList]
            # keyDecoder(k)
        # print(loaded[k])
    
    keys = [*jobAndCareer.keys()]
    # print(keys)
    jobAndCareer["keys"] = keys
    jobAndCareer["jobs"] = len(keys)


    with open("./resources/Preprocess/jobAndCareer.json","w") as f:
        json.dump(jobAndCareer, f, indent=2)

# 직종코드별로 분류된 대상들 csv로 저장함 
def trace_change(path):
    with open(path, "r", encoding="utf-8") as f:
        loaded = json.load(f)
    
    # 회차 리스트
    versionList = []
    for i in range(1,27): 
        versionList.append(f"v{str(i).zfill(2)}")

    # column 작성용
    columnList = ["code", "job"]
    for i in range(1,27): # 01~26회차까지 나이랑 월급
        columnList.append(f"v{str(i).zfill(2)}age")
        columnList.append(f"v{str(i).zfill(2)}salary")

    df = pd.DataFrame(columns=columnList)
    
    keys = loaded["keys"]
    count = 0
    for k in keys: # 키값
        for outValue in loaded[k]: # 바인딩용
            for idx in outValue: # 직종
                # 추적대상
                if len(idx) < 2: # 2개 미만은 뺌. 변화율 확인 불가
                    continue
                newList = []
                
                indexCount = 0 # 26번 돌긴해야되는데 모든 회차에 응답한 사람은 없어서 index 구분해서 처리해야함
                for versionIndex in range(len(versionList)):
                    
                    if len(idx) > indexCount and idx[indexCount][0] == versionList[versionIndex]:
                        age = idx[indexCount][1]
                        salary = idx[indexCount][2]
                        if salary > 0: # 간혹 월급에 -1 적어둔 항목이 나오는거 처리
                            newList.append(idx[indexCount][1]) 
                            newList.append(idx[indexCount][2])
                        else:
                            for l in range(2):
                                newList.append("")  
                        indexCount += 1
                    else:
                        for l in range(2):
                            newList.append("")    

                # 넣기전에 앞쪽에 코드, 직종 형태로 시작하게 추가함
                newList.insert(0, jobDecoder(k))
                newList.insert(0, k)
                df.loc[count] = newList
                count += 1
                
                
    df.to_csv("./resources/Preprocess/TraceTarget.csv", index=False ,encoding="utf-8")

# 이전 회차와 현재 회차 비교하여 임금 변화율 체크함.
def rate_of_change(path):
    df = pd.read_csv(path)
    tag = "salary"
    versionList = []
    for i in range(2,27):# 이전꺼랑 비교하니 2부터 시작
        versionList.append(f"v{str(i).zfill(2)}")
    versionList.insert(0, "code")

    outDF = pd.DataFrame(columns=versionList)
    count = 0

    for i in range(df.last_valid_index()): # df의 인덱스 수만큼 반복
        if(df.iloc[i].notnull().sum() <= 4): # 코드, 직종 [나이 월급] 1개 면 총 4개 -> 4개 이하 = 데이터 1개 = 거름
            continue

        curIndex = df.iloc[i]
        newIndex = [curIndex["code"]]
        for j in range(2,27):
            preNumString = str(j-1).zfill(2)
            curNumString = str(j).zfill(2)

            preCol = f"v{preNumString}{tag}"
            curCol = f"v{curNumString}{tag}"
            
            # 2개만 있으면 가장 앞쪽이랑 나중이랑도 체크하게 하려했는데 복잡해보여서 취소함
            # if(str(curIndex[preCol]).isdigit() == False):
            #     for l in range(26,j,-1):
            #         preNumString = str(l).zfill(2)
            #         preCol = f"v{preNumString}{tag}"
            #         if(str(curIndex[preCol]).isdigit() == True):
            #             break

            # if(str(curIndex[curCol]).isdigit() == False):
            #     for l in range(j, 27):
            #         curNumString = str(l).zfill(2)
            #         curCol = f"v{curNumString}{tag}"
            #         if(str(curIndex[curCol]).isdigit() == True):
            #             break

            # 숫자로 변형되는지 확인하던 파트. df 자료형이라 그런지 안되길래 try로 우회함
            # if(str(curIndex[preCol]).isdigit() == False or str(curIndex[curCol]).isdigit() == False):
            #     print(int(curIndex[preCol]))
            #     print(curIndex[curCol])
            #     continue
            try:
                preSalary = int(curIndex[preCol])
                curSalary = int(curIndex[curCol])
            except: # 형변환 실패하면 보통 공백임
                newIndex.append("")
                continue
            
            per = curSalary / preSalary
            value = ""
            if per >= 1.0:
                per = (per - 1.0)*100
            else:
                per = (1.0 - per)*-100
            value += str(f"{per:.2f}")
            newIndex.append(value)

        # print(newIndex)
        # print(outDF)            
        outDF.loc[count] = newIndex
        count += 1

    outDF.to_csv("./resources/Preprocess/rateOfChange.csv", index=False, encoding=encoding)
    pass

# export_csv_Part_Data("D:/project/AI-Project/resources/compareData", "D:/project/AI-Project/resources", "haveJob", "p", compareCodeList)

# trace_target_data(r"D:\project\AI-Project\resources\Preprocess\haveJob", compareCodeList, collectCodeList)
# reclassificate_trace_data("D:/project/AI-Project/resources/Preprocess/Datas.json")
# trace_change("./resources/Preprocess/jobAndCareer.json")

# rate_of_change("./resources/Preprocess/TraceTarget.csv")
