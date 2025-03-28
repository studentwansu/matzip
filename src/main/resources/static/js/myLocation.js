let geocoder = new kakao.maps.services.Geocoder();
window.MyApp = window.MyApp || {};


function myLocation2() {
    navigator.geolocation.getCurrentPosition(function (position) {
        // position은 현재 사용자의 위치 정보를 가지고 있음
        // 이 정보를 kakao.maps.LatLng 객체로 변환해야 함
        let coords = new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude);
        MyApp.currentCoords = coords;
        // 변환된 좌표로 상세 주소 검색
        const locationReadyEvent = new CustomEvent('locationReady', {
            detail: MyApp.currentCoords
        });
        console.log(locationReadyEvent);
        document.dispatchEvent(locationReadyEvent);
    });
}

