<script setup>
import { KakaoMap, KakaoMapMarker} from 'vue3-kakao-maps';
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getSidoAddress, getGugunAddress } from "@/api/address" 
import { getAttractionList } from "@/api/attraction"
import { getHotPlaceInfoDetail, postDeletePost } from "@/api/hotPlace.js";
import { getMemberId } from "@/util/storageUtil"
import { createLike, deleteLike } from "@/api/like"

const route = useRoute();
const router = useRouter();
const { id } = route.params;
const hotPlaceInfo = ref([]);
const markerLat = ref('');
const markerLng = ref('');
const mapLat = ref(33.450701);
const mapLng = ref(126.570667);
const writerId = ref();
const isLike = ref();
const memberId = ref();

onMounted(() => {
    memberId.value = getMemberId();
    hotPlaceDetail();
});

var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 

const hotPlaceDetail = async () => {
    getHotPlaceInfoDetail(
        id,
        ({ data }) => {
            hotPlaceInfo.value = data;
            markerLat.value = data.lat;
            markerLng.value = data.lng;
            mapLat.value = data.lat;
            mapLng.value = data.lng;
            writerId.value = data.memberId;
            isLike.value = data.likeFlag;
            console.log(hotPlaceInfo.value)
        },
        (error) => {
            console.log(error);
       }
    );
};


function moveUpdate() { 
    router.push({name: 'hotPlaceUpdate', params: { id: id }});
}
function deletePost() { 
    postDeletePost(
        id,
        (response) => {
            if (response.status == 200) moveList();
        },
        (error) => {
        console.log(error);
        }
    );
}
function moveList() {
  router.push({name: 'bragOfHotPlace'});
}

const clickHeart = () => {
    const like = {
        "memberId": getMemberId(),
        "hotPlaceId": hotPlaceInfo.value.hotPlaceId
    }

    if (isLike.value == true) {
        deleteLike(
            hotPlaceInfo.value.hotPlaceId,
            response => {
                console.log(response)
                hotPlaceDetail();
            },
            err => console.log(err)
        )
    } else {
        createLike(
            like,
            response => {
                console.log(response)
                hotPlaceDetail();
            },
            err => console.log(err)
        )
    }
}

</script>

<template>
    <div class="hotPlaceBanner">
      <div class="position-absolute top-50 start-50 translate-middle">Brag of Hotplace : Detail</div>
    </div>
    <div class="container">
        <div class="imgAndMapDiv">
            <div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner imgAndMapCarousel">
                    <div class="carousel-item active mapDiv">
                        <KakaoMap :lat="mapLat" :lng="mapLng" width="100%" height="100%" @onLoadKakaoMap="onLoadKakaoMap">
                            <KakaoMapMarker :lat="markerLat" :lng="markerLng">
                            </KakaoMapMarker>
                        </KakaoMap>
                    </div>
                    <div>
                        <div class="carousel-item active" data-bs-interval="2000">
                            <div class="carouselItemBlock">
                                <img src="@/assets/img/HotPlace/testImg.png" class="d-block w-100" alt="...">
                            </div>
                        </div>
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <div class="divisionLineColumn"></div>
        <div class="postInfoDiv">
            <div class="postInfo">
                <div class="hotPlaceAddress">
                    <div>
                        {{ hotPlaceInfo.address }}
                    </div>
                </div>
                <div class="postTitle">
                    {{ hotPlaceInfo.hotPlaceName }}
                </div>
                <div class="divisionLineRow"></div>
                <div class="writerAndCommentCount">
                    {{ hotPlaceInfo.writerName }} | {{ hotPlaceInfo.views }} views
                </div>
                <div class="visitedDate">
                    <!-- {{ hotPlaceInfo.visitedDate[0] }}.{{ hotPlaceInfo.visitedDate[1] }}.{{ hotPlaceInfo.visitedDate[2] }} -->
                </div>
                <div class="postContents">
                    {{ hotPlaceInfo.contents }}
                </div>
                <div class="createdDate">
                    <!-- {{ hotPlaceInfo.createdDate[0] }}.{{ hotPlaceInfo.createdDate[1] }}.{{ hotPlaceInfo.createdDate[2] }} {{ hotPlaceInfo.createdDate[3] }}:{{ hotPlaceInfo.createdDate[4] }}:{{ hotPlaceInfo.createdDate[5] }} -->
                </div>
            </div>
            <div class="divisionLineRow"></div>
            <div class="likeDiv">
                <img src="@/assets/img/common/heartFilled.png" @click="clickHeart" v-if="isLike" height="20px" style="margin-right: 5px;">
                <img src="@/assets/img/common/heart.png" @click="clickHeart" v-else height="20px" style="margin-right: 5px;">
                {{ hotPlaceInfo.likes }} people like this
            </div>
            <div class="BtnGroup" >
                <button type="button" class="btn updateArticle" @click="moveUpdate" v-show="writerId === getMemberId()">
                수정
                </button>
                <button type="button" class="btn deleteArticle" @click="deletePost" v-show="writerId === getMemberId()">
                삭제
                </button>
                <button type="button" class="btn moveToList" @click="moveList">
                목록 보기
                </button>
            </div>
        </div>
    </div>
</template>

<style scoped>
.hotPlaceBanner{
  background-image: linear-gradient( rgba(0,0,0, 0.55), rgba(0,0,0, 0.55) ),url("@/assets/img/HotPlace/bannerImg.png");
  background-repeat : no-repeat;
  background-size : cover;
  height: 200px;
  color: #7AEBB8;
  font-weight: bold;
  font-size: 35px;
  position: relative;
}
.container{
    margin-top: 100px;
    margin-bottom: 50px;
    height: 900px;
    display: flex;
}
.mapDiv{
    background-color: red;
    width: 100%;
    height: 900px;
}
.imgAndMapDiv{
    width: 45%;
    height: 900px;
}
.carouselItemBlock{
    height: 900px;
    display: table-cell;
    vertical-align: middle;
}
.divisionLineColumn{
    background-color: lightgrey;
    width: 2px;
    height: 100%;
    margin: 0 auto;
}
.postInfoDiv{
    width: 45%;
    height: 100%;
}
.postInfo{
    width: 100%;
    height: 86%;
}
.hotPlaceAddress{
    color: #5ad39c;
    font-size: 20px;
    font-weight: bold;
}
.postTitle{
    font-size: 50px;
    font-weight: bold;
}
.divisionLineRow{
    background-color: lightgrey;
    width: 100%;
    height: 1px;
    margin-bottom: 10px
}
.writerAndCommentCount{
    font-size: 15px;
    color: grey;
}
.visitedDate{
    font-size: 15px;
    color: grey;
    margin-bottom: 30px;
}
.postContents{
    height: 75%;
    font-size: 23px;
}
.createdDate{
    font-size: 15px;
    color: grey;
}
.likeDiv{
    height: 5%;
    width: 100%;
}
.BtnGroup{
    width: 100%;
    padding-top: 5%;
}
.updateArticle{
    margin-left: 45%;
    background-color: white;
    border: 1px solid #4AD597;
    border-radius: 10px;
    width: 100px;
    height: 40px;
    color: #4AD597;
    font-weight: bold;
    font-size: 17px;
}
.deleteArticle{
    background-color: white;
    border: 1px solid #4AD597;
    border-radius: 10px;
    width: 100px;
    height: 40px;
    margin-left: 10px;
    color: #4AD597;
    font-weight: bold;
    font-size: 17px;
}
.moveToList{
    background-color: #4AD597;
    border: none;
    border-radius: 10px;
    width: 100px;
    height: 40px;
    margin-left: 10px;
    color: white;
    font-weight: bold;
    font-size: 17px;
}
</style>