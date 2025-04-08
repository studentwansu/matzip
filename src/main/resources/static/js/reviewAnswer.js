function openModal(reviewCode, businessCode) {
    document.getElementById("reviewModal").style.display = "block";

    // 리뷰 코드 & 사업자 코드 hidden 필드에 설정
    document.getElementById("hiddenReviewCode").value = reviewCode;
    document.getElementById("hiddenBusinessCode").value = businessCode;

    console.log("로드된 리뷰 데이터:", testReview);


    // 리뷰 목록에서 해당 리뷰 객체 찾기
    const targetReview = testReview.find(r => r.reviewCode === reviewCode);
    if (!targetReview) {
        console.error("리뷰를 찾을 수 없습니다.");
        return;
    }

    document.getElementById("reviewReply").value = targetReview.reviewReply;

    // 고객 리뷰 내용 채우기
    document.getElementById("reviewContent").value = targetReview.reviewContent;

    // 별점 체크 표시 (읽기 전용)
    document.querySelectorAll('input[name="rating"]').forEach(input => {
        input.checked = Number(input.value) === targetReview.rating;
    });

    const container = document.querySelector('.imageContainer');
    container.innerHTML = "";

    if (targetReview.reviewImages && targetReview.reviewImages.length > 0) {
        targetReview.reviewImages.forEach(img => {
            const div = document.createElement('div');
            div.classList.add("image-preview");

            const image = document.createElement('img');
            image.src = img.reviewImagePath.startsWith("/") ? img.reviewImagePath : "/" + img.reviewImagePath;
            image.classList.add("preview-img");

            div.appendChild(image);
            container.appendChild(div);
        });
    }
}

function closeModal() {
    document.getElementById("reviewModal").style.display = "none";
}





//완수-신고기능에 필요
function reportReview(reviewCode) {
    // CSRF 토큰 가져오기 (페이지 내에 CSRF 토큰이 hidden input으로 포함되어 있어야 함)
    const csrfToken = document.querySelector('input[name="_csrf"]').value;

    fetch(`/business/review/report?reviewCode=${reviewCode}`, {
        method: "POST",
        headers: {
            "X-CSRF-TOKEN": csrfToken
        }
    })
        .then(response => {
            if (response.ok) {
                alert("신고가 접수되었습니다.");
                location.reload(); // 신고 수 반영을 위해 새로고침
            } else {
                alert("신고 처리 중 오류가 발생했습니다.");
            }
        })
        .catch(error => {
            console.error("신고 에러: ", error);
            alert("신고 처리 중 오류가 발생했습니다.");
        });
}