<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, org.json.JSONArray, org.json.JSONObject" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/base.css">
    <link rel="stylesheet" type="text/css" href="css/header.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <link rel="stylesheet" type="text/css" href="css/simulateResult.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>예측 결과 | CAREER.AI</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="js/main.js" defer></script>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div class="page-wrapper">
        <%-- ================================================================== --%>
        <%--  JSP Scriptlet: 데이터 준비 (서블릿에서 전달된 데이터를 받음) --%>
        <%-- ================================================================== --%>
        <%
            // 직업군 코드와 이름 매핑
            Map<String, String> jobCategoryMap = new LinkedHashMap<>();
            jobCategoryMap.put("1", "관리자");
            jobCategoryMap.put("2", "전문가 및 관련 종사자");
            jobCategoryMap.put("3", "사무 종사자");
            jobCategoryMap.put("4", "서비스 종사자");
            jobCategoryMap.put("5", "판매 종사자");
            jobCategoryMap.put("6", "농림 어업 숙련 종사자");
            jobCategoryMap.put("7", "기능원 및 관련 기능 종사자");
            jobCategoryMap.put("8", "장치·기계조작 및 조립 종사자");
            jobCategoryMap.put("9", "단순 노무 종사자");

            // 만족도 요인 코드와 이름 매핑
            Map<String, String> satisFactorMap = new HashMap<>();
            satisFactorMap.put("satis_wage", "임금 또는 보수");
            satisFactorMap.put("satis_stability", "고용 안정성");
            satisFactorMap.put("satis_task_content", "일의 내용");
            satisFactorMap.put("satis_work_env", "근무 환경");
            satisFactorMap.put("satis_work_time", "근무 시간과 여가");
            satisFactorMap.put("satis_growth", "발전 가능성");
            satisFactorMap.put("satis_communication", "인간 관계");
            satisFactorMap.put("satis_fair_eval", "공정한 평가와 보상");
            satisFactorMap.put("satis_welfare", "복리 후생");
            
            String satisFocusKey = (String) request.getAttribute("satisFocusKey");
            String focusKeyName = satisFactorMap.get(satisFocusKey);
        %>

        <%-- ================================================================== --%>
        <%-- JSP Declaration: 헬퍼 함수 정의 (JSP 내에서 재사용될 함수) --%>
        <%-- ================================================================== --%>
        <%!
            String formatIncomeChange(double value) {
                String icon = value > 0.001 ? "▲" : (value < -0.001 ? "▼" : "―");
                return String.format("%s %.2f%%", icon, value * 100);
            }

            String formatSatisfactionChange(double value) {
                String icon = value > 0.001 ? "▲" : (value < -0.001 ? "▼" : "―");
                return String.format("%s %.2f점", icon, value);
            }

            String getChangeClass(double value) {
                if (value > 0.001) return "positive-change";
                if (value < -0.001) return "negative-change";
                return "no-change";
            }
        %>

        <aside class="sidebar">
            <h3>직업군 선택</h3>
            <form id="simulationForm" action="SimulateServlet" method="post">
                <input type="hidden" name="age" value="<%= request.getAttribute("userAge") %>">
                <input type="hidden" name="gender" value="<%= request.getAttribute("userGender") %>">
                <input type="hidden" name="education" value="<%= request.getAttribute("userEducation") %>">
                <input type="hidden" name="monthly_income" value="<%= request.getAttribute("userMonthlyIncome") %>">
                <input type="hidden" name="job_satisfaction" value="<%= request.getAttribute("userJobSatisfaction") %>">
                <input type="hidden" name="current_job_category" value="<%= request.getAttribute("currentJobCategory") %>">
                <input type="hidden" name="satis_focus_key" value="<%= request.getAttribute("satisFocusKey") %>">

                <div class="input-group">
                    <p><strong>현재 직업군:</strong> <%= jobCategoryMap.get(String.valueOf(request.getAttribute("currentJobCategory"))) %></p>

                    <label for="jobACategorySelect">선택 직업군 A</label>
                    <select id="jobACategorySelect" name="job_A_category" onchange="this.form.submit()">
                        <% for (Map.Entry<String, String> entry : jobCategoryMap.entrySet()) {
                                String selected = String.valueOf(request.getAttribute("selectedJobACategory")).equals(entry.getKey()) ? "selected" : ""; %>
                        <option value="<%= entry.getKey() %>" <%= selected %>><%= entry.getValue() %></option>
                        <% } %>
                    </select>
                </div>

                <div class="input-group">
                    <label for="jobBCategorySelect">선택 직업군 B</label>
                    <select id="jobBCategorySelect" name="job_B_category" onchange="this.form.submit()">
                        <% for (Map.Entry<String, String> entry : jobCategoryMap.entrySet()) {
                                String selected = String.valueOf(request.getAttribute("selectedJobBCategory")).equals(entry.getKey()) ? "selected" : ""; %>
                        <option value="<%= entry.getKey() %>" <%= selected %>><%= entry.getValue() %></option>
                        <% } %>
                    </select>
                </div>

                <div class="input-group">
                    <label for="prioritySlider">결과 추천 기준</label>
                    <div class="slider-wrapper">
                        <span>만족도 우선</span>
                        <input type="range" id="prioritySlider" min="0" max="100" value="50" step="10">
                        <span>소득 우선</span>
                    </div>
                    <div id="priorityLabel" class="slider-label">균형 (50:50)</div>
                </div>
            </form>
        </aside>

        <main class="main-content">
            <h2>커리어 시뮬레이션 결과</h2>
            <% if (request.getAttribute("errorMessage") != null) { %>
                <p style="color:red;"><%= request.getAttribute("errorMessage") %></p>
            <% } %>

            <h3>예측 결과 요약</h3>
            <div class="result-cards-container" id="predictionResultsContainer">
                <%
                    JSONArray predictionResults = new JSONArray(String.valueOf(request.getAttribute("predictionResults")));
                    JSONObject currentResult = predictionResults.getJSONObject(0);
                    JSONObject jobAResult = predictionResults.getJSONObject(1);
                    JSONObject jobBResult = predictionResults.length() > 2 ? predictionResults.getJSONObject(2) : null;
                %>
                <div class="result-card" data-scenario-id="current">
                    <div class="recommend-badge">AI 추천</div>
                    <h4>현직 유지 시</h4>
                    <div class="result-item">
                        <span class="label">월 소득 변화율</span>
                        <span class="value <%= getChangeClass(currentResult.getDouble("income_change_rate")) %>"><%= formatIncomeChange(currentResult.getDouble("income_change_rate")) %></span>
                    </div>
                    <div class="result-item">
                        <span class="label">직무 만족도 변화</span>
                        <span class="value <%= getChangeClass(currentResult.getDouble("satisfaction_change_score")) %>"><%= formatSatisfactionChange(currentResult.getDouble("satisfaction_change_score")) %></span>
                    </div>
                    <% if (focusKeyName != null) { %><p class="focus-key-info">중요 항목: <%= focusKeyName %> 반영</p><% } %>
                </div>

                <div class="result-card" data-scenario-id="jobA">
                    <div class="recommend-badge">AI 추천</div>
                    <h4 id="jobAName"><%= jobCategoryMap.get(String.valueOf(request.getAttribute("selectedJobACategory"))) %></h4>
                    <div class="result-item">
                        <span class="label">월 소득 변화율</span>
                        <span class="value <%= getChangeClass(jobAResult.getDouble("income_change_rate")) %>" id="jobAIncome"><%= formatIncomeChange(jobAResult.getDouble("income_change_rate")) %></span>
                    </div>
                    <div class="result-item">
                        <span class="label">직무 만족도 변화</span>
                        <span class="value <%= getChangeClass(jobAResult.getDouble("satisfaction_change_score")) %>" id="jobASatis"><%= formatSatisfactionChange(jobAResult.getDouble("satisfaction_change_score")) %></span>
                    </div>
                    <% if (focusKeyName != null) { %><p class="focus-key-info">중요 항목: <%= focusKeyName %> 반영</p><% } %>
                </div>

                <div class="result-card" data-scenario-id="jobB" style="<%= jobBResult == null ? "display:none;" : "" %>">
                    <div class="recommend-badge">AI 추천</div>
                    <h4 id="jobBName"><%= jobBResult != null ? jobCategoryMap.get(String.valueOf(request.getAttribute("selectedJobBCategory"))) : "" %></h4>
                    <div class="result-item">
                        <span class="label">월 소득 변화율</span>
                        <span class="value <%= getChangeClass(jobBResult != null ? jobBResult.getDouble("income_change_rate") : 0.0) %>" id="jobBIncome"><%= formatIncomeChange(jobBResult != null ? jobBResult.getDouble("income_change_rate") : 0.0) %></span>
                    </div>
                    <div class="result-item">
                        <span class="label">직무 만족도 변화</span>
                        <span class="value <%= getChangeClass(jobBResult != null ? jobBResult.getDouble("satisfaction_change_score") : 0.0) %>" id="jobBSatis"><%= formatSatisfactionChange(jobBResult != null ? jobBResult.getDouble("satisfaction_change_score") : 0.0) %></span>
                    </div>
                    <% if (jobBResult != null && focusKeyName != null) { %><p class="focus-key-info">중요 항목: <%= focusKeyName %> 반영</p><% } %>
                </div>
            </div>

            <h3>시나리오 비교</h3>
            <div class="charts-section">
                <div class="chart-container">
                    <h4>월 소득 변화율</h4>
                    <div class="chart-canvas-wrapper"><canvas id="incomeChart"></canvas></div>
                </div>
                <div class="chart-container">
                    <h4>직무 만족도 변화</h4>
                    <div class="chart-canvas-wrapper"><canvas id="satisfactionChart"></canvas></div>
                </div>
            </div>

            <h3 class="section-title">유사 사례 분포</h3>
            <p style="text-align:center; font-size:0.9rem; color:#555;">
                AI가 추천한 '<span id="distributionChartTitle" style="font-weight:bold; color:#00b894;"></span>' 시나리오와<br>
                유사한 조건(직업, 연령, 학력)을 가진 사람들의 1년 후 결과 분포입니다.
            </p>
            <div class="charts-section">
                <div class="chart-container">
                    <h4>소득 변화율 분포</h4>
                    <div class="chart-canvas-wrapper"><canvas id="incomeDistributionChart"></canvas></div>
                </div>
                <div class="chart-container">
                    <h4>직무 만족도 변화 분포</h4>
                    <div class="chart-canvas-wrapper"><canvas id="satisfactionDistributionChart"></canvas></div>
                </div>
            </div>

            <h3 class="section-title">AI 추천</h3>
            <div class="recommendation-section">
                <p><strong>추천 선택지:</strong> <span id="recommendedJobName" class="highlight">계산 중...</span></p>
                <p id="recommendationReason" class="recommendation-reason">추천 기준을 조절하여 가장 적합한 선택지를 찾아보세요.</p>
            </div>

            <form action="advice.jsp" method="get" style="text-align: center; margin-top: 20px;">
                <input type="hidden" name="userAge" value="<%= request.getAttribute("userAge") %>">
                <input type="hidden" name="userGender" value="<%= request.getAttribute("userGender") %>">
                <input type="hidden" name="userEducation" value="<%= request.getAttribute("userEducation") %>">
                <input type="hidden" name="userMonthlyIncome" value="<%= request.getAttribute("userMonthlyIncome") %>">
                <input type="hidden" name="userJobSatisfaction" value="<%= request.getAttribute("userJobSatisfaction") %>">
                <input type="hidden" name="currentJobCategoryName" value="<%= jobCategoryMap.get(String.valueOf(request.getAttribute("currentJobCategory"))) %>">
                <input type="hidden" name="selectedJobACategoryName" value="<%= jobCategoryMap.get(String.valueOf(request.getAttribute("selectedJobACategory"))) %>">
                <input type="hidden" name="selectedJobBCategoryName" value="<%= jobBResult != null ? jobCategoryMap.get(String.valueOf(request.getAttribute("selectedJobBCategory"))) : "" %>">
                <input type="hidden" name="satisFocusKeyName" value="<%= focusKeyName != null ? focusKeyName : "" %>">
                <input type="hidden" name="predictionResults" value='<%= new JSONArray(String.valueOf(request.getAttribute("predictionResults"))).toString() %>'>
                <input type="hidden" name="recommendedJobName" id="hiddenRecommendedJobName">
                <input type="hidden" name="recommendationReason" id="hiddenRecommendationReason">
                
                <button type="submit" class="btn-advice">AI 조언 받기</button>
            </form>

            <script>
                // Pass data from JSP to JavaScript
                const predictionResultsRaw = JSON.parse('<%= new JSONArray(String.valueOf(request.getAttribute("predictionResults"))).toString().replace("", "").replace("'", "'") %>');
                const selectedJobACategory = '<%= request.getAttribute("selectedJobACategory") %>';
                const selectedJobBCategory = '<%= request.getAttribute("selectedJobBCategory") != null ? request.getAttribute("selectedJobBCategory").toString() : "" %>';
                const jobCategoryMapJs = <%= new JSONObject(jobCategoryMap).toString() %>;
            </script>
            <script src="js/simulateResult.js"></script>
        </main>
    </div>
    
    <%@ include file="../common/footer.jsp" %>
</body>
</html>