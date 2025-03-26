// const API_KEY = '03b12504013ff688dca9c9c6e11188cd';
// const CITY = 'Seoul'; // 원하는 도시 입력
let apiURL = 'https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=03b12504013ff688dca9c9c6e11188cd&units=metric';
console.log(apiURL);

async function testFunc() {

    try {
        const response = await axios.get(`${apiURL}`);
            const data = response.data;
            const test = data.weather[0].description;
            console.log(data);
            console.log(test);
    } catch (error) {
        console.error(error);
    }

    // axios.get(`${URL}`)
    // .then(response => {
    //     const data = response.data;
    //     const test = data.weather.description;
    //     console.log(`현재 날씨는 ${test} 입니다.`);
        // // 📌 데이터 추출
        // const temperature = data.main.temp;  // 현재 기온 (°C)
        // const humidity = data.main.humidity; // 습도 (%)
        // const weather = data.weather[0].main; // 날씨 상태 (예: Clear, Rain)
        //
        // console.log(`현재 ${CITY}의 날씨는 ${weather}, 온도는 ${temperature}°C, 습도는 ${humidity}%입니다.`);
        //
        // // 🔥 키워드 생성
        // const keyword = generateWeatherKeyword(temperature, weather);
        // console.log(`추천 키워드: ${keyword}`);

}
