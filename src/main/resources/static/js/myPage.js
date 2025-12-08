// /js/mypage.js

// ✅ 카카오(다음) 우편번호 검색 함수
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 최종 주소 문자열
            var addr = '';      // 기본 주소
            var extraAddr = ''; // 참고항목

            // 사용자가 선택한 주소 타입에 따라 주소값 설정
            if (data.userSelectedType === 'R') { // 도로명 주소 선택
                addr = data.roadAddress;
            } else { // 지번 주소 선택
                addr = data.jibunAddress;
            }

            // 도로명 주소일 때만 참고항목 조합
            if (data.userSelectedType === 'R') {
                // 법정동명이 있고, "동/로/가"로 끝나면 추가
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명과 공동주택 여부도 체크해서 추가
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 참고항목이 있으면 괄호까지 포함해서 주소 뒤에 붙이기
                if (extraAddr !== '') {
                    addr += ' (' + extraAddr + ')';
                }
            }

            // 우편번호와 주소 정보를 해당 필드에 세팅
            document.getElementById('zipCode').value = data.zonecode; // 5자리 우편번호
            document.getElementById('addr').value = addr;

            // 상세주소 입력 필드로 포커스 이동
            var detAddrInput = document.getElementById('detAddr');
            if (detAddrInput) {
                detAddrInput.focus();
            }
        }
    }).open();
}

$(document).ready(function () {
    // ✅ 마이페이지 정보 수정 Ajax
    $("#myPageForm").on("submit", function (e) {
        e.preventDefault();

        const form = $(this);
        const userName = form.find("input[name='userName']").val().trim();
        const email = form.find("input[name='email']").val().trim();

        if (userName === "") {
            alert("이름은 필수 입력입니다.");
            return;
        }

        if (email !== "" && !/^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(email)) {
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
                    // 세션에 저장된 값도 업데이트되었다고 가정하고 새로고침
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
