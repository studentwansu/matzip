async function currentWeather() {
    myLocation2();
    document.addEventListener('locationReady', async function (event) {
        MyApp.currentCoords = event.detail;
        const API_KEY = '7b796a5a1863f135b688aca260fcc97b';
        const lat = parseFloat(MyApp.currentCoords.Ma.toFixed(2));
        const lon = parseFloat(MyApp.currentCoords.La.toFixed(2));
        const apiURL = `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric`;

        try {
            const response = await axios.get(apiURL);
            const weatherKeyword = response.data.weather[0].main;  // 현재 날씨 정보

            const weatherInputValue = document.querySelector('input[name="recommendKeywords"]');
            const weatherInputValue2 = document.querySelector('input[name="weatherCondition"]');
            weatherInputValue.value = weatherKeyword;
            weatherInputValue2.value = weatherKeyword;


            // CSRF 토큰 가져오기
            const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
            const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

            // 해더 설정
            const headers = {
                'Content-Type': 'application/json'
            };
            headers[csrfHeader] = csrfToken;

            // 해시태그와 음식 정보를 동시에 요청
            const hashtagPromise = axios.post("/weather/hashtags", {weatherKeyword: weatherKeyword}, {headers: headers});

            const foodPromise = axios.post("/weather", {weatherKeyword: weatherKeyword}, {headers: headers});

            // 두 요청이 모두 완료될 때까지 기다림
            const [hashtagResponse, foodResponse] = await Promise.all([hashtagPromise, foodPromise]);

            // 해시태그 처리
            const hashtagData = hashtagResponse.data;
            let hashtags = "";
            hashtags += hashtagData;
            document.querySelector('p[name="weatherHashtags"]').innerHTML = hashtags;

            // 음식 목록 처리
            const foodData = foodResponse.data;
            let foodListHtml = "";
            foodData.forEach(food => {
                foodListHtml += `
                <form action="/search">
                    <div class="food-item">
                        <button style="text-decoration: none; color: inherit; display: block; cursor: pointer; background: none;" type="submit">
                            <img class="main-img" src="${food.keywordImgPath}" alt="${food.keyword}" width="100">
                            <p style="font-size: 1.6vh;">${food.keyword}</p>
                            <input type="hidden" name="keyword" value="${food.keyword}">
                        </button>
                    </div>
                </form>`;
            });
            document.querySelector(".food-list").innerHTML = foodListHtml;

        } catch (error) {
            console.error("날씨 정보 또는 추천 데이터를 가져오는 데 실패했습니다:", error);
            // 에러 표시
            document.querySelector(".food-list").innerHTML = "<p>날씨 정보를 가져올 수 없습니다. 나중에 다시 시도해주세요.</p>";
        }
    });
}

