function openModal(reviewCode, userCode) {
    document.getElementById("reviewModal").style.display = "block";
    const formContainer = document.getElementById("test");

    // reviewCode 값을 hidden 필드에 설정
    const hiddenReviewCode = document.getElementById("hiddenReviewCode");
    const hiddenUserCode = document.getElementById("hiddenUserCode");
    hiddenReviewCode.value = reviewCode;
    hiddenUserCode.value = userCode;

    console.log("수정할 리뷰 코드:", reviewCode);
    console.log("수정할 유저 코드:", userCode);

    // 리뷰 목록에서 해당 리뷰 코드를 가진 객체를 찾음
    const targetReview = testReview.find(r => r.reviewCode === reviewCode);
    console.log("test");
    if (!targetReview) {
        console.error("리뷰를 찾을 수 없습니다.");
        return;
    }

    console.log("수정할 리뷰:", targetReview);

    // 별점 체크
    document.querySelectorAll('input[name="rating"]').forEach(input => {
        input.checked = Number(input.value) === targetReview.rating;
    });

    // 리뷰 내용을 폼에 넣기
    const reviewContent = document.getElementById("reviewContent");
    reviewContent.value = targetReview.reviewContent;

    // 이미지 표시
    if (targetReview.reviewImages && targetReview.reviewImages.length > 0) {
        formContainer.innerHTML = ''; // 기존 내용 초기화
        targetReview.reviewImages.forEach(image => {
            const div = document.createElement('div');
            const img = document.createElement('img');
            img.setAttribute('src', image.reviewImagePath);
            img.setAttribute('alt', '리뷰 이미지');
            div.appendChild(img);
            formContainer.appendChild(div);
        });
    } else {
        formContainer.innerHTML = '이미지가 없습니다.';
    }
}

function closeModal() {
    document.getElementById("reviewModal").style.display = "none";
}

window.onclick = function(event) {
    if (event.target == document.getElementById("reviewModal")) {
        closeModal();
    }
}

