<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>마이페이지</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h1 class="form-title">마이페이지</h1>
    <div class="user-info">
        <p><strong>아이디:</strong> ${sessionScope.loginMember.userId}</p>
        <p><strong>이름:</strong> ${sessionScope.loginMember.userName}</p>
    </div>
    <form action="/updateAction" method="post">
        <input type="hidden" name="userId" value="${sessionScope.loginMember.userId}">
        <div class="form-group">
            <label>새 비밀번호 (변경 시에만 입력)</label>
            <input type="password" name="userPw" placeholder="새 비밀번호">
        </div>
        <div class="form-group">
            <label>이름</label>
            <input type="text" name="userName" value="${sessionScope.loginMember.userName}" required>
        </div>
        <button type="submit" class="btn">정보 수정</button>
    </form>
    <div class="link-group"><a href="/logout">로그아웃</a></div>
</div>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</body>
</html>