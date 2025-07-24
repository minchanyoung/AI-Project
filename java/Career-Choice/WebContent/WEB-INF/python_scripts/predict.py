import sys
import json
import joblib
import pandas as pd
import numpy as np
from catboost import CatBoostRegressor
import os

# ==============================================================================
# 1. 상수 및 경로 정의 (Constants & Paths)
# ==============================================================================
# 스크립트의 현재 위치를 기준으로 경로를 설정하여 이식성을 높입니다.
SCRIPT_DIR = os.path.dirname(__file__)
MODEL_DIR = os.path.join(SCRIPT_DIR, "saved_models")
DATA_PATH = os.path.join(SCRIPT_DIR, "..", "data", "klips_data_23.csv")

# 모델 파일 경로
CAT_INCOME_MODEL_PATH = os.path.join(MODEL_DIR, "cat_income_change_model.cbm")
XGB_SATIS_MODEL_PATH = os.path.join(MODEL_DIR, "xgb_satisfaction_change_model.pkl")
CAT_SATIS_MODEL_PATH = os.path.join(MODEL_DIR, "cat_satisfaction_change_model.cbm")

# 모델 하이퍼파라미터 및 상수
ALPHA = 0.3  # 만족도 예측을 위한 Soft-Blending 가중치
MIN_SATISFACTION_CHANGE = -4  # 만족도 변화량의 최소값
MAX_SATISFACTION_CHANGE = 3   # 만족도 변화량의 최대값

# 예측에 사용될 피처 목록
# 만족도 예측 피처를 기본으로 정의
SATIS_FEATURES = [
    "age", "gender", "education", "monthly_income", "job_category",
    "satis_wage", "satis_stability", "satis_task_content", "satis_work_env",
    "satis_work_time", "satis_growth", "satis_communication",
    "satis_fair_eval", "satis_welfare", "prev_job_satisfaction",
    "prev_monthly_income", "job_category_income_avg",
    "income_relative_to_job", "job_category_education_avg", "education_relative_to_job",
    "job_category_satisfaction_avg", "age_x_job_category", "monthly_income_x_job_category",
    "education_x_job_category", "income_relative_to_job_x_job_category"
]
# 소득 예측 피처는 만족도 피처에 'job_satisfaction'과 'income_diff'를 추가
INCOME_FEATURES = SATIS_FEATURES + ["job_satisfaction", "income_diff"]


# ==============================================================================
# 2. 모델 및 데이터 로딩 (Model & Data Loading)
# ==============================================================================
def load_models():
    """
    저장된 3개의 머신러닝 모델(CatBoost 소득, XGBoost 만족도, CatBoost 만족도)을 로드합니다.
    Returns:
        Tuple: 로드된 모델 객체들 (cat_income, xgb_satis, cat_satis)
    """
    try:
        cat_income = CatBoostRegressor().load_model(CAT_INCOME_MODEL_PATH)
        xgb_satis = joblib.load(XGB_SATIS_MODEL_PATH)
        cat_satis = CatBoostRegressor().load_model(CAT_SATIS_MODEL_PATH)
        return cat_income, xgb_satis, cat_satis
    except Exception as e:
        print(f"Error loading models: {e}", file=sys.stderr)
        sys.exit(1)

def load_klips_data():
    """
    유사 사례 분석을 위한 원본 KLIPS 데이터를 로드합니다.
    Returns:
        pd.DataFrame or None: 로드된 데이터프레임 또는 실패 시 None
    """
    try:
        return pd.read_csv(DATA_PATH)
    except Exception as e:
        print(f"Error loading klips_data_23.csv: {e}", file=sys.stderr)
        return None

# 스크립트 시작 시 모델과 데이터를 전역 변수로 로드하여 재사용
cat_income, xgb_satis, cat_satis = load_models()
klips_df = load_klips_data()


# ==============================================================================
# 3. 핵심 기능 함수 (Core Functions)
# ==============================================================================
def get_similar_cases_distribution(scenario_data):
    """
    스마트 필터링을 통해 유사 사례를 찾고, 소득 및 만족도 변화량의 분포를 계산합니다.
    필터링은 가장 엄격한 조건에서 시작하여, 최소 10개의 사례가 확보될 때까지 점진적으로 완화됩니다.
    
    Args:
        scenario_data (dict): 현재 시나리오의 피처 데이터
    Returns:
        dict or None: 소득 및 만족도 분포 데이터(counts, bins) 또는 유사 사례 부족 시 None
    """
    if klips_df is None:
        return None
    try:
        job_cat = scenario_data['job_category']
        age = scenario_data['age']
        edu = scenario_data['education']
        gender = scenario_data['gender']

        # 단계별 필터링 전략 (엄격한 조건에서 점차 완화)
        filters = [
            # 1단계: 직업 + 나이(±5) + 학력 + 성별
            (klips_df['job_category'] == job_cat) & (klips_df['age'].between(age - 5, age + 5)) & 
            (klips_df['education'] == edu) & (klips_df['gender'] == gender),
            # 2단계: 나이 범위 완화 (±7)
            (klips_df['job_category'] == job_cat) & (klips_df['age'].between(age - 7, age + 7)) & 
            (klips_df['education'] == edu) & (klips_df['gender'] == gender),
            # 3단계: 학력 조건 제거
            (klips_df['job_category'] == job_cat) & (klips_df['age'].between(age - 7, age + 7)) & 
            (klips_df['gender'] == gender),
            # 4단계: 성별 조건 제거, 나이 범위 최대화 (±10)
            (klips_df['job_category'] == job_cat) & (klips_df['age'].between(age - 10, age + 10))
        ]

        similar_df = pd.DataFrame()
        for f in filters:
            similar_df = klips_df[f]
            if len(similar_df) >= 10:
                break
        
        if len(similar_df) < 10:
            return None

        # IQR 기반 이상치 제거 후 히스토그램 생성
        income_data = similar_df['income_change_rate']
        Q1, Q3 = income_data.quantile(0.25), income_data.quantile(0.75)
        IQR = Q3 - Q1
        lower, upper = Q1 - 1.5 * IQR, Q3 + 1.5 * IQR
        filtered_income = income_data[(income_data >= lower) & (income_data <= upper)]
        
        # 필터링된 데이터가 충분하면 사용, 아니면 원본 사용
        income_hist_data = filtered_income if len(filtered_income) >= 10 else income_data
        
        income_hist = np.histogram(income_hist_data, bins=8)
        satis_hist = np.histogram(similar_df['satisfaction_change_score'], bins=8)

        return {
            "income": {"counts": income_hist[0].tolist(), "bins": income_hist[1].tolist()},
            "satisfaction": {"counts": satis_hist[0].tolist(), "bins": satis_hist[1].tolist()}
        }
    except Exception:
        return None

def predict_scenario(row):
    """
    단일 시나리오에 대한 소득 및 만족도 변화를 예측합니다.
    
    Args:
        row (dict): 예측할 단일 시나리오 데이터
    Returns:
        Tuple: (소득 변화율, 만족도 변화 점수, 분포 데이터)
    """
    income_df = pd.DataFrame([row])[INCOME_FEATURES]
    satis_df = pd.DataFrame([row])[SATIS_FEATURES]

    # 모델 예측 (소득: CatBoost 단독, 만족도: Soft-Blending 앙상블)
    income_pred = cat_income.predict(income_df)[0]
    satis_pred_xgb = xgb_satis.predict(satis_df)[0]
    satis_pred_cat = cat_satis.predict(satis_df)[0]
    satis_pred_blend = ALPHA * satis_pred_xgb + (1 - ALPHA) * satis_pred_cat
    
    # 후처리: 만족도 값 범위를 실제 데이터의 최소/최대값으로 제한
    satis_pred_processed = np.clip(satis_pred_blend, MIN_SATISFACTION_CHANGE, MAX_SATISFACTION_CHANGE)
    
    distribution_data = get_similar_cases_distribution(row)

    return round(income_pred, 4), round(satis_pred_processed, 4), distribution_data


# ==============================================================================
# 4. 메인 실행 로직 (Main Execution)
# ==============================================================================
def main():
    """
    Java 서블릿으로부터 표준 입력(stdin)으로 시나리오 데이터를 JSON 형식으로 받아,
    예측을 수행하고 결과를 표준 출력(stdout)으로 반환합니다.
    """
    try:
        scenarios_data = json.loads(sys.stdin.read())
        results = []
        for scenario in scenarios_data:
            income, satis, dist = predict_scenario(scenario)
            results.append({
                "income_change_rate": income,
                "satisfaction_change_score": satis,
                "distribution": dist
            })
        print(json.dumps(results))
    except Exception as e:
        print(f"Error during prediction: {e}", file=sys.stderr)
        sys.exit(1)

if __name__ == "__main__":
    main()

