<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="common/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>사업차 상업부문 지역 분석</title>
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/IndustryResult.css">
</head>
<body>
	<div class="analysis-container">
		<h1 class="page-title">사업차 상업부문 지역 분석</h1>
		<div class="main-content">
			<!-- Sidebar -->
			<div class="sidebar">
				<h3>선택된 상업</h3>
				<select id="industrySelect" style="width: 100%; padding: 8px;">
					<option value="">-- 상업 선택 --</option>
					<option value="agriculture">농업, 임업 및 어업</option>
					<option value="mining">광업</option>
					<option value="manufacturing">제조업</option>
					<option value="electricity">전기/가스/수도/하수</option>
					<option value="construction">건설업</option>
					<option value="wholesale">도매 및 소매업</option>
					<option value="transport">운수 및 창고업</option>
					<option value="hospitality">숙박 및 음식점업</option>
					<option value="it">정보통신업</option>
					<option value="finance">금융 및 보험업</option>
					<option value="realestate">부동산업/시설관리/지원/임대</option>
					<option value="professional">전문 과학 및 기술서비스업</option>
					<option value="education">교육서비스업</option>
					<option value="health">보건업 및 사회복지서비스업</option>
					<option value="culture">오락 문화 및 운동관련서비스업</option>
					<option value="other">기타 공공 수리 및 개인서비스업</option>
				</select>
				<button id="addIndustryBtn" class="add-btn">+추가</button>
				<div class="industry-list-wrapper" id="industryList"></div>
			</div>

			<!-- Chart -->
			<div class="chart-area">
				<canvas id="industryChart"></canvas>
			</div>
		</div>
	</div>
	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>
	<%@ page import="java.util.Enumeration" %>
	<%@ page import="com.db.BaseVO" %>
	<%@ page import="org.json.JSONObject" %>
	<script>
    const dataMap = {};
    <% 
        Enumeration<String> keys = application.getAttributeNames();
		String opt1 = (String) application.getAttribute("rangeLimit");
		String opt2 = (String) application.getAttribute("mainType");
		System.out.println(keys);
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
    		System.out.println(key);
            
            if (key.matches("\\d{4}_.+")) { // 예: 2025_제조업
            	System.out.println("true");
            	JSONObject obj = (JSONObject) application.getAttribute(key);
                Object d = obj.get("data");
            	System.out.println(obj);
    %>
                dataMap["<%= key %>"] = {
                    data: <%= d %>
                };
    <%      }
        }
    %>
	let rangeLimit = <%= opt1 %>;
	let mainType = "<%= opt2 %>"; 
    console.log("range: ", rangeLimit);
    console.log("mainType: ", mainType);
    console.log("dataMap from context: ", dataMap);
</script>
	
	<script src="js/IndustryResult.js"></script>
	<script>
	
	</script>
	<%@ include file="common/footer.jsp"%>
</body>
</html>