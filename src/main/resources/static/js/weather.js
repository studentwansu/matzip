// const CITY = 'Seoul'; // 원하는 도시 입력
let currentCoords = null;

function testFunc() {
    myLocation2();
    document.addEventListener('locationReady', async function(event) {
        currentCoords = event.detail;

        const API_KEY = '03b12504013ff688dca9c9c6e11188cd';
        const lat = parseFloat(currentCoords.Ma.toFixed(2));
        const lon = parseFloat(currentCoords.La.toFixed(2));
        const apiURL = `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric`;
        try {
            const response = await axios.get(`${apiURL}`);
            const data = response.data;
            const test = data.weather[0].main;
            console.log(data);
            console.log(test);
        } catch (error) {
            console.error(error);
        }
    });

}