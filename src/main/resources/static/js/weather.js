async function currentWeather() {
    myLocation2();
    document.addEventListener('locationReady', async function(event) {
        MyApp.currentCoords = event.detail;
        // console.log(event.detail);
        const API_KEY = '03b12504013ff688dca9c9c6e11188cd';
        const lat = parseFloat(MyApp.currentCoords.Ma.toFixed(2));
        const lon = parseFloat(MyApp.currentCoords.La.toFixed(2));
        const apiURL = `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric`;

        try {
            const response = await axios.get(apiURL);
            const weatherKeyword = response.data.weather[0].main;  // 현재 날씨 정보

            console.log("현재 날씨:", weatherKeyword);

            $.ajax({
                type: "POST",
                url: "/weather",
                contentType: "application/json",
                data: JSON.stringify({ weatherKeyword: weatherKeyword }),
                beforeSend: function(xhr) {
                    const csrfToken = $("meta[name='_csrf']").attr("content");
                    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function (data) {
                    console.log("서버 응답:", data);

                    // 음식 리스트를 동적으로 추가
                    let foodListHtml = "";
                    data.recommendKeywords.forEach(food => {
                        foodListHtml += `
                <div class="food-item">
                    <img src="${food.keywordImgPath}" alt="${food.keyword}" width="100">
                    <p>${food.keyword}</p>
                </div>
            `;
                    });

                    // .food-list에 동적으로 생성된 HTML 삽입
                    $(".food-list").html(foodListHtml);
                },
                error: function (error) {
                    console.error("음식 추천을 불러오지 못했습니다.", error);
                }
            });



        } catch (error) {
            console.error("날씨 정보를 가져오는 데 실패했습니다.", error);
        }
    });
}

