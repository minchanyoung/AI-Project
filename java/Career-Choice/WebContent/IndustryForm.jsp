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
  <script src="js/main.js" defer></script>
</head>
<body>
  <div class="wrapper">
    <div class="form-wrapper">
      <h1>분석 구성 설정</h1>
      <form id="IndustryForm" action="SetIndustryDataServlet" method="get">
      
        <div class="form-group">
          <label for="rangeLimit">비교 범위 (1 ~ 10)</label>
          <input type="number" id="rangeLimit" name="rangeLimit" min="1" max="10" value="5">
        </div>

        <div class="form-group">
          <label for="detail">비교 항목 선택</label>
          <select id="detail" name="detail" required>
            <option value="">-- 비교 항목 선택 --</option>
            <option value="0">상용종사자 변화율</option>
            <option value="1">임금 변화율</option>
            <option value="2">근무일 변화율</option>
            <option value="3">평균 근속 변화율</option>
            <option value="4">단입사업체 비율 변화율</option>
            <option value="5">종사자 변화율</option>
          </select>
        </div>

        <button type="submit" class="btn btn-primary">제출하고 분석 보기</button>
      </form>
    </div>
  </div>

  <div id="loadingOverlay" style="display:none;">
      <div class="spinner"></div>
      <p>정보 구성 중입니다. 잠시만 기다려주세요</p>
  </div>
  <script src="js/IndustryForm.js"></script>
	<%@ include file="common/footer.jsp"%>
</body>
</html>

