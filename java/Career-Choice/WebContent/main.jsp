<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>커리어 선택 예측 AI | CAREER.AI</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullPage.js/3.1.2/fullpage.min.css">
<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/footer.css">
<link rel="stylesheet" href="css/main.css">
<script src="js/main.js" defer></script>
</head>
<body>
	<%@ include file="common/header.jsp"%>
	
	<div id="fullpage">
		<div class="section">
			<div class="swiper-container">
				<div class="swiper-wrapper">
					<div class="swiper-slide" style="background-image:url(images/career_image.jpeg)">
						<div class="hero-content">
							<h1>
								당신의 커리어,<br> <span class="highlight">더 나은 선택</span>을 위해
							</h1>
							<p>커리어 선택에 대한 시뮬레이션, 지금 받아보세요</p>
							<div class="buttons">
								<a href="simulate.jsp" class="start-btn">시작하기</a> <a
									href="about.jsp" class="outline-btn">서비스 소개 보기</a>
							</div>
						</div>
					</div>
					<div class="swiper-slide" style="background-image:url(images/career_image_2.jpg)">
						<div class="hero-content">
							<h1>
								당신의 관심 분야,<br> <span class="highlight">성장 가능성</span>은 얼마나 될까요?
							</h1>
							<p>데이터로 보는 전망, 지금 확인해 보세요</p>
							<div class="buttons">
								<a href="IndustryForm.jsp" class="start-btn">시작하기</a> <a
									href="about2.jsp" class="outline-btn">서비스 소개 보기</a>
							</div>
						</div>
					</div>
				</div>
				<!-- Add Navigation -->
				<div class="swiper-button-next"></div>
				<div class="swiper-button-prev"></div>
			</div>
		</div>
		<div class="section section-cards">
			<div class="main-content">
				<h2>주요 서비스</h2>
				<div class="card-container">
					<div class="card">
						<div class="card-icon">
							<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
								<path stroke-linecap="round" stroke-linejoin="round" d="M2.25 18L9 11.25l4.306 4.307a11.95 11.95 0 015.814-5.519l2.74-1.22m0 0l-3.75-.625m3.75.625l-6.25 3.75" />
							</svg>
						</div>
						<h3>Trend</h3>
						<p>산업군 연도별 성장률을 통해<br>노동시장의 흐름을 확인하세요.</p>
						<a href="IndustryForm.jsp" class="card-button">자세히 보기</a>
					</div>
					<div class="card">
						<div class="card-icon">
							<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
								<path stroke-linecap="round" stroke-linejoin="round" d="M8.25 3v1.5M4.5 8.25H3m18 0h-1.5M4.5 12H3m18 0h-1.5m-15 3.75H3m18 0h-1.5M8.25 19.5V21M12 3v1.5m0 15V21m3.75-18v1.5m0 15V21m-9-1.5h10.5a2.25 2.25 0 002.25-2.25V6.75a2.25 2.25 0 00-2.25-2.25H6.75A2.25 2.25 0 004.5 6.75v10.5a2.25 2.25 0 002.25 2.25z" />
							</svg>
						</div>
						<h3>Simulate</h3>
						<p>시뮬레이션을 통해<br>소득 변화율과 만족도 변화를 예측하세요.</p>
						<a href="simulate.jsp" class="card-button">자세히 보기</a>
					</div>
					<div class="card">
						<div class="card-icon">
							<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
								<path stroke-linecap="round" stroke-linejoin="round" d="M9.813 15.904L9 18.75l-.813-2.846a4.5 4.5 0 00-3.09-3.09L2.25 12l2.846-.813a4.5 4.5 0 003.09-3.09L9 5.25l.813 2.846a4.5 4.5 0 003.09 3.09L15.75 12l-2.846.813a4.5 4.5 0 00-3.09 3.09zM18.259 8.715L18 9.75l-.259-1.035a3.375 3.375 0 00-2.455-2.456L14.25 6l1.036-.259a3.375 3.375 0 002.455-2.456L18 2.25l.259 1.035a3.375 3.375 0 002.456 2.456L21.75 6l-1.035.259a3.375 3.375 0 00-2.456 2.456zM16.898 20.562L16.25 21.75l-.648-1.188a2.25 2.25 0 01-1.423-1.423L13.5 18.75l1.188-.648a2.25 2.25 0 011.423 1.423l.648 1.188z" />
							</svg>
						</div>
						<h3>AI Advice</h3>
						<p>예측 결과를 바탕으로<br>개인 맞춤형 조언을 받아보세요.</p>
						<a href="advice.jsp" class="card-button">자세히 보기</a>
					</div>
				</div>
			</div>
			<%@ include file="common/footer.jsp"%>
		</div>
	</div>
	<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/fullPage.js/3.1.2/fullpage.min.js"></script>
	<script>
		document.addEventListener("DOMContentLoaded", function() {
			new fullpage('#fullpage', {
				licenseKey: 'gplv3',
				autoScrolling: true,
                navigation: false,
                responsiveWidth: 1024,
			});

			var swiper = new Swiper('.swiper-container', {
				effect: 'fade',
				loop: true,
				navigation: {
					nextEl: '.swiper-button-next',
					prevEl: '.swiper-button-prev',
				},
                autoplay: {
                    delay: 5000,
                    disableOnInteraction: false,
                },
			});
		});
	</script>
</body>
</html>