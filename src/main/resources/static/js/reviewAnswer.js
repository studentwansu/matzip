// function openModal(reviewCode, businessCode) {
//     document.getElementById("reviewModal").style.display = "block";
//     document.getElementById("hiddenReviewCode").value = reviewCode;
//     document.getElementById("hiddenBusinessCode").value = businessCode;
//     // reviewCode 값을 hidden 필드에 설정
//     const hiddenReviewCode = document.getElementById("hiddenReviewCode");
//     hiddenReviewCode.value = reviewCode;
//
//     console.log("리뷰코드:", document.getElementById("hiddenReviewCode").value);
//     console.log("비즈니스코드:", document.getElementById("hiddenBusinessCode").value);
//     console.log("답변내용:", document.getElementById("replyContent").value);
//
//     console.log("수정할 리뷰 코드:", reviewCode);
//
//     // 리뷰 목록에서 해당 리뷰 코드를 가진 객체를 찾음
//     const targetReview = testReview.find(r => r.reviewCode === reviewCode);
//     console.log("test");
//     if (!targetReview) {
//         console.error("리뷰를 찾을 수 없습니다.");
//         return;
//     }
//
//     console.log("수정할 리뷰:", targetReview);
//
//     // 별점 체크
//     document.querySelectorAll('input[name="rating"]').forEach(input => {
//         input.checked = Number(input.value) === targetReview.rating;
//     });
//
//     // 리뷰 내용을 폼에 넣기
//     const reviewContent = document.getElementById("reviewContent");
//     reviewContent.value = targetReview.reviewContent;
//
//
//     const formContainer = document.querySelector(".imageContainer");
//
//     formContainer.innerHTML = ''; // 이미지 초기화
//
//     fetch(`/user/review/imageList/${reviewCode}`)
//         .then(res => res.json())
//         .then(data => {
//             if (data.length === 0) {
//                 formContainer.innerHTML = '이미지가 없습니다.';
//             } else {
//                 data.forEach(image => {
//                     const div = document.createElement('div');
//                     div.classList.add("image-preview"); // 이 클래스 중요함!
//
//                     const img = document.createElement('img');
//                     img.src = '/' + image.reviewImagePath.replace(/^\/+/, '');
//                     img.alt = '리뷰 이미지';
//                     img.classList.add("preview-img"); // 스타일 입히려면 꼭 필요!
//
//                     div.appendChild(img);
//                     formContainer.appendChild(div);
//                 });
//             }
//         });
// }

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


    // fetch(`/user/review/imageList/${reviewCode}`)
    //     .then(res => res.json())
    //     .then(data => {
    //         if (data.length === 0) {
    //             formContainer.innerHTML = '<p style="color:gray;">이미지가 없습니다.</p>';
    //         } else {
    //             data.forEach(image => {
    //                 const div = document.createElement('div');
    //                 div.classList.add("image-preview");
    //
    //                 const img = document.createElement('img');
    //                 img.src = '/' + image.reviewImagePath.replace(/^\/+/, '');
    //                 img.alt = '리뷰 이미지';
    //                 img.classList.add("preview-img");
    //
    //                 div.appendChild(img);
    //                 formContainer.appendChild(div);
    //             });
    //         }
    //     })
    //     .catch(err => {
    //         console.error("이미지 불러오기 실패:", err);
    //     });
}




function closeModal() {
    document.getElementById("reviewModal").style.display = "none";
}
//
// window.addEventListener("DOMContentLoaded", () => {
//     document.querySelectorAll(".fileInput").forEach(input => {
//         input.addEventListener("change", function () {
//             const maxFiles = 3;
//             if (this.files.length > maxFiles) {
//                 alert("이미지는 최대 3장까지 업로드할 수 있습니다.");
//                 this.value = ""; // 초기화
//             }
//         });
//     });
// });




// function openModal(reviewCode, businessCode) {
//     document.getElementById("reviewModal").style.display = "block";
//     document.getElementById("replyReviewCode").value = reviewCode;
//     document.getElementById("replyBusinessCode").value = businessCode;
//
//     const review = testReview.find(r => r.reviewCode === reviewCode);
//     if (!review) {
//         alert("리뷰 정보를 찾을 수 없습니다.");
//         return;
//     }
//
//     document.getElementById("customerRating").innerText = review.rating + "점";
//     document.getElementById("customerReviewContent").innerText = review.reviewContent;
//     document.getElementById("replyContent").value = review.reviewReply || "";
//
//     // 이미지 fetch
//     const imageContainer = document.getElementById("customerImages");
//     imageContainer.innerHTML = "";
//     fetch(`/user/review/imageList/${reviewCode}`)
//         .then(res => res.json())
//         .then(images => {
//             if (images.length === 0) {
//                 imageContainer.innerHTML = "<p>등록된 이미지 없음</p>";
//             } else {
//                 images.forEach(img => {
//                     const imgEl = document.createElement("img");
//                     imgEl.src = '/' + img.reviewImagePath.replace(/^\/+/, '');
//                     imgEl.classList.add("preview-img");
//                     imageContainer.appendChild(imgEl);
//                 });
//             }
//         });
// }
