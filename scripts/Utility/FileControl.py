import os
import pandas as pd

categoryList = ["구분", "문항 번호", "질문 내용", "변수명", "물리변수명"]
encoding = "utf-8-sig"

# 지정 폴더 내 DF 인덱스 갯수에 맞춰서 rename해줌
def file_rename(srcDir:str, startNum=1):
    fileList = os.listdir(srcDir)
    qCount = startNum
    print(f"file count: {fileList.count}")
    for f in fileList:
        df = pd.read_csv(f"{srcDir}/{f}")
        curQuestion = df.last_valid_index()
        
        os.rename(f"{srcDir}/{f}", f"{srcDir}/문항_코드_{qCount:4d}_{qCount+curQuestion:4d}.csv")
        #print(f"{srcDir}/{f}", f"{srcDir}/문항_코드_{qCount}_{qCount+curQuestion}.csv")
        qCount += (curQuestion + 1)
    print(qCount)
        # for d in df.index:
        #     print(df.index[d])
    pass

# 폴더 내의 csv들을 확인하고 그 중 지정한 단어를 포함한 대상을 찾아냄
def find_dir_word(srcDir:str, category:str, word:str):
    fileList = os.listdir(srcDir)

    for f in fileList:
        df = pd.read_csv(f"{srcDir}/{f}")
        for i in df.index:
            v =  str.find(df[category][i], word)
            if v >= 0:
                print(f)
                print(df.loc[i])
                os.system("pause")
# csv를 확인하고 그 중 지정한 단어를 포함한 대상을 찾아냄        
def find_dir_word(srcDir:str, category:str, word:str):
    df = pd.read_csv(f"{srcDir}")
    for i in df.index:
        v =  str.find(df[category][i], word)
        if v >= 0:
            print(srcDir)
            print(df.loc[i])
            os.system("pause")

# 지정한 폴더 내의 모든 csv를 하나의 csv로 합쳐서 다시 저장함
def merge_CSV(srcDir:str, dstPath:str):
    fileList = os.listdir(srcDir)

    mainDF = pd.read_csv(f"{srcDir}/{fileList[0]}", encoding=encoding)
    for i in range(1,len(fileList)):
        print(fileList[i])
        df = pd.read_csv(f"{srcDir}/{fileList[i]}", encoding=encoding)
        mainDF = pd.concat([mainDF, df])
    
    mainDF.to_csv(dstPath, index=False, encoding=encoding)

# 지정한 csv내에서 넘겨준 카테고리 내에 dataList를 포함한 데이터만 모아서 새 파일로 저장함
def merge_DF(srcPath:str, dstPath:str, category:str, dataList):
    srcDF = pd.read_csv(f"{srcPath}", encoding=encoding)
    df1 = pd.DataFrame()
    
    for data in dataList:
        target = srcDF[srcDF[category] == data]
        df2 = pd.DataFrame(target)
        df1 = pd.concat([df1, df2])
    
    df1.to_csv(dstPath, index=False, encoding=encoding)

# csv내에서 지정한 카테고리 내에 words를 포함한 DF리스트를 반환함
def get_word_list(path:str, category:str, words:list):
    df = pd.read_csv(f"{path}", encoding=encoding)
    targetList = []
    for word in words:
        for i in df.index:
            v =  str.find(df[category][i], word)
            if v >= 0:
                targetList.append(df.iloc[i])
                break

    return targetList

# 새 컬룸넣기(gpt가 매핑이 이상해서 추가함)
def insertColumn(path:str, columnName:str, index:list, insertPos:int):
    df = None
    try:
        df = pd.read_csv(path, encoding=encoding)
    except:
        df = pd.DataFrame()

    df.insert(insertPos, columnName,index)
    df.to_csv(path, index=False, encoding=encoding)


def ExportPartData(directory:str, dstDir:str, codekind, codeList:list):
    fileList = os.listdir(directory)

    for fileIndex in range(len(fileList)):
        numString = f"{fileIndex + 1}".zfill(2)
        path = f"{directory}/{fileList[fileIndex]}"
        df = pd.read_excel(path)

        dest = f"{dstDir}/refine{numString}p.csv"
        
        
        dstDF = None
        try:
            dstDF = pd.read_csv(dest, encoding=encoding)
        except:
            dstDF = pd.DataFrame()

        for columnCount in range(len(codeList)):
            column = f"p{numString}{codeList[columnCount]}"
            dfPart = df[column]
            dstDF.insert(columnCount, column, dfPart)
        dstDF.to_csv(dest, index=False, encoding=encoding)
    pass

#            성별    만나이   학력    직군     근로유무   액수(만)
codeList = ["0101", "0107", "0110", "0350", "1641", "1642"]
targetList = ["8140"]#, "임금변동"] # 임금변동은 이전 월급과 비교하여 변화 퍼센테이지 표시

# ExportPartData("D:/26", "D:/project/AI-Project/자료/inputCSV", "p", codeList)
# ExportPartData("D:/26", "D:/project/AI-Project/자료/inputCSV", "pa", targetList)



# 문항 찾으려고 진행한 부분  
# find_dir_word("./자료/total_문항보정_거주지역추가.csv", categoryList[2], "구성원")

# merge_CSV(r"D:\AI-Project\자료\질문변수", r"D:\AI-Project\자료\total.csv")

# 찾아낸 문항정보를 기반으로 모아서 새로 저장함
# personal = ["Q1-1", "Q1-2", "Q1-3", "Q1-4", "Q7-1"]
# merge_DF("./자료/total.csv", "./자료/inputCSV/personal.csv", categoryList[1], personal)

# economic = ["Q1-8", "Q1-11", "QH2-1", "QH2-2"]
# work = ["Q43-5", "Q1-10", "Q43-7", "Q43-2"]
# household = ["QH1-50", "QH14-6", "Q1-21"] # p**0150:구성원 수
# trust = ["Q105-4"] # 명확하지 않음
# target = ["Q65-8", "Q81-40"]
# merge_DF("./자료/total_문항보정_거주지역추가.csv", "./자료/inputCSV/work.csv", categoryList[1], work)

#          성별     생년        혼인        학력        건강상태(6차부터 존재함)
# index = ["p010101","p010104","p015501","p010110","p066101"]

#          직업종류   개인소득   가구소득   월 평균 생활비(*12하면 될듯)
# index = ["p010350","p011612","h012102","h012301"]

#      근로시간(임금)  직무만족도  고용형태  직장 안정감
# index = ["p011004","p014311","pa115102","p014312"]


# index = ["개인","개인"]
# insertColumn("./자료/inputCSV/target.csv", "구분", index, 0)
# insertColumn("./자료/inputCSV/target.csv", "문항 번호", target, 1)
# index = ["생활 만족도","삶의 만족도 현재 상태"]
# insertColumn("./자료/inputCSV/target.csv", "질문 내용", index, 2)
# index = ["p016508","pa208140"]
# insertColumn("./자료/inputCSV/target.csv", "변수명", index, 3)

#      가구원수     입주형태     지역코드
# index = ["h010150","h011406", "p010121"]
# insertColumn("./자료/inputCSV/household.csv", "물리코드", index)

#      생활 만족도    삶의 만족도 현재 상태   
# index = ["p016508","pa208140"]
# insertColumn("./자료/inputCSV/target.csv", "물리코드", index)

# 문항_코드_   1_  30.csv
# 구분               개인
# 문항 번호          Q1-1
# 질문 내용    성별은 무엇입니까?
# 변수명          p01sex
# Name: 0, dtype: object

# 문항_코드_   1_  30.csv
# 구분                  개인
# 문항 번호             Q1-2
# 질문 내용    생년은 어떻게 되십니까?
# 변수명          p01birthy
# Name: 1, dtype: object

# 문항_코드_   1_  30.csv
# 구분                   개인
# 문항 번호              Q1-3
# 질문 내용    현재 배우자가 있으십니까?
# 변수명           p01marsta
# Name: 2, dtype: object

# 문항_코드_   1_  30.csv
# 구분                개인
# 문항 번호           Q1-4
# 질문 내용    귀하의 최종 학력은?
# 변수명        p01educlv
# Name: 3, dtype: object

# 문항_코드_  62_  91.csv
# 구분                  개인
# 문항 번호             Q7-1
# 질문 내용    건강 상태는 어떠합니까?
# 변수명          p07health
# Name: 7, dtype: object