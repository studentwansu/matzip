function openModal(reviewCode) {
     document.getElementById("reviewModal").style.display = "block";
    const formContainer  = document.getElementById("modifyForm");

    const targetReview = reviews.find(r => r.reviewCode === reviewCode);

    // 별점 체크
    document.querySelectorAll('input[name="rating"]').forEach(input => {
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
