<script setup>
import { ref, onMounted } from "vue"
import axios from "axios";
import { useRoute, useRouter } from "vue-router"
import { getAccessToken, getMemberId } from "@/util/storageUtil";
import { createHotPlaceInfo } from "@/api/hotPlace.js";
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from 'vue3-kakao-maps';
import {getHotPlaceInfoDetail, updateHotPlaceInfo} from "@/api/hotPlace.js";

const route = useRoute()
const router = useRouter()
const props = defineProps({ type: String, id : String })
const ImgInfo = ref("");
const ImgChangeBoolean = ref(false);

// var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
// const coordinate = ref({
//   lat: 37.566826,
//   lng: 126.9786567
// });

// const onLoadKakaoMap = (mapRef) => {
//   map.value = mapRef;
// };

const hotPlaceInfo = ref({
    hotPlaceId: '1',
    memberId: '1',
    hotPlaceName: "",
    visitedDate : "",
    address: "",
    contents: "",
    lat: 0.0,
    lng: 0.0
})

onMounted(() => {
    props.type === "update" ? onSettingForm() : ''
});

function onSettingForm() { 
    console.log(props.id)
    getHotPlaceInfoDetail(
        props.id,
        ({ data }) => {
            hotPlaceInfo.value = data;
            console.log(hotPlaceInfo.value)
        },
        (error) => {
            console.log(error);
       }
    );
    console.log("테스트 ====>")
    console.log(hotPlaceInfo.value.visitedDate)
    hotPlaceInfo.value.visitedDate = hotPlaceInfo.value.visitedDate[0]+'-'+hotPlaceInfo.value.visitedDate[1]+'-'+hotPlaceInfo.value.visitedDate[2]
}

function selectFile(file) { 
    ImgInfo.value = file.target.files[0];
}

function onSubmit() {
    console.log(props.type)
    console.log(props.type === "update")
    props.type === "update" ? onUpdateHotPlaceInfo() : onCreateHotPlaceInfo()
}

function onCreateHotPlaceInfo() {
    hotPlaceInfo.value.visitedDate = hotPlaceInfo.value.visitedDate+" 00:00:00"
    var formData = new FormData();
    formData.append('memberId', hotPlaceInfo.value.memberId)
    formData.append('hotPlaceName', hotPlaceInfo.value.hotPlaceName)
    formData.append('visitedDate', hotPlaceInfo.value.visitedDate)
    formData.append('address', hotPlaceInfo.value.address)
    formData.append('ImgInfo', ImgInfo.value)
    formData.append('contents', hotPlaceInfo.value.contents)
    formData.append('lat', hotPlaceInfo.value.lat)
    formData.append('lng', hotPlaceInfo.value.lng)

    createHotPlaceInfo(
        formData,
       (response) => {
          let msg = "HotPlace글 등록 처리시 문제 발생했습니다."
          if (response.status == 200) msg = "글등록이 완료되었습니다."
          alert(msg)
        moveList()
        },
        (error) => console.log(error)
    )
}

function onUpdateHotPlaceInfo() {
    hotPlaceInfo.value.visitedDate = hotPlaceInfo.value.visitedDate+" 00:00:00"
    var formData = new FormData();
    formData.append('hotPlaceId', hotPlaceInfo.value.hotPlaceId)
    formData.append('hotPlaceName', hotPlaceInfo.value.hotPlaceName)
    formData.append('visitedDate', hotPlaceInfo.value.visitedDate)
    formData.append('address', hotPlaceInfo.value.address)
    if (ImgChangeBoolean ) { 
        formData.append('ImgInfo', ImgInfo.value)
    }
    formData.append('contents', hotPlaceInfo.value.contents)
    formData.append('lat', hotPlaceInfo.value.lat)
    formData.append('lng', hotPlaceInfo.value.lng)

    updateHotPlaceInfo(
        formData,
       (response) => {
          let msg = "HotPlace글 업데이트 처리시 문제 발생했습니다."
          if (response.status == 200) msg = "HotPlace 업데이트가 완료되었습니다."
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
        <div v-if="props.type == 'regist'">
            <div class="text-start">
                <label for="ImgAddress" class="form-label">핫플레이스 이미지</label>
                <v-file-input @change="selectFile"></v-file-input>
            </div>
        </div>
        <div v-if="props.type == 'update'">
            <div class="text-start">
                <label for="ImgAddress" class="form-label">핫플레이스 이미지 변경</label>
                <input type="checkbox" v-model="ImgChangeBoolean" @click="setImgInput">
                <div v-if="ImgChangeBoolean">
                    <v-file-input @change="selectFile"></v-file-input>
                </div>
            </div>
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