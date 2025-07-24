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
		<div class="section">
			<h1>CAREER.AI 산업 구조 분석 서비스</h1>
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

	<%@ include file="common/footer.jsp"%>
</body>
</html>

