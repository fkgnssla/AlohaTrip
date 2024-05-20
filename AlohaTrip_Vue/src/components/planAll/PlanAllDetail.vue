<script setup>
import {ref, onMounted, watch} from "vue";
import PlanDetailItem from "@/components/planAll/item/PlanDetailItem.vue";
import { KakaoMap, KakaoMapPolyline, KakaoMapMarker } from 'vue3-kakao-maps';
import { useRoute, useRouter } from "vue-router";
import { getAttractionList } from "@/api/attraction"
import { getPlanDetail, addRoute, deleteRoute } from "@/api/plan"

const route = useRoute();
const router = useRouter();
const { planId } = route.params;
const planDetail = ref({
  "planId": 0,
  "title": ""
})

var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
// latitude와 longitude를 추출하여 latLngList 배열에 매핑
const latLngList = ref([]);
const attractions = ref();
const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567
});

const maxOrder = ref()
const loading = ref(true)

onMounted(async () => {
  onPlanDetail();
})

const onPlanDetail = () => {
  loading.value = true;
  getPlanDetail(
    planId,
    (response) => {
        planDetail.value = response.data
        console.log(response.data)

        if(response.data.attractions == null) {
          attractions.value = []
          latLngList.value = []
          maxOrder.value = 0
        } else {
          latLngList.value = planDetail.value.attractions.map(attraction => ({
            lat: attraction.attractionDto.latitude,
            lng: attraction.attractionDto.longitude
          }));
          console.log(latLngList.value)
          coordinate.value.lat = latLngList.value[0].lat;
          coordinate.value.lng = latLngList.value[0].lng;
          
          attractions.value = planDetail.value.attractions.map(marker => ({
              ...marker,
              showInfo: false // 각 마커의 기본값으로 false 설정
          }));

          maxOrder.value = Math.max(...attractions.value.map(attraction => attraction.order));
        }
        console.log("다음 순서: " + (maxOrder.value + 1))

        loading.value = false;
    },
    (error) => console.log(error)

  );
}

// 마커에 마우스를 올렸을 때 인포윈도우 표시 함수
const showInfoWindow = (marker) => {
  marker.showInfo = true; // 마커 객체의 showInfo 상태를 true로 변경하여 인포윈도우 표시
};

// 마커에서 마우스를 내렸을 때 인포윈도우 숨기는 함수
const hideInfoWindow = (marker) => {
  marker.showInfo = false; // 마커 객체의 showInfo 상태를 false로 변경하여 인포윈도우 숨김
};

const movePlanList = () => {
  router.push({ name: "planAllList" });
}
</script>

<template>
    <div class="container p-3">
        <div class="row">
          <div class="col-10 my-3">
            <h2>{{ planDetail.title }}</h2>
          </div>
          <div class="col-2 my-3">
            <a href="#" class="btn btn-primary me-5" @click="movePlanList">뒤로가기</a>
          </div>
        </div>

        <div class="row p-5" v-if="loading">
          <v-progress-linear
            color="green"
            indeterminate
          ></v-progress-linear>
        </div>
        <div class="row" v-else>
            <div class="col-md-6">
                <KakaoMap :lat="coordinate.lat" :lng="coordinate.lng">
                    <KakaoMapPolyline :latLngList="latLngList" :endArrow="true" />
                    <KakaoMapMarker v-for="marker in attractions" :lat="marker.attractionDto.latitude" :lng="marker.attractionDto.longitude"
                        :clickable="true"
                        :image="{
                            imageSrc: imageSrc,
                            imageWidth: 24,
                            imageHeight: 28,
                            imageOption: {}
                        }"
                        :infoWindow="{ content: `${marker.attractionDto.title} <br> ${marker.attractionDto.addr1}`, visible: marker.showInfo }"
                        @mouseOverKakaoMapMarker="showInfoWindow(marker)"
                        @mouseOutKakaoMapMarker="hideInfoWindow(marker)">
                    </KakaoMapMarker>
                    <KakaoMapMarker v-for="marker in searchMarkers" :lat="marker.latitude" :lng="marker.longitude" :key="marker.contentId"
                        :clickable="true"
                        :infoWindow="{ content: `${marker.title} <br> ${marker.addr1}`, visible: marker.showInfo }"
                        @mouseOverKakaoMapMarker="showInfoWindow(marker)"
                        @mouseOutKakaoMapMarker="hideInfoWindow(marker)"
                    >
                    </KakaoMapMarker>
                </KakaoMap>
            </div>
            <div class="col-md-6">
                <div class="scroll scroll4">
                  <v-container>
                    <v-row justify="start">
                        <v-timeline align="start" side="end">
                          <PlanDetailItem v-for="attraction in attractions" :attraction="attraction"
                            @onDeleteRoute="handleDelete"/>
                        </v-timeline>
                    </v-row>
                  </v-container>
                </div>
            </div>
        </div>
  </div>
    
</template>

<style scoped>
.scroll4::-webkit-scrollbar {
  width: 10px;
}
.scroll4::-webkit-scrollbar-thumb {
  background: #666;
  border-radius: 20px;
}

.scroll4::-webkit-scrollbar-track {
  background: #ddd;
  border-radius: 20px;
}
.scroll {
    width: 40vw;     /* 브라우저 창의 너비의 50% */
  height: 70vh;    /* 브라우저 창의 높이의 70% */
  overflow: auto;
  float: left;
  margin: 0 10px;
}
/* .content {
  height: 2500px;
} */
</style>