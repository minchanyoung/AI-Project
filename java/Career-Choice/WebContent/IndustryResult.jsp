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
	<script src="js/IndustryResult.js"></script>
	<%@ include file="common/footer.jsp"%>
</body>
</html>