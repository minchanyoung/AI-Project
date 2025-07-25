<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>About | CAREER.AI</title>
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/footer.css">
<link rel="stylesheet" href="css/about.css">
<script src="js/main.js" defer></script>
</head>
<body>
<%@ include file="common/header.jsp"%>
    <div class="wrapper">
        <div class="tabs">
            <button class="tab-link active" onclick="openTab(event, 'service-intro')">커리어 시뮬레이션</button>
            <button class="tab-link" onclick="openTab(event, 'industry-analysis')">산업 구조 분석</button>
        </div>

        <div id="service-intro" class="tab-content" style="display: block;">
            <div class="section">
                <h1>CAREER.AI 커리어 선택 예측 서비스</h1>
                <p>
                    <strong>CAREER.AI</strong>는 중요한 커리어 선택의 순간, 당신의 곁에서 든든한 나침반이 되어주는 AI 커리어 파트너입니다. <span
                        class="highlight">나와 비슷한 사람들의 실제 데이터</span>를 통해 미래를 예측하고, <span class="highlight">나만을 위한 AI의 조언</span>을 더해 후회 없는 선택을 할 수 있도록 지원합니다.
                </p>
            </div>

            <div class="section how-it-works-section">
                <h2>CAREER.AI, 이렇게 이용하세요</h2>
                <div class="steps-container">
                    <div class="step">
                        <div class="step-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" d="M9 12h3.75M9 15h3.75M9 18h3.75m3 .75H18a2.25 2.25 0 002.25-2.25V6.108c0-1.135-.845-2.098-1.976-2.192a48.424 48.424 0 00-1.123-.08m-5.801 0c-.065.21-.1.433-.1.664 0 .414.336.75.75.75h4.5a.75.75 0 00.75-.75 2.25 2.25 0 00-.1-.664m-5.8 0A2.251 2.251 0 0113.5 2.25H15c1.012 0 1.867.668 2.15 1.586m-5.8 0c-.376.023-.75.05-1.124.08C9.095 4.01 8.25 4.973 8.25 6.108V8.25m0 0H4.875c-.621 0-1.125.504-1.125 1.125v11.25c0 .621.504 1.125 1.125 1.125h9.75c.621 0 1.125-.504 1.125-1.125V9.375c0-.621-.504-1.125-1.125-1.125H8.25zM6.75 12h.008v.008H6.75V12zm0 3h.008v.008H6.75V15zm0 3h.008v.008H6.75V18z" />
                            </svg>
                        </div>
                        <h3>1. 내 정보 입력</h3>
                        <p>AI 분석에 필요한<br>나의 현재 정보를 입력합니다.</p>
                    </div>
                    <div class="step-separator"></div>
                    <div class="step">
                        <div class="step-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" d="M9.813 15.904L9 18.75l-.813-2.846a4.5 4.5 0 00-3.09-3.09L2.25 12l2.846-.813a4.5 4.5 0 003.09-3.09L9 5.25l.813 2.846a4.5 4.5 0 003.09 3.09L15.75 12l-2.846.813a4.5 4.5 0 00-3.09 3.09zM18.259 8.715L18 9.75l-.259-1.035a3.375 3.375 0 00-2.455-2.456L14.25 6l1.036-.259a3.375 3.375 0 002.455-2.456L18 2.25l.259 1.035a3.375 3.375 0 002.456 2.456L21.75 6l-1.035.259a3.375 3.375 0 00-2.456 2.456zM16.898 20.562L16.25 21.75l-.648-1.188a2.25 2.25 0 01-1.423-1.423L13.5 18.75l1.188-.648a2.25 2.25 0 011.423 1.423l.648 1.188z" />
                            </svg>
                        </div>
                        <h3>2. AI 미래 예측</h3>
                        <p>입력된 정보를 바탕으로<br>AI가 미래를 예측합니다.</p>
                    </div>
                    <div class="step-separator"></div>
                    <div class="step">
                        <div class="step-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" d="M3 13.125C3 12.504 3.504 12 4.125 12h2.25c.621 0 1.125.504 1.125 1.125v6.75C7.5 20.496 6.996 21 6.375 21h-2.25A1.125 1.125 0 013 19.875v-6.75zM9.75 8.625c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125v11.25c0 .621-.504 1.125-1.125 1.125h-2.25a1.125 1.125 0 01-1.125-1.125V8.625zM16.5 4.125c0-.621.504-1.125 1.125-1.125h2.25C20.496 3 21 3.504 21 4.125v15.75c0 .621-.504 1.125-1.125 1.125h-2.25a1.125 1.125 0 01-1.125-1.125V4.125z" />
                            </svg>
                        </div>
                        <h3>3. 맞춤 리포트 확인</h3>
                        <p>여러 선택지를 비교 분석한<br>상세 리포트를 확인합니다.</p>
                    </div>
                </div>
            </div>

            <div class="section">
                <h2>주요 기능</h2>
                <ul>
                    <li><strong>커리어 시뮬레이션:</strong> 현직 유지 또는 직업 변경 시의 소득과 직무 만족도 변화를 예측합니다.</li>
                    <li><strong>노동시장 트렌드:</strong> 직업별 성장률, 안정성 등 최신 데이터를 통해 노동시장의 흐름을 파악합니다.</li>
                    <li><strong>나만을 위한 AI 조언:</strong> 분석 결과를 바탕으로, AI가 사용자에게 최적화된 조언을 제공합니다.</li>
                </ul>
            </div>

            <div class="section">
                <h2>어떻게 예측하나요?</h2>
                <p>
                    CAREER.AI는 공신력 있는 <strong>한국노동패널(KLIPS)</strong> 데이터를 사용합니다. 사용자가 입력한 정보(연령, 학력, 직업 등)를 바탕으로, 나와 가장 비슷한 사람들을 데이터 속에서 찾아냅니다. 그리고 그 사람들이 <strong>1년 뒤 실제로 어떻게 변화했는지</strong>를 분석하여, 나의 미래 소득과 직무 만족도를 예측하는 방식입니다.
                </p>
                <p>이를 통해 막연한 추측이 아닌, 실제 사례에 기반한 객관적인 예측 결과를 확인할 수 있습니다.</p>
            </div>

            <div class="section">
                <h2>AI 조언, 어떻게 다른가요?</h2>
                <p>
                    단순히 'A가 좋다'고 말하는 대신, 왜 좋은지를 구체적으로 설명해주는 똑똑한 AI입니다. 사용자의 상황과 중요하게 생각하는 가치(소득 vs 만족도)에 맞춰, 마치 <strong>나를 잘 아는 선배가 조언해주듯</strong> 현실적인 커리어 전략을 세울 수 있도록 돕습니다.
                </p>
            </div>

            <div class="section">
                <h2>이것만은 기억해주세요</h2>
                <ul>
                    <li>CAREER.AI는 여러분의 더 나은 선택을 돕기 위한 <strong>정보 분석 도구</strong>입니다. 최종 결정은 항상 여러분 자신의 몫이라는 점을 잊지 마십시오.</li>
                    <li>예측 결과는 수많은 데이터를 바탕으로 한 통계적 경향이므로, 개인의 노력이나 외부 환경 변화에 따라 실제 미래는 달라질 수 있습니다.</li>
                    <li>여러분의 모든 정보는 <strong>완벽히 익명으로 처리</strong>되어 안전하게 보호되니, 안심하고 이용하시기 바랍니다.</li>
                </ul>
            </div>
        </div>

        <div id="industry-analysis" class="tab-content">
            <div class="section">
                <h1>CAREER.AI 노동시장 흐름 예측 서비스</h1>
                <p>
                    <strong>CAREER.AI</strong>는 산업별 구조 데이터를 기반으로 머신러닝 회귀 분석을 수행하여, <span class="highlight">각 산업의 고유한 특성과 성장 신호</span>를 도출합니다. 단순 수치 나열이 아닌 <span class="highlight">해석 가능한 AI 분석 결과</span>를 통해, 산업의 구조적 강점과 리스크를 명확히 파악할 수 있습니다.
                </p>
            </div>
        
            <div class="section how-it-works-section">
                <h2>CAREER.AI, 이렇게 분석합니다</h2>
                <div class="steps-container">
                    <div class="step">
                        <div class="step-icon">📊</div>
                        <h3>1. 산업 구조 데이터 수집</h3>
                        <p>공식 통계 데이터를 기반으로<br>산업별 사업체 구성, 종사자 구성, 임금 구조 등을 수집합니다.</p>
                    </div>
                    <div class="step-separator"></div>
                    <div class="step">
                        <div class="step-icon">🧠</div>
                        <h3>2. 회귀 기반 특성 분석</h3>
                        <p>머신러닝 회귀 모델을 활용해<br>산업의 성장률과 구조적 변수 간의 관계를 분석합니다.</p>
                    </div>
                    <div class="step-separator"></div>
                    <div class="step">
                        <div class="step-icon">📈</div>
                        <h3>3. 해석 가능한 산업 리포트</h3>
                        <p>회귀 계수 기반으로<br>각 산업의 특성과 경향을 시각화 및 리포트 형태로 제공합니다.</p>
                    </div>
                </div>
            </div>
        
            <div class="section">
                <h2>분석 가능한 주요 항목</h2>
                <ul>
                    <li><strong>사업체 구성:</strong> 사업체 규모별 비율 (1~5인, 10~20인 등)</li>
                    <li><strong>종사자 구성:</strong> 상용직, 일용직, 자영업, 무급가족 종사자 비율</li>
                    <li><strong>임금 정보:</strong> 평균급여, 정액급여, 초과급여, 특별급여 등</li>
                    <li><strong>기타 변수:</strong> 평균연령, 평균근속년수, 근로시간 등</li>
                </ul>
            </div>
        
            <div class="section">
                <h2>CAREER.AI 산업 분석이 특별한 이유</h2>
                <ul>
                    <li><strong>해석 중심 분석:</strong> 단순 예측이 아닌, 계수 기반 해석을 통해 산업의 구조적 특성을 설명합니다.</li>
                    <li><strong>정책 활용 가능성:</strong> 산업 성장성 진단, 구조 리스크 파악 등 공공/민간 정책 수립에 활용할 수 있습니다.</li>
                    <li><strong>현실 기반 인사이트:</strong> 통계청, 노동조사 기반의 공신력 있는 데이터만 사용합니다.</li>
                </ul>
            </div>
        
            <div class="section">
                <h2>CAREER.AI의 산업 분석, 이런 분께 추천합니다</h2>
                <ul>
                    <li>고용, 산업 정책 수립에 필요한 정량적 근거가 필요한 연구자 및 행정가</li>
                    <li>산업 구조 변화나 인구구조 영향을 분석하고자 하는 학계 및 언론</li>
                    <li>향후 진출 산업군을 고민 중인 스타트업 및 창업자</li>
                </ul>
            </div>
        
            <div class="section">
                <h2>주의사항</h2>
                <ul>
                    <li>본 서비스는 <strong>산업구조 기반 회귀분석 결과에 따른 구조적 경향성 해석</strong>을 제공합니다.</li>
                    <li>미래 예측은 통계적 모델 기반의 수치 추정으로, 환경 변화나 외부 요인에 따라 달라질 수 있습니다.</li>
                    <li>데이터는 <strong>공식 통계 기준으로만 분석</strong>되며, 개별 사업체나 기업에 대한 직접적인 정보는 포함되지 않습니다.</li>
                </ul>
            </div>
        </div>
    </div>

    <script>
        function openTab(evt, tabName) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tab-content");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tab-link");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(tabName).style.display = "block";
            evt.currentTarget.className += " active";
        }
    </script>

<%@ include file="common/footer.jsp"%>
</body>
</html>