<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>회원가입 | CAREER.AI</title>
<link rel="stylesheet" href="css/base.css">
<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/footer.css">
<link rel="stylesheet" href="css/signup.css">
<link rel="stylesheet" href="css/auth.css">
<script src="js/main.js" defer></script>
</head>
<body>
	<%@ include file="common/header.jsp"%>
	<div class="auth-container">
		<div class="auth-wrapper">
			<h2 class="signup-title">회원가입</h2>
			<form id="signupForm"
				action="${pageContext.request.contextPath}/signup" method="post">
				<div class="form-group">
					<label for="username">아이디</label> <input type="text" id="username"
						name="username" required placeholder="영문+숫자 5~16자"> <span
						id="idCheckMsg" class="message"></span>
				</div>

				<div class="form-group">
					<label for="email">이메일</label> <input type="email" id="email"
						name="email" required> <span id="emailCheckMsg"
						class="message"></span>
				</div>

				<div class="form-group">
					<label for="password">비밀번호</label> <input type="password"
						id="password" name="password" required placeholder="8자 이상, 특수문자 포함">
				</div>

				<div class="form-group">
					<label for="confirmPassword">비밀번호 확인</label> <input type="password"
						id="confirmPassword" name="confirmPassword" required> <span
						id="pwMatchMsg" class="message"></span>
				</div>

				<button type="submit" class="btn btn-primary">가입하기</button>
			</form>
		</div>
	</div>

	<script src="js/signup.js"></script>
	<%@ include file="common/footer.jsp"%>
</body>
</html>

