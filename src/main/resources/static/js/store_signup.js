// 사업자 회원가입 폼 유효성 검사 함수
function validateBusinessSignupForm() {
    // 왼쪽(공통) 필드 검증
    var userId = document.getElementById("userId").value.trim();
    var password = document.getElementById("pwd").value;
    var passwordCheck = document.getElementById("pwd_check").value;
    var passwordQuestion = document.querySelector("select[name='passwordQuestion']").value;
    var passwordAnswer = document.getElementById("pwd_answer").value.trim();

    // 오른쪽(사업자 전용) 필드 검증
    var email = document.getElementById("email").value.trim();
    var phone = document.getElementById("phone").value.trim();
    var restaurantName = document.getElementById("store").value.trim();
    var businessNumber = document.getElementById("store_number").value.trim();

    // 아이디: 5~20자 영문자, 숫자
    var userIdRegex = /^[a-zA-Z0-9]{5,20}$/;
    if (!userIdRegex.test(userId)) {
        alert("아이디는 5자 이상 20자 이하의 영문자와 숫자로만 구성되어야 합니다.");
        return false;
    }

    // 비밀번호: 8~20자, 문자와 숫자 포함
    var passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,20}$/;
    if (!passwordRegex.test(password)) {
        alert("비밀번호는 8자 이상 20자 이하이며, 숫자와 문자를 포함해야 합니다.");
        return false;
    }

    // 비밀번호 확인
    if (password !== passwordCheck) {
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        return false;
    }

    // 비밀번호 찾기 질문과 답변 필수 체크
    if (passwordQuestion === "") {
        alert("비밀번호 찾기 질문을 선택해주세요.");
        return false;
    }
    if (passwordAnswer === "") {
        alert("비밀번호 찾기 답을 입력해주세요.");
        return false;
    }

    // 이메일 검증
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("유효한 이메일 주소를 입력해주세요.");
        return false;
    }

    // 전화번호 검증 (예: 010-1234-5678)
    var phoneRegex = /^01[016789]-\d{3,4}-\d{4}$/;
    if (!phoneRegex.test(phone)) {
        alert("전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)");
        return false;
    }

    // 상호명 필수
    if (restaurantName === "") {
        alert("상호명을 입력해주세요.");
        return false;
    }

    // 사업자등록번호 검증 (예: 123-45-67890)
    var businessNumberRegex = /^\d{3}-\d{2}-\d{5}$/;
    if (!businessNumberRegex.test(businessNumber)) {
        alert("사업자등록번호 형식이 올바르지 않습니다. (예: 123-45-67890)");
        return false;
    }

    // 추가적으로 약관 동의 체크 등 필요한 조건이 있으면 추가 가능

    return true;
}

// 폼 제출 이벤트에 유효성 검사 연결
document.addEventListener("DOMContentLoaded", function () {
    var businessForm = document.getElementById("businessSignupForm");
    if (businessForm) {
        businessForm.addEventListener("submit", function (e) {
            if (!validateBusinessSignupForm()) {
                e.preventDefault();
            }
        });
    }
});

// 기존의 아이디 중복 검사 함수도 그대로 사용
function checkUserId() {
    var userId = document.getElementById("userId").value.trim();
    if (userId === "") {
        alert("아이디를 입력해주세요.");
        return;
    }
    fetch('/checkUserId', {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
        body: new URLSearchParams({userId: userId})
    })
        .then(response => response.json())
        .then(data => {
            if (data.available) {
                alert("사용 가능한 아이디입니다.");
            } else {
                alert("이미 사용 중인 아이디입니다.");
            }
        })
        .catch(error => console.error("중복검사 오류:", error));
}
