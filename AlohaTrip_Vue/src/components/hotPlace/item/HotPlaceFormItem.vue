<script setup>
import { ref, onMounted } from "vue"
import axios from "axios";
import { useRoute, useRouter } from "vue-router"
import { getAccessToken, getMemberId } from "@/util/storageUtil";
import { createHotPlaceInfo } from "@/api/hotPlace.js";
import { KakaoMap,KakaoMapMarker } from 'vue3-kakao-maps';
import {getHotPlaceInfoDetail, updateHotPlaceInfo} from "@/api/hotPlace.js";

const route = useRoute()
const router = useRouter()
const props = defineProps({ type: String, id : String })
const ImgInfo = ref("");
const ImgChangeBoolean = ref(false);

const map = ref();
const message = ref('');
const checkMarker = ref(false);
const markerLat = ref('');
const markerLng = ref('');
const mapLat = ref(33.450701);
const mapLng = ref(126.570667);

const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;

  kakao.maps.event.addListener(map.value, 'click', function (mouseEvent) {
    // 클릭한 위도, 경도 정보를 가져옵니다
    checkMarker.value = true;
    const latlng = mouseEvent.latLng;
    markerLat.value = latlng.getLat()
    markerLng.value = latlng.getLng()
        
    console.log(markerLat.value)
    console.log(markerLng.value)

  });
};

const hotPlaceInfo = ref({
    hotPlaceId: '1',
    memberId: '1',
    hotPlaceName: "",
    visitedDate : "",
    address: "",
    contents: ""
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
            markerLat.value = data.lat;
            markerLng.value = data.lng;
            mapLat.value = data.lat;
            mapLng.value = data.lng;
            checkMarker.value = true;
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
    formData.append('lat', markerLat.value)
    formData.append('lng', markerLng.value)

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
    formData.append('lat', markerLat.value)
    formData.append('lng', markerLng.value)

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
<div class="contents">
    <div class="kakaoMapDiv">
        <KakaoMap :lat="mapLat" :lng="mapLng" width="100%" height="100%" @onLoadKakaoMap="onLoadKakaoMap">
            <KakaoMapMarker v-if="checkMarker" :lat="markerLat" :lng="markerLng">
            </KakaoMapMarker>
        </KakaoMap>
    </div>
    <div class="formDiv">
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
                <input type="checkbox" v-model="ImgChangeBoolean" @click="setImgInput" class="checkBoxInput">
                <div v-if="!ImgChangeBoolean">
                    <v-file-input disabled @change="selectFile"></v-file-input>
                </div>
                <div v-if="ImgChangeBoolean">
                    <v-file-input  @change="selectFile"></v-file-input>
                </div>
            </div>
        </div>
        <div class="text-start">
            <label for="contents" class="form-label">핫플레이스 상세설명</label>
            <textarea class="form-control" name="contents" v-model="hotPlaceInfo.contents" rows="10"></textarea>
        </div>
        <div class="buttonGather">
            <button type="button" class="btn btnList" @click="moveList">
            취소
            </button>
            <button type="submit" class="btn btnCreate" v-if="type !== 'update'" >
            작성
            </button>
            <button type="submit" class="btn btnUpdate" v-if="type == 'update'">
            수정
            </button>
        </div>
    </form>
    </div>
</div>
</template>

<style scoped>
.contents{
    display: flex;
    justify-content: center;
}
.kakaoMapDiv{
    width: 43%;
    height: 900px;
    margin-right: 5%;
    margin-left: 5%;
}
.formDiv{
    width: 42%;
    height: 900px;
    margin-right: 5%;
}
.checkBoxInput{
    margin-left: 5px;
    height: 10px;
}
.text-start{
    margin-bottom: 20px;;
}
textarea{
    height: 400px;
}
.btnCreate{
    float : right;
    background-color: white;
    border: 1px solid #4AD597;
    border-radius: 10px;
    width: 100px;
    height: 40px;
    color: #4AD597;
    font-weight: bold;
    font-size: 17px;
    margin-right: 10px;
}
.btnUpdate{
    background-color: white;
    border: 1px solid #4AD597;
    border-radius: 10px;
    width: 100px;
    height: 40px;
    color: #4AD597;
    font-weight: bold;
    font-size: 17px;
    float : right;
    margin-right: 10px;
}.btnList{
    background-color: #4AD597;
    border: none;
    border-radius: 10px;
    width: 100px;
    height: 40px;
    margin-right: 10px;
    color: white;
    font-weight: bold;
    font-size: 17px;
    float : right;
}
</style>