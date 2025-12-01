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

    <form id="joinForm" action="/joinAction" method="post">
        <div class="form-group"><label>아이디</label><input type="text" id="userId" name="userId" class="userId" required></div>
        <p id="idAlert" style="color:red; font-size: 10px; text-align: left;"></p>
        <div class="form-group"><label>비밀번호</label><input type="password" id="userPw" name="userPw" required></div>
        <div class="form-group"><label>비밀번호 확인</label><input type="password" id="userPwChk" name="userPwChk" required></div>
        <p id="pwAlert" style="color:red; font-size: 10px; text-align: left;"></p>
        <div class="form-group"><label>이름</label><input type="text" name="userName" required></div>
        <div class="form-group"><label>휴대전화</label><input type="text" name="phone" required></div>
        <div class="form-group"><label>email</label><input type="text" name="frontEmail" id="frontEmail" class="frontEmail" required>
            <span class="at">@</span>
            <input type="text" id="emailDomain" class="emailDomain" name="emailDomain" required>
            <select id="emailSelect">
                <option value="">직접입력</option>
                <option value="naver.com">naver.com</option>
                <option value="daum.net">daum.net</option>
                <option value="gmail.com">gmail.com</option>
                <option value="nate.com">nate.com</option>
            </select>
            <input type="hidden" id="email" name="email" >
        </div>
        <div class="form-group zip-row"><label>우편번호</label><input type="text" name="zipCode" class="zipCode" placeholder="우편번호" required><button type="button" class="zip-btn">주소검색</button></div>
        <div class="form-group"><label>주소</label><input type="text" name="addr"  placeholder="도로명 주소" required></div>
        <div class="form-group"><label>상세주소</label><input type="text" name="detAddr" placeholder="상세 주소" required></div>
        <button type="submit" class="btn">가입하기</button>
    </form>
</div>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="/js/join.js"></script>
</body>
</html>