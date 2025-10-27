<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h1>회원가입</h1>

    <c:if test="${not empty error}">
        <script>
            // EL은 HTML 이스케이프만 하므로 간단 경고용으로 사용합니다.
            alert("${fn:escapeXml(error)}");
        </script>
    </c:if>

    <form action="/joinAction" method="post">
        <div class="form-group"><label>아이디</label><input type="text" name="userId" required></div>
        <div class="form-group"><label>비밀번호</label><input type="password" name="userPw" required></div>
        <div class="form-group"><label>이름</label><input type="text" name="userName" required></div>
        <div class="form-group"><label>휴대전화</label><input type="text" name="phone" required></div>
        <div class="form-group"><label>email</label><input type="text" name="frontEmail" required>@<input type="text" name="backEmail" required></div>
        <div class="form-group"><label>주소</label><input type="text" name="addr" required></div>
        <div class="form-group"><label>상세주소</label><input type="text" name="detAddr" required></div>
        <button type="submit" class="btn">가입하기</button>
    </form>
</div>
</body>
</html>