<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="common/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>산업 분석 설문 | CAREER.AI</title>
  <link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/footer.css">
  <link rel="stylesheet" href="css/IndustryForm.css">
</head>
<body>
  <div class="container">
    <h1>분석 구성 설정</h1>
    <form id="IndustryForm" action="SetIndustryDataServlet" method="get">
    
	  <div class="form-group">
		  <label for="rangeLimit">범위 (1 ~ 10):</label>
		  <input type="number" id="rangeLimit" name="rangeLimit" min="1" max="10" value="5"">
	  </div>
		<%--
      <div class="form-group">
        <label for="industry">기준 산업 선택</label>
        <select id="industry" name="industry" required>
          <option value="">-- 산업 선택 --</option>
          <option value=전체>전체</option>
          <option value=농업임업및어업>농업/임업/어업</option>
          <option value=광업>광업</option>
          <option value=제조업>제조업</option>
          <option value=전기가스수도하수>전기/가스/수도</option>
          <option value=건설업>건설업</option>
          <option value=도매및소매업>도매/소매업</option>
          <option value=운수및창고업>운수/창고업</option>
          <option value=숙박및음식점업>숙박/음식점업</option>
          <option value=정보통신업>정보통신업</option>
          <option value=금융및보험업>금융/보험업</option>
          <option value=부동산업시설관리지원임대>부동산/임대업</option>
          <option value=전문과학및기술서비스업>전문/과학/기술 서비스</option>
          <option value=교육서비스업>교육 서비스업</option>
          <option value=보건업및사회복지서비스업>보건/사회복지</option>
          <option value=오락문화및운동관련서비스업>예술/스포츠/여가</option>
          <option value=기타공공수리및개인서비스업>기타 개인서비스</option>
        </select>
      </div>
      --%>

      <div class="form-group">
        <label for="detail">비교 항목 선택</label>
        <select id="detail" name="detail" required>
          <option value="">-- 산업 선택 --</option>
          <option value=0>상용종사자 변화율</option>
          <option value=1>임금 변화율</option>
          <option value=2>근무일 변화율</option>
          <option value=3>평균 근속 변화율</option>
          <option value=4>단입사업체 비율 변화율</option>
          <option value=5>종사자 변화율</option>
        </select>
      </div>

      <button type="submit" class="submit-btn" method="get" action="SetIndustryDataServlet">제출하고 분석 보기</button>
    </form>
  </div>

    <div id="loadingOverlay" style="display:none;">
        <div class="spinner"></div>
        <p>정보 구성 중입니다. 잠시만 기다려주세요</p>
    </div>
  <script src="js/IndustryForm.js"></script>
	<%@ include file="common/footer.jsp"%>
</body>
</html>
