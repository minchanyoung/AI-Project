import pandas as pd
import matplotlib.pyplot as plt
import platform
import os
import re

# 🔡 폰트 설정 (운영체제별)
if platform.system() == 'Windows':
    plt.rcParams['font.family'] = 'Malgun Gothic'
elif platform.system() == 'Darwin':
    plt.rcParams['font.family'] = 'AppleGothic'
else:
    plt.rcParams['font.family'] = 'NanumGothic'
plt.rcParams['axes.unicode_minus'] = False

# 📁 엑셀 파일 경로
folder_path = r'C:\Users\오정현\OneDrive'  # 각자 파일이 있는 경로로 수정하세요
file_list = [
    f for f in os.listdir(folder_path)
    if re.match(r'^klips\d{2}p.*\.xlsx$', f)
]

all_gender_data = []

for file_name in file_list:
    file_path = os.path.join(folder_path, file_name)
    try:
        df = pd.read_excel(file_path)
        # 성별 열 찾기: 'p[회차번호]0101' 패턴
        gender_cols = [
            col for col in df.columns
            if isinstance(col, str) and re.match(r'^p\d{2}0101$', col)
        ]
        if not gender_cols:
            print(f"[스킵] {file_name}: 성별 열 없음")
            continue

        gender_col = gender_cols[0]
        gender_values = df[gender_col].dropna()
        gender_values = gender_values[gender_values.isin([1, 2])]
        all_gender_data.extend(gender_values.tolist())
        print(f"[수집] {file_name}: {gender_col} → {len(gender_values)}명")

    except Exception as e:
        print(f"[오류] {file_name}: {e}")

# 📊 전체 성별 값 집계
label_map = {1: '남성', 2: '여성'}
labels_mapped = pd.Series(all_gender_data).map(label_map)
gender_counts = labels_mapped.value_counts()

# 📈 원형 그래프 그리기
plt.figure(figsize=(6, 6))
plt.pie(
    gender_counts,
    labels=gender_counts.index,
    autopct='%.1f%%',
    startangle=90
)
plt.title('전체 회차 성별 분포')
plt.axis('equal')
plt.tight_layout()
plt.show()

