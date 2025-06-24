# dashboard.py

import pandas as pd
import plotly.express as px
from dash import Dash, dcc, html, Input, Output

# 🎯 데이터 불러오기
# 기존 full_df를 저장해놨다고 가정 (CSV로 불러오기)
full_df = pd.read_csv('combined_output.csv')  # 또는 pkl 등 다른 포맷

# 만족도 점수 매핑
satisfaction_score = {
    '매우 만족': 5, '만족': 4, '보통': 3, '불만족': 2, '매우 불만족': 1
}
full_df['만족도점수'] = full_df['만족도'].map(satisfaction_score)

# 그룹별 평균 계산
grouped = full_df.groupby(['연령대', '성별', '학력'])['만족도점수'].mean().reset_index()

# 💻 Dash 앱 초기화
app = Dash(__name__)

app.layout = html.Div([
    html.H1("전반적 생활만족도 대시보드", style={'textAlign': 'center'}),
    dcc.Dropdown(
        id='age-dropdown',
        options=[{'label': age, 'value': age} for age in sorted(full_df['연령대'].dropna().unique())],
        value='30대',
        clearable=False,
        style={'width': '300px'}
    ),
    dcc.Graph(id='heatmap')
])

@app.callback(
    Output('heatmap', 'figure'),
    Input('age-dropdown', 'value')
)
def update_heatmap(selected_age):
    df = grouped[grouped['연령대'] == selected_age]
    pivot = df.pivot(index='학력', columns='성별', values='만족도점수')
    fig = px.imshow(
        pivot,
        text_auto=True,
        color_continuous_scale='YlGnBu',
        labels={'color': '평균 만족도'},
        aspect='auto'
    )
    fig.update_layout(title=f"{selected_age} 성별·학력별 평균 생활만족도")
    return fig

if __name__ == '__main__':
    app.run(debug=True)
