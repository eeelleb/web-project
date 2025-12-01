<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
    <div class="container">
        <h1 class="form-title">로그인</h1>
        
        
        <c:if test="${not empty error}">
            <script>
                // EL은 HTML 이스케이프만 하므로 간단 경고용으로 사용합니다.
                alert("${fn:escapeXml(error)}");
            </script>
        </c:if>

        <form id="loginForm" action="/loginAction" method="post">
            <div class="form-group">
                <label>아이디</label>
                <input type="text" id="userId" name="userId" required>
            </div>
            <div class="form-group">
                <label>비밀번호</label>
                <input type="password" id="userPw" name="userPw" required>
            </div>
            <button type="submit" class="btn">로그인</button>
        </form>
        
        <div class="link-group">
            <a href="/joinForm">회원가입</a>
        </div>
    </div>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="/js/login.js"></script>
</body>
</html>