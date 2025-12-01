$(document).ready(function() {
    $("#userPw, #userPwChk").on("keyup", function() {
        var userPw = $("#userPw").val();
        var userPwChk = $("#userPwChk").val();
        var alertMessage = $("#pwAlert");

        if (userPw !== userPwChk) {
            alertMessage.css("color","red");
            alertMessage.text("비밀번호가 일치하지 않습니다.");
        } else {
            alertMessage.css("color","green");
            alertMessage.text("비밀번호가 일치합니다.");
        }

    });

    $("#emailSelect").on("change", function() {
        var emailSelect = $(this).val();

        if (emailSelect === "") {
            $("#emailDomain").val("");
            $("#emailDomain").prop("readonly", false);
            $("#emailDomain").focus();
        } else {
            $("#emailDomain").val(emailSelect);
            $("#emailDomain").prop("readonly", true);
        }
    });

    //아이디중복 스크립트 수정
    //아이디 중복확인
    $ ("#userId").on("blur", function() {
        var userId = $("#userId").val();
        var alertMessage = $("#idAlert");

        $.ajax({
            type: "GET",
            url: "/checkId",
            data: { userId : userId }, // 폼 데이터 직렬화
            success: function(response) {
                if (response) {
                    alertMessage.css("color","red");
                    alertMessage.text("" + userId + "는(은) 이미 사용 중인 아이디입니다.");
                    focus("#userId");
                } else{
                    alertMessage.css("color","green");
                    alertMessage.text("" + userId + "는(은) 사용 가능한 아이디입니다.");
                }
            },
            error: function(error) {
                alert("아이디 중복 확인에 실패하였습니다. 다시 시도해주세요.");
            }
        });
    });

    //주소검색
    $(".zip-btn").on("click", function() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수
                
                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                }
                else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }
                
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.querySelector('input[name="zipCode"]').value = data.zonecode;
                document.querySelector('input[name="addr"]').value = addr;
                
                // 상세주소 필드로 포커스 이동
                document.querySelector('input[name="detAddr"]').focus();
            }
        }).open();
    });

    //회원가입
    $("#joinForm").on("submit", function(e) {
        var userPw = $("#userPw").val();
        var userPwChk = $("#userPwChk").val();
        var userId = $("#userId").val();

        if (userPw !== userPwChk) {
            e.preventDefault();
            alert("비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
            $("#userPw").focus();
        }
        
        var frontEmail = $("#frontEmail").val();
        var emailDomain = $("#emailDomain").val();
        var email = frontEmail + "@" + emailDomain;
        $("#email").val(email);

        console.log("앞 이메일: " + frontEmail);
        console.log("도메인 이메일: " + emailDomain);
        console.log("완성된 이메일: " + email);

        /*$.ajax({
            type: "GET",
            url: "/checkId",
            data: { userId : userId }, // 폼 데이터 직렬화
            success: function(response) {
                if (response) {
                    alert("" + userId + "는(은) 이미 사용 중인 아이디입니다.");
                    focus("#userId");
                    return false; // 중복된 아이디가 있을 경우 폼 제출 막기
                }
            },
            error: function(error) {
                alert("회원가입에 실패하였습니다. 다시 시도해주세요.");
            }
        });*/
        
        let formData = $(this).serialize();

        $.ajax({
            type: "POST",
            url: "/joinAction",
            data: formData, // 폼 데이터 직렬화
            success: function(response) {
                if (response.result === "success") {
                    alert(response.message);
                    location.href = response.redirectUrl;
                } else {
                    alert(response.message);
                }
            },
            error: function(error) {
                alert("회원가입에 실패하였습니다. 다시 시도해주세요.");
            }
        });

    });

});
