function openModal(reviewCode, userCode) {
    document.getElementById("reviewModal").style.display = "block";

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


    const formContainer = document.querySelector(".imageContainer");

    formContainer.innerHTML = ''; // 이미지 초기화

    fetch(`/user/review/imageList/${reviewCode}`)
        .then(res => res.json())
        .then(data => {
            if (data.length === 0) {
                formContainer.innerHTML = '이미지가 없습니다.';
            } else {
                data.forEach(image => {
                    const div = document.createElement('div');
                    div.classList.add("image-preview"); // 이 클래스 중요함!

                    const img = document.createElement('img');
                    img.src = '/' + image.reviewImagePath.replace(/^\/+/, '');
                    img.alt = '리뷰 이미지';
                    img.classList.add("preview-img"); // 스타일 입히려면 꼭 필요!

                    div.appendChild(img);
                    formContainer.appendChild(div);
                });
            }
        });
}

function closeModal() {
    document.getElementById("reviewModal").style.display = "none";
}

window.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".fileInput").forEach(input => {
        input.addEventListener("change", function () {
            const maxFiles = 3;
            if (this.files.length > maxFiles) {
                alert("이미지는 최대 3장까지 업로드할 수 있습니다.");
                this.value = ""; // 초기화
            }
        });
    });
});
