import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import os
import re
import platform

# 📌 폰트 설정
if platform.system() == 'Windows':
    plt.rcParams['font.family'] = 'Malgun Gothic'
elif platform.system() == 'Darwin':
    plt.rcParams['font.family'] = 'AppleGothic'
else:
    plt.rcParams['font.family'] = 'NanumGothic'
plt.rcParams['axes.unicode_minus'] = False

# 📁 분석 대상 폴더
folder_path = r'C:\Users\user\OneDrive'
file_list = [
    f for f in os.listdir(folder_path)
    if re.match(r'^klips\d{2}p.*\.xlsx$', f)
]

education_map = {
    1: '미취학', 2: '무학', 3: '초등학교', 4: '중학교', 5: '고등학교',
    6: '2년제 대학,전문대', 7: '4년제 대학', 8: '대학원 석사', 9: '대학원 박사'
}

all_educations = []

# 📊 각 파일 처리
for file_name in file_list:
    file_path = os.path.join(folder_path, file_name)
    try:
        df = pd.read_excel(file_path)
        edu_cols = [
            col for col in df.columns
            if isinstance(col, str) and re.match(r'^p\d{2}0110$', col)
        ]
        if not edu_cols:
            print(f"[스킵] {file_name}: 학력 열 없음")
            continue

        edu_col = edu_cols[0]
        edu_data = df[edu_col].dropna()
        edu_data = edu_data[edu_data.isin(education_map.keys())]

        all_educations.extend(edu_data.tolist())
        print(f"[수집] {file_name} → '{edu_col}' → {len(edu_data)}명")

    except Exception as e:
        print(f"[오류] {file_name}: {e}")

# 📐 학력 레이블 변환 및 집계
edu_df = pd.DataFrame({'코드': all_educations})
edu_df['학력'] = edu_df['코드'].map(education_map)
edu_counts = edu_df['학력'].value_counts().sort_index()

# 📈 원형 그래프
fig, ax = plt.subplots(figsize=(8, 8))
wedges, _ = ax.pie(
    edu_counts,
    startangle=90,
    labels=None
)

# 🎯 라벨 주석 추가
for i, (wedge, label) in enumerate(zip(wedges, edu_counts.index)):
    angle = (wedge.theta2 + wedge.theta1) / 2
    angle_rad = np.deg2rad(angle)
    x, y = np.cos(angle_rad), np.sin(angle_rad)
    ax.annotate(
        f"{label} ({edu_counts.iloc[i]})",
        xy=(0.7 * x, 0.7 * y),
        xytext=(1.2 * x, 1.2 * y),
        ha='center',
        va='center',
        arrowprops=dict(arrowstyle='-', lw=0.8),
        fontsize=9,
        bbox=dict(boxstyle="round,pad=0.3", fc="white", ec="gray", lw=0.5)
    )

plt.title('전체 회차 학력 분포')
plt.axis('equal')
plt.tight_layout()
plt.show()
