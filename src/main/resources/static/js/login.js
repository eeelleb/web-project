$(document).ready(function() {
    $("#loginForm").on("submit", function() {
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
                if (response === "success") {
                    alert("로그인에 성공했습니다.");
                }
                else {
                    alert("아이디 또는 비밀번호가 올바르지 않습니다.");
                }
            },
            error: function() {
                alert("로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
            }
        });
    });
});