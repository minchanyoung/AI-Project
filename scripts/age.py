import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import os
import re
import platform

# 📌 폰트 설정 (OS별)
if platform.system() == 'Windows':
    plt.rcParams['font.family'] = 'Malgun Gothic'
elif platform.system() == 'Darwin':
    plt.rcParams['font.family'] = 'AppleGothic'
else:
    plt.rcParams['font.family'] = 'NanumGothic'
plt.rcParams['axes.unicode_minus'] = False

# 📁 분석 대상 폴더
folder_path = r'C:\Users\오정현\OneDrive'
file_list = [
    f for f in os.listdir(folder_path)
    if re.match(r'^klips\d{2}p.*\.xlsx$', f)
]

all_ages = []

# 📊 각 파일 처리
for file_name in file_list:
    file_path = os.path.join(folder_path, file_name)
    try:
        df = pd.read_excel(file_path)
        # 'p[회차번호]0107' 패턴 찾기 (나이 열)
        age_cols = [
            col for col in df.columns
            if isinstance(col, str) and re.match(r'^p\d{2}0107$', col)
        ]
        if not age_cols:
            print(f"[스킵] {file_name}: 나이 열 없음")
            continue

        age_col = age_cols[0]
        age_data = df[age_col].dropna()
        age_data = age_data[age_data.between(0, 100)]

        all_ages.extend(age_data.tolist())
        print(f"[수집] {file_name} → '{age_col}' → {len(age_data)}명")

    except Exception as e:
        print(f"[오류] {file_name}: {e}")

# 📐 연령대 구간 설정
bins = list(range(0, 101, 5))
labels = [f'{i}~{i+4}세' for i in bins[:-1]]
age_df = pd.DataFrame({'나이': all_ages})
age_df['연령대'] = pd.cut(age_df['나이'], bins=bins, labels=labels, right=False)
age_group_counts = age_df['연령대'].value_counts().sort_index()

# 📈 원형 그래프 
fig, ax = plt.subplots(figsize=(8, 8))
wedges, _ = ax.pie(
    age_group_counts,
    startangle=90,
    labels=None
)

# 🎯 바깥쪽 라벨 표시 (annotate)
for i, (wedge, label) in enumerate(zip(wedges, age_group_counts.index)):
    angle = (wedge.theta2 + wedge.theta1) / 2
    angle_rad = np.deg2rad(angle)
    x = np.cos(angle_rad)
    y = np.sin(angle_rad)
    ax.annotate(
        f"{label} ({age_group_counts.iloc[i]})",
        xy=(0.7 * x, 0.7 * y),
        xytext=(1.2 * x, 1.2 * y),
        ha='center',
        va='center',
        arrowprops=dict(arrowstyle='-', lw=0.8),
        fontsize=9,
        bbox=dict(boxstyle="round,pad=0.3", fc="white", ec="gray", lw=0.5)
    )

plt.title('전체 회차 연령대 분포 (5세 단위)')
plt.axis('equal')
plt.tight_layout()
plt.show()