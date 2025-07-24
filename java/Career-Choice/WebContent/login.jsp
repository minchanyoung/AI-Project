<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>로그인 | CAREER.AI</title>
  <link rel="stylesheet" href="css/base.css">
  <link rel="stylesheet" href="css/header.css">
  <link rel="stylesheet" href="css/footer.css">
  <link rel="stylesheet" href="css/login.css">
  <link rel="stylesheet" href="css/auth.css">
  <script src="js/main.js" defer></script>
</head>
<body>
  <%@ include file="common/header.jsp"%>
  <div class="auth-container">
    <div class="auth-wrapper">
      <h2>로그인</h2>
      <form id="loginForm" method="post" action="${pageContext.request.contextPath}/login">
        <div class="form-group">
          <label for="username">아이디</label>
          <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
          <label for="password">비밀번호</label>
          <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group checkbox-group">
          <label><input type="checkbox" name="rememberMe"> 로그인 유지</label>
        </div>

        <button type="submit" class="btn btn-primary">로그인</button>

        <p class="goto-signup">아직 회원이 아니신가요? <a href="signup.jsp">회원가입</a></p>
      </form>
    </div>
  </div>

  <script src="js/login.js"></script>
  <%@ include file="common/footer.jsp"%>
</body>
</html>
