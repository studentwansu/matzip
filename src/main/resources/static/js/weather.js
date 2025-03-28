function currentWeather() {
    myLocation2();
    document.addEventListener('locationReady', async function(event) {
        MyApp.currentCoords = event.detail;

        const API_KEY = '03b12504013ff688dca9c9c6e11188cd';
        const lat = parseFloat(MyApp.currentCoords.Ma.toFixed(2));
        const lon = parseFloat(MyApp.currentCoords.La.toFixed(2));
        const apiURL = `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric`;
        try {
            const response = await axios.get(`${apiURL}`);
            const weatherData = response.data;
            console.log(weatherData.weather[0].main);

            // const weatherInput = document.querySelector('input[name="weatherKeyword"]');
            // weatherInput.value = weatherData.weather[0].main;

        } catch (error) {
            console.error(error);
        }
    });
}