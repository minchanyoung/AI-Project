<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>사업차 상업부문 지역 분석</title>
<link rel="stylesheet" href="css/main.css">
<script></script>
<link rel="stylesheet" href="css/IndustryResult.css">
</head>
<body>
	<%@ include file="common/header.jsp"%>
	<div class="analysis-container">
		<h1 class="page-title">사업차 상업부문 지역 분석</h1>
		<div class="main-content">
			<!-- Sidebar -->
			<div class="sidebar">
				<h3>선택된 상업</h3>
				<select id="industrySelect" style="width: 100%; padding: 8px;">
					<option value="">-- 상업 선택 --</option>
					<option value="agriculture">농에업</option>
					<option value="manufacturing">제조업</option>
					<option value="it">정보화</option>
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
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="com.db.BaseVO" %>
	<%
    ArrayList<BaseVO<Integer>> dataList = (ArrayList<BaseVO<Integer>>) application.getAttribute("contextData");
	for (int i = 0; i < dataList.size(); i++){
		for (int j = 0; j < dataList.get(i).size(); j++){
			System.out.println(dataList.get(i).getData().get(j));
		}
	}
	%>
		<script id="contextData" type="application/json">
[
		<% for (int i = 0; i < dataList.size(); i++) {
		    BaseVO<Integer> vo = dataList.get(i);
		    int year = vo.getYear();
		    String type = vo.getIndustryType();
		    ArrayList<Integer> data = vo.getData();
		%>
    {
        "year": <%= year %>,
        "industryType": "<%= type %>",
        "data": [<% for(int j = 0; j < data.size(); j++) { %><%= data.get(j) %><%= (j < data.size() - 1) ? "," : "" %><% } %>]
    }<%= (i < dataList.size() - 1) ? "," : "" %>
<% } %>
]
</script>
	
	<script src="js/IndustryResult.js"></script>
	<script>
	
	</script>
	<%@ include file="common/footer.jsp"%>
</body>
</html>