<script setup>
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay  } from 'vue3-kakao-maps';
import { ref, onMounted, watch } from "vue";
import { getSidoAddress, getGugunAddress } from "@/api/address" 
import { getAttractionList } from "@/api/attraction"

const searchAreaCity = ref("")
const searchAreaOptions = ref([]);
const searchAreaTown = ref("")
const searchAreaTownOptions = ref([]);
const searchContentId = ref("")
const searchMarkers = ref([]);
const searchKeyword = ref("");
const map = ref();

var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567
});

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

onMounted(() => {
  getSidoAddress(
    (response) => { searchAreaOptions.value = response.data; },
    (error) => console.error("Error fetching data:", error)
  );
})

watch(searchAreaCity, (newValue, oldValue) => {
  console.log("시/도 변경 => ", oldValue, "to", newValue);
  getGugunAddress(
    searchAreaCity.value,
    (response) => { searchAreaTownOptions.value = response.data; },
    (error) => console.error("Error fetching data:", error)
  )
});

watch(searchContentId, (newValue, oldValue) => {
  console.log("관광지 유형 변경 => ", oldValue, "to", newValue);
  
  //관광지 검색
  getAttractionList(searchAreaCity.value, searchAreaTown.value, searchContentId.value, searchKeyword.value,
    response => {
      searchMarkers.value = response.data;

      markersWithShowInfo.value = [];

      markersWithShowInfo.value = searchMarkers.value.map(marker => ({
      ...marker,
      showInfo: false // 각 마커의 기본값으로 false 설정
      }));

      // markersWithShowInfo[0].value 
      console.log(markersWithShowInfo.value[0].latitude)
      coordinate.value.lat = markersWithShowInfo.value[0].latitude
      coordinate.value.lng = markersWithShowInfo.value[0].longitude
    },
    error => console.log(error)
  );
});

const markersWithShowInfo = ref([]);

// 마커에 마우스를 올렸을 때 인포윈도우 표시 함수
const showInfoWindow = (marker) => {
  marker.showInfo = true; // 마커 객체의 showInfo 상태를 true로 변경하여 인포윈도우 표시
};

// 마커에서 마우스를 내렸을 때 인포윈도우 숨기는 함수
const hideInfoWindow = (marker) => {
  marker.showInfo = false; // 마커 객체의 showInfo 상태를 false로 변경하여 인포윈도우 숨김
};
</script>

<template>
  <main>
    <div class="container">
      <div style="height: 70px"></div>  
      <!-- 중앙 center content end -->
      <div class="col-md-9 mx-auto">
        <div class="mt-3 text-center fs-2" role="alert">
          지역 관광 정보
        </div>
        <hr class="center-hr">

        <!-- 관광지 검색 start -->
        <form class="d-flex my-3" onsubmit="return false;" role="search">
          <select id="search-area-city" class="form-select me-2" v-model="searchAreaCity">
            <option value="" selected>시 ・ 도</option>
            <option v-for="option in searchAreaOptions" :key="option.sidoCode" :value="option.sidoCode">
              {{ option.sidoName }}
            </option>
          </select>
          <select id="search-area-town" class="form-select me-2" v-model="searchAreaTown">
            <option value="" selected>검색 할 지역 선택</option>
            <option v-for="option in searchAreaTownOptions" :key="option.gugunCode" :value="option.gugunCode">
              {{ option.gugunName }}
            </option>
          </select>
          <select id="search-content-id" class="form-select me-2" v-model="searchContentId">
            <option value="" selected>관광지 유형</option>
			      <option value="12">관광지</option>
            <option value="14">문화시설</option>
            <option value="15">축제공연행사</option>
            <option value="25">여행코스</option>
            <option value="28">레포츠</option>
            <option value="32">숙박</option>
            <option value="38">쇼핑</option>
            <option value="39">음식점</option>
          </select>
        </form>

        <!-- kakao map start -->
        <KakaoMap @onLoadKakaoMap="onLoadKakaoMap" :lat="coordinate.lat" :lng="coordinate.lng" :draggable="true">
          <KakaoMapMarker v-for="marker in markersWithShowInfo" :lat="marker.latitude" :lng="marker.longitude" :key="marker.contentId"
              :clickable="true"
              :image="{
                imageSrc: imageSrc,
                imageWidth: 24,
                imageHeight: 28,
                imageOption: {}
              }"
              :infoWindow="{ content: `${marker.title} <br> ${marker.addr1}`, visible: marker.showInfo }"
              @mouseOverKakaoMapMarker="showInfoWindow(marker)"
              @mouseOutKakaoMapMarker="hideInfoWindow(marker)"
          >
          </KakaoMapMarker>
        </KakaoMap>
        <!-- kakao map end -->

        <div class="row">
          <table class="table table-striped" style="display: none">
            <thead>
              <tr>
                <th>대표이미지</th>
                <th>관광지명</th>
                <th>주소</th>
                <th>위도</th>
                <th>경도</th>
              </tr>
            </thead>
            <tbody id="trip-list"></tbody>
          </table>
        </div>
        <!-- 관광지 검색 end -->
      </div>
    </div>
  </main>
</template>

<style scoped></style>
