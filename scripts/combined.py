import os
import re
import logging
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import platform

# 🔔 로그 설정
for handler in logging.root.handlers[:]:
    logging.root.removeHandler(handler)

logging.basicConfig(
    level=logging.INFO,
    format='[%(levelname)s] %(message)s'
)

# 📌 한글 폰트 설정 (운영체제별)
if platform.system() == 'Windows':
    plt.rcParams['font.family'] = 'Malgun Gothic'
elif platform.system() == 'Darwin':
    plt.rcParams['font.family'] = 'AppleGothic'
else:
    plt.rcParams['font.family'] = 'NanumGothic'
plt.rcParams['axes.unicode_minus'] = False

# 📁 폴더 경로 및 파일 수집
folder_path = r'C:\Users\user\OneDrive'
file_list = [
    f for f in os.listdir(folder_path)
    if re.match(r'^klips\d{2}p.*\.xlsx$', f)
]
logging.info(f"총 분석 대상 파일 수: {len(file_list)}개")

# 변수 정의
age_bins = [0, 29, 39, 49, 59, 100]
age_labels = ['20대 이하', '30대', '40대', '50대', '60대 이상']
gender_map = {1: '남성', 2: '여성'}
edu_map = {
    1: '미취학', 2: '무학', 3: '초등학교', 4: '중학교',
    5: '고등학교', 6: '전문대', 7: '4년제 대학', 8: '석사', 9: '박사'
}
satisfaction_map = {
    1: '매우 만족', 2: '만족', 3: '보통', 4: '불만족', 5: '매우 불만족'
}

# 통계 변수 초기화
combined = []
total_rows = 0
skipped_files = 0
error_files = 0

# 🔄 분석 루프 시작
for file_name in file_list:
    file_path = os.path.join(folder_path, file_name)
    try:
        df = pd.read_excel(file_path)
        cols = df.columns

        age_col = [c for c in cols if re.match(r'^p\d{2}0107$', c)]
        gender_col = [c for c in cols if re.match(r'^p\d{2}0101$', c)]
        edu_col = [c for c in cols if re.match(r'^p\d{2}0110$', c)]
        sat_col = [c for c in cols if re.match(r'^p\d{2}6508$', c)]

        if not all([age_col, gender_col, edu_col, sat_col]):
            logging.warning(f"[스킵] {file_name} → 필요한 열 누락")
            skipped_files += 1
            continue

        subset = df[[age_col[0], gender_col[0], edu_col[0], sat_col[0]]].dropna()
        subset.columns = ['나이', '성별', '학력', '만족도']

        subset = subset[
            (subset['성별'].isin(gender_map)) &
            (subset['학력'].isin(edu_map)) &
            (subset['만족도'].isin(satisfaction_map)) &
            (subset['나이'].between(15, 100))
        ]

        total_rows += len(subset)

        subset['연령대'] = pd.cut(subset['나이'], bins=age_bins, labels=age_labels)
        subset['성별'] = subset['성별'].map(gender_map)
        subset['학력'] = subset['학력'].map(edu_map)
        subset['만족도'] = subset['만족도'].map(satisfaction_map)

        combined.append(subset[['연령대', '성별', '학력', '만족도']])
        logging.info(f"[수집] {file_name} → {len(subset)}명")

    except Exception as e:
        logging.error(f"[오류] {file_name} → {e}")
        error_files += 1

# 📊 처리 결과 요약
logging.info("📊 분석 완료")
logging.info(f"▶ 총 응답자 수: {total_rows:,}명")
logging.info(f"▶ 건너뛴 파일: {skipped_files}개")
logging.info(f"▶ 오류 발생 파일: {error_files}개")

# 🔎 데이터 병합 및 시각화
full_df = pd.concat(combined)

full_df.to_csv('combined_output.csv', index=False)

import seaborn as sns
import matplotlib.pyplot as plt

# 1. 만족도 점수 매핑
satisfaction_score = {
    '매우 만족': 5,
    '만족': 4,
    '보통': 3,
    '불만족': 2,
    '매우 불만족': 1
}
full_df['만족도점수'] = full_df['만족도'].map(satisfaction_score)

# 2. 연령대 × 성별 × 학력별 평균 점수 계산
grouped = full_df.groupby(['연령대', '성별', '학력'])['만족도점수'].mean().reset_index()

# 3. 연령대 목록
age_groups = grouped['연령대'].dropna().unique()

# 4. 연령대별 히트맵 반복 출력
for age in age_groups:
    data = grouped[grouped['연령대'] == age]
    heat_data = data.pivot(index='학력', columns='성별', values='만족도점수')

    plt.figure(figsize=(6, 5))
    sns.heatmap(
        heat_data,
        annot=True,
        fmt=".2f",
        cmap='YlGnBu',
        vmin=1, vmax=5,
        cbar_kws={'label': '평균 만족도 점수'}
    )
    plt.title(f'{age} 성별·학력별 평균 생활만족도')
    plt.xlabel('성별')
    plt.ylabel('학력')
    plt.tight_layout()
    plt.show()


