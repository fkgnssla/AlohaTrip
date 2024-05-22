<script setup>
import {ref, onMounted, watch} from "vue";
import PlanDetailItem from "@/components/plan/item/PlanDetailItem.vue";
import { KakaoMap, KakaoMapPolyline, KakaoMapMarker } from 'vue3-kakao-maps';
import { useRoute, useRouter } from "vue-router";
import { getSidoAddress, getGugunAddress } from "@/api/address" 
import { getAttractionList } from "@/api/attraction"
import { getPlanDetail, addRoute, deleteRoute } from "@/api/plan"

const route = useRoute();
const router = useRouter();
const { planId } = route.params;
const planDetail = ref()

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
  getSidoAddress(
      (response) => { searchAreaOptions.value = response.data; },
      (error) => console.error("Error fetching data:", error)
  );
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

const handleDelete = (planAttractionId) => {
  if (confirm('경로를 삭제하시겠습니까?')) {
    onDeleteRoute(planAttractionId);
  }
};

const onDeleteRoute = (planAttractionId) => {
  deleteRoute(
    planAttractionId,
    (res) => {
        console.log("삭제완료")
        onPlanDetail()
    },
    error => console.log(error)
  )
}

const movePlanList = () => {
  router.push({ name: "planList" });
}

//AttractionView.vue
const searchAreaCity = ref("")
const searchAreaOptions = ref([]);
const searchAreaTown = ref("")
const searchAreaTownOptions = ref([]);
const searchContentId = ref("")
const searchMarkers = ref([]); //근처 관광지 마커

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
  getAttractionList(searchAreaCity.value, searchAreaTown.value, searchContentId.value, "",
    response => {
      searchMarkers.value = response.data.map(marker => ({
      ...marker,
      showInfo: false // 각 마커의 기본값으로 false 설정
      }));
    },
    error => console.log(error)
  );
});

// 모달
const showModal = ref(false);
const clickMarker = ref();
const planMemo = ref("");

const openModal = (marker) => {
  clickMarker.value = marker;
  showModal.value = true; // 모달 열기
};

const closeModal = () => {
    showModal.value = false;
    planMemo.value = '';
};


const onAddRoute = () => {
    console.log('경로 추가:', clickMarker.value.title);
    console.log("order 최대값:", maxOrder);

    const newRoute = {
        "planId": planDetail.value.planId,
        "contentId": clickMarker.value.contentId,
        "order": maxOrder.value + 1,
        "memo": planMemo.value
    }

    console.log(JSON.stringify(newRoute))
    addRoute(
      newRoute,
      res => {
        console.log("추가")
        onPlanDetail()
        closeModal()
      },
      err => console.log(err)
    )
};
</script>

<template>
    <div class="container p-3">
        <div class="row">
          <div class="col-9">
            <form class="d-flex my-3 searchGroup" onsubmit="return false;" role="search">
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
          </div>
          <div class="col-3 my-3">
            <a href="#" class="btn btn-primary me-5" @click="movePlanList">여행 목록 보기</a>
          </div>
            
        </div>

        <div class="row p-5 m-5" v-if="loading">
          <h3>여행 계획을 불러오고 있습니다..</h3>
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
                        @onClickKakaoMapMarker="openModal(marker)"
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

        <!-- 모달 -->
      <div v-if="showModal" class="modal" @click.self="closeModal">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <!-- 모달 헤더 -->
            <div class="modal-header">
              <h5 class="modal-title">경로 추가하기</h5>
              <button type="button" class="btn-close" @click="closeModal"></button>
            </div>
            
            <!-- 모달 바디 -->
            <div class="modal-body">
              <!-- 지명 -->
              <div class="mb-3">
                <label for="planTitle" class="form-label">지명</label>
                <input type="text" class="form-control" id="planTitle" :value="clickMarker.title">
              </div>
              <!-- 주소 -->
              <div class="mb-3">
                <label for="planAddr" class="form-label">주소</label>
                <input type="text" class="form-control" id="planAddr" :value="clickMarker.addr1">
              </div>
              <!-- 메모 -->
              <div class="mb-3">
                <label for="planMemo" class="form-label">메모</label>
                <input type="text" class="form-control" id="planMemo" v-model="planMemo">
              </div>
              
            </div>
            
            <!-- 모달 푸터 -->
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" @click="closeModal">취소</button>
              <button type="button" class="btn btn-primary" @click="onAddRoute">저장</button>
            </div>
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

/* 모달 스타일 (부트스트랩 클래스 사용) */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-dialog {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.modal-content {
  padding: 20px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #ddd;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>