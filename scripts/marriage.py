import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import os
import re
import platform

# 📌 한글 폰트 설정 (운영체제별)
if platform.system() == 'Windows':
    plt.rcParams['font.family'] = 'Malgun Gothic'
elif platform.system() == 'Darwin':
    plt.rcParams['font.family'] = 'AppleGothic'
else:
    plt.rcParams['font.family'] = 'NanumGothic'
plt.rcParams['axes.unicode_minus'] = False

# 📁 분석 대상 폴더와 대상 파일 목록
folder_path = r'C:\Users\user\OneDrive'
file_list = [
    f for f in os.listdir(folder_path)
    if re.match(r'^klips\d{2}p.*\.xlsx$', f)
]

# 혼인상태 코드 → 설명 매핑
marriage_map = {
    1: '미혼',
    2: '기혼(배우자 있음)',
    3: '별거',
    4: '이혼',
    5: '사별'
}

all_statuses = []

# 📊 각 파일 처리
for file_name in file_list:
    file_path = os.path.join(folder_path, file_name)
    try:
        df = pd.read_excel(file_path)

        # pXX5501 열 추출
        marriage_cols = [
            col for col in df.columns
            if isinstance(col, str) and re.match(r'^p\d{2}5501$', col)
        ]
        if not marriage_cols:
            print(f"[스킵] {file_name}: 혼인상태 열 없음")
            continue

        m_col = marriage_cols[0]
        m_data = df[m_col].dropna()
        m_data = m_data[m_data.isin(marriage_map.keys())]

        all_statuses.extend(m_data.tolist())
        print(f"[수집] {file_name} → '{m_col}' → {len(m_data)}명")

    except Exception as e:
        print(f"[오류] {file_name}: {e}")

# 📐 데이터 정리 및 시각화 준비
m_df = pd.DataFrame({'코드': all_statuses})
m_df['혼인상태'] = m_df['코드'].map(marriage_map)
m_counts = m_df['혼인상태'].value_counts().sort_index()

# 📈 원형 그래프
fig, ax = plt.subplots(figsize=(8, 8))
wedges, _ = ax.pie(
    m_counts,
    startangle=90,
    labels=None
)

# 🎯 라벨 주석 추가
for i, (wedge, label) in enumerate(zip(wedges, m_counts.index)):
    angle = (wedge.theta2 + wedge.theta1) / 2
    angle_rad = np.deg2rad(angle)
    x, y = np.cos(angle_rad), np.sin(angle_rad)
    ax.annotate(
        f"{label} ({m_counts.iloc[i]})",
        xy=(0.7 * x, 0.7 * y),
        xytext=(1.2 * x, 1.2 * y),
        ha='center',
        va='center',
        arrowprops=dict(arrowstyle='-', lw=0.8),
        fontsize=9,
        bbox=dict(boxstyle="round,pad=0.3", fc="white", ec="gray", lw=0.5)
    )

plt.title('전체 회차 혼인상태 분포')
plt.axis('equal')
plt.tight_layout()
plt.show()
