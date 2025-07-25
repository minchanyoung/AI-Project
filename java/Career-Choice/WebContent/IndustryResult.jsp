<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="common/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>산업별 동향 분석 | CAREER.AI</title>
  <link rel="stylesheet" href="css/base.css">
  <link rel="stylesheet" href="css/header.css">
  <link rel="stylesheet" href="css/footer.css">
  <link rel="stylesheet" href="css/main.css">
  <link rel="stylesheet" href="css/IndustryResult.css">
</head>
<body>
	<div class="page-wrapper">
		<!-- Sidebar -->
		<aside class="sidebar card">
			<h3>산업 선택 및 비교</h3>
			<div class="form-group">
				<label for="industrySelect">산업 선택</label>
				<select id="industrySelect">
					<option value="">-- 산업 선택 --</option>
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
			</div>
			<button id="addIndustryBtn" class="btn btn-primary">+ 추가</button>
			<hr>
			<h4>비교 목록</h4>
			<div class="industry-list-wrapper" id="industryList">
				<!-- Selected industries will be listed here -->
			</div>
		</aside>

		<!-- Main Content -->
		<main class="main-content">
			<h1 class="page-title">산업별 동향 분석</h1>
			<div class="chart-area card">
				<div class="chart-canvas-wrapper">
					<canvas id="industryChart"></canvas>
				</div>
			</div>
		</main>
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
	<%@ include file="common/footer.jsp"%>
</body>
</html>
