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
    </div>

    <form id="myPageForm" action="/updateAction" method="post">
        <!-- 아이디는 수정 불가(읽기 전용) -->
        <input type="hidden" name="userId" value="${sessionScope.loginMember.userId}">

        <div class="form-group">
            <label>새 비밀번호 (변경 시에만 입력)</label>
            <input type="password" name="userPw" placeholder="새 비밀번호">
        </div>

        <div class="form-group">
            <label>이름</label>
            <input type="text" name="userName"
            value="${sessionScope.loginMember.userName}" required>
        </div>

        <div class="form-group">
            <label>전화번호</label>
            <input type="text" name="phone"
                value="${sessionScope.loginMember.phone}">
        </div>

        <div class="form-group">
            <label>이메일</label>
            <input type="email" name="email"
            value="${sessionScope.loginMember.email}">
        </div>

        <div class="form-group">
            <label>우편번호</label>
            <div style="display:flex; gap:8px;">
                <input type="text" id="zipCode" name="zipCode"
                value="${sessionScope.loginMember.zipCode}" readonly>
                <button type="button" class="btn"
                        onclick="execDaumPostcode()">주소 검색</button>
            </div>
        </div>

        <div class="form-group">
            <label>주소</label>
            <input type="text" id="addr" name="addr"
            value="${sessionScope.loginMember.addr}" readonly>
        </div>

        <div class="form-group">
            <label>상세주소</label>
            <input type="text" id="detAddr" name="detAddr"
            value="${sessionScope.loginMember.detAddr}">
        </div>

        <button type="submit" class="btn">정보 수정</button>
    </form>

    <div class="link-group">
        <a href="/logout">로그아웃</a>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

<!-- ✅ 카카오(다음) 우편번호 API 스크립트 -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- 마이페이지용 JS -->
<script src="/js/mypage.js"></script>
</body>
</html>
