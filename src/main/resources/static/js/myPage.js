// /js/mypage.js
$(document).ready(function () {
    $("#myPageForm").on("submit", function (e) {
        e.preventDefault();

        const form = $(this);
        const userName = form.find("input[name='userName']").val().trim();
        const userPhone = form.find("input[name='userPhone']").val().trim();
        const userEmail = form.find("input[name='userEmail']").val().trim();
        const userAddress = form.find("input[name='userAddress']").val().trim();

        if (userName === "") {
            alert("이름은 필수 입력입니다.");
            return;
        }

        if (userEmail !== "" && !/^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(userEmail)) {
            alert("이메일 형식이 올바르지 않습니다.");
            return;
        }

        $.ajax({
            type: "POST",
            url: "/updateAction",
            data: form.serialize(),
            success: function (response) {
                if (response.status === "success") {
                    alert(response.message || "회원 정보가 수정되었습니다.");
                    // 필요하면 새로고침으로 화면 값 재반영
                    location.reload();
                } else {
                    alert(response.message || "정보 수정 중 오류가 발생했습니다.");
                }
            },
            error: function () {
                alert("정보 수정 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    });
});
