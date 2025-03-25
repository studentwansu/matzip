function openModal() {
     document.getElementById("reviewModal").style.display = "block";
    const formContainer  = document.getElementById("test");
    // container.innerHTML = ''; // 기존 내용 초기화 (필요 시)

    const targetReview = reviews.find(r => r.reviewCode === reviewCode);

    // 별점 체크
    document.querySelectorAll('input[name="reviewStar"]').forEach(input => {
        input.checked = Number(input.value) === targetReview.rating;
    });


    console.log(targetReview);
    if (targetReview && targetReview.reviewImages) {
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



// function openModal(reviewc) {
//     // console.log(reviewc);
//     document.getElementById("reviewModal").style.display = "block";
//
//     const targetReview = revieww;
//     //     // .find(r => r.reviewCode === reviewc);
//     // console.log(targetReview); // 콘솔 찍어서 확인
//     //
//     // if (!targetReview) {
//     //     alert('리뷰를 찾을 수 없습니다.');
//     //     return;
//     // }
//
//     // 별점 채우기 (필수 수정사항 있음)
//     document.querySelectorAll('input[name="reviewStar"]').forEach(input => {
//         input.checked = Number(input.value) === Number(targetReview.rating);
//     });
//
//     // 리뷰 내용 채우기
//     document.getElementById('reviewContents').value = targetReview.reviewContent || '';
//
//     // 이미지 처리 (추가 시)
//     const formContainer = document.getElementById("myform");
//     formContainer.querySelectorAll(".review-img").forEach(el => el.remove());
//
//     if (targetReview.reviewImages) {
//         targetReview.reviewImages.forEach(image => {
//             const div = document.createElement('div');
//             div.classList.add("review-img"); // 삭제 편의
//
//             const img = document.createElement('img');
//             img.setAttribute('src', '/' + image.reviewImagePath);
//             img.setAttribute('alt', '리뷰 이미지');
//
//             div.appendChild(img);
//             formContainer.appendChild(div);
//         });
//     }
// }
