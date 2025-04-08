window.openWriteModal = function openWriteModal(userCode, restaurantCode, reservationCode) {
    document.getElementById("reviewWriteModal").style.display = "block";
    document.getElementById("writeUserCode").value = userCode;
    document.getElementById("writeRestaurantCode").value = restaurantCode;
    document.getElementById("writeReservationCode").value = reservationCode;

    // 이미지 미리보기 초기화
    document.getElementById("writeImagePreview").innerHTML = '';
}

function closeWriteModal() {
    document.getElementById("reviewWriteModal").style.display = "none";
}

function previewWriteImages(input) {
    const previewContainer = document.getElementById("writeImagePreview");
    previewContainer.innerHTML = '';

    const files = input.files;
    if (files.length > 3) {
        alert("이미지는 최대 3장까지 업로드할 수 있습니다.");
        input.value = "";
        return;
    }

    for (let file of files) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const div = document.createElement("div");
            div.className = "image-preview";

            const img = document.createElement("img");
            img.src = e.target.result;
            img.className = "preview-img";
            div.appendChild(img);

            previewContainer.appendChild(div);
        };
        reader.readAsDataURL(file);
    }
}

