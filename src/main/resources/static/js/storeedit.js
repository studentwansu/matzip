// 메뉴 추가버튼
const $menu = document.getElementById("menu");
let num = $menu.querySelectorAll("tr").length;
function addMenu() {
    let newMenu = $menu.insertRow();
    let input1 = newMenu.insertCell(0);
    let input2 = newMenu.insertCell(1);
    let input3 = newMenu.insertCell(2);
    let input4 = newMenu.insertCell(3);

    num++;

    // 셀 내부에 직접 <input/> 태그 추가
    input1.innerHTML = `<input type="radio" id="mainMenu${num}" name="mainMenu" value=""/>`
    input2.innerHTML = `<input class="store-input-small" type="text" id="menuName${num}" name="menuName" placeholder="메뉴명" onInput="this.closest('tr').querySelector('input[type=radio]').value = this.value"/>`;
    input3.innerHTML = '------- '
    input4.innerHTML = `<input class="store-input-small" type="text" id="menuPrice${num}" name="menuPrice" placeholder="가격"/>원`;
}

// 메뉴 삭제버튼

function deleteMenu() {
    if (num > 2) {
        $menu.deleteRow($menu.rows.length - 1);
        num--;
    } else
        alert("더 이상 삭제할 수 없습니다!");
}

document.getElementById('custom-button').addEventListener('click', function () {
    document.getElementById('real-file').click(); // 숨겨진 파일 input 클릭
});

document.getElementById('real-file').addEventListener('change', function () {
    const fileInput = this;
    const fileNameDisplay = document.getElementById('file-name');

    if (fileInput.files.length > 0) {
        const names = Array.from(fileInput.files).map(f => f.name).join(', ');
        fileNameDisplay.textContent = names;
    } else {
        fileNameDisplay.textContent = '선택된 파일 없음';
    }
});

function removeEmptyKeywords() {
    // 키워드2~5 중에서 값이 비어있는 입력 필드 비활성화
    for (let i = 2; i <= 5; i++) {
        let keywordInput = document.getElementById('restaurantKeyword' + i);
        if (keywordInput.value.trim() === '') {
            keywordInput.disabled = true; // 비활성화하면 폼 제출 시 전송되지 않음
        }
    }
    return true; // 폼 제출 계속 진행
}
