$(document).ready(function() {
    $("#loginForm").on("submit", function(e) {
        e.preventDefault();  // ★ 이게 있어야 폼이 브라우저 기본 submit로 안 나갑니다.

        var userId = $("#userId").val();
        var userPw = $("#userPw").val();
        
        if (userId === "" || userPw === "") {
            alert("아이디와 비밀번호를 모두 입력해주세요.");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/loginAction",
            data: { userId: userId, userPw: userPw },
            success: function(response) {
                if (response.status === "success") {
                    location.href = response.redirectUrl;
                } else {
                    alert(response.message || "아이디 또는 비밀번호가 올바르지 않습니다.");
                }
            },
            error: function() {
                alert("로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });
});