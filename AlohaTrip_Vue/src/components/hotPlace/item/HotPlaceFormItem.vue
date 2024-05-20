<script setup>
import { ref } from "vue"
import axios from "axios";
import { useRoute, useRouter } from "vue-router"
import { getAccessToken, getMemberId } from "@/util/storageUtil";
import { createHotPlaceInfo } from "@/api/hotPlace.js";
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay  } from 'vue3-kakao-maps';

const route = useRoute()
const router = useRouter()
const props = defineProps({ type: String })
const tmpImgInfo = ref(null);

var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567
});

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
};

const hotPlaceInfo = ref({
    memberId: getMemberId(),
    hotPlaceName: "",
    visitedDate : "",
    address: "",
    ImgAddress: "",
    contents: "",
    lat: 0.0,
    lng: 0.0
})

function selectFile(file) { 
    tmpImgInfo = file.target.files[0];
}

function onSubmit() {
    console.log(props.type)
    console.log(props.type === "update")
    props.type === "update" ? onUpdateTripInfo() : onCreateTripInfo()
}

function onCreateTripInfo() {
    hotPlaceInfo.value.visitedDate = hotPlaceInfo.value.visitedDate+"T00:00:00"
    console.log(hotPlaceInfo.value)
    
    createHotPlaceInfo(
        hotPlaceInfo.value,
        tmpImgInfo,
       (response) => {
          let msg = "HotPlace글등록 처리시 문제 발생했습니다."
          if (response.status == 200) msg = "글등록이 완료되었습니다."
          alert(msg)
        moveList()
        },
        (error) => console.log(error)
    )
}

function moveList() {
  router.replace({ name: "hotPlaceList", params: { page: 1 } })
}
</script>

<template>
    <!-- <KakaoMap @onLoadKakaoMap="onLoadKakaoMap" :lat="coordinate.lat" :lng="coordinate.lng" :draggable="true" class="kakaoMapObj">
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
    </KakaoMap> -->
    <form @submit.prevent="onSubmit">
        <div class="text-start">
            <label for="HotPlaceName" class="form-label">핫플레이스 이름</label>
            <input type="text" class="form-control" name="HotPlaceName" v-model="hotPlaceInfo.hotPlaceName" placeholder="핫플레이스 이름" />
        </div>
        <div class="text-start">
            <label for="visitedDate" class="form-label">다녀온 날짜</label>
            <input class="form-control" type="date" name="visitedDate" v-model="hotPlaceInfo.visitedDate" />
        </div>
        <div class="text-start">
            <label for="address" class="form-label">주소</label>
            <input type="text" class="form-control" name="address" v-model="hotPlaceInfo.address" placeholder="핫플레이스 주소" />
        </div>
        <div class="text-start">
            <label for="ImgAddress" class="form-label">핫플레이스 이미지</label>
            <v-file-input @change="selectFile"></v-file-input>
        </div>
        <div class="text-start">
            <label for="contents" class="form-label">핫플레이스 상세설명</label>
            <textarea class="form-control" name="contents" v-model="hotPlaceInfo.contents" rows="10"></textarea>
        </div>
        <div>
            <input type="text" class="form-control" name="lat" placeholder="핫플레이스 lat" v-model="hotPlaceInfo.lat"/>
            <input type="text" class="form-control" name="lng" placeholder="핫플레이스 lng" v-model="hotPlaceInfo.lng"/>
        </div>
        <div class="buttonGather">
            <button type="submit" class="btn btnCreate" v-if="type !== 'update'" >
            작성
            </button>
            <button type="submit" class="btn btnUpdate" v-else>
            수정
            </button>
            <button type="button" class="btn btnList" @click="moveList">
            취소
            </button>
        </div>
    </form>
</template>

<style scoped>
</style>