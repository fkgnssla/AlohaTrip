<script setup>
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay  } from 'vue3-kakao-maps';
import { ref, onMounted, watch } from "vue";
import { getSidoAddress, getGugunAddress } from "@/api/address" 
import { getAttractionList } from "@/api/attraction"

var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
const coordinate = ref({
  lat: 37.566826,
  lng: 126.9786567
});
const onLoadKakaoMap = (mapRef) => {
    map.value = mapRef;
    resizeMap();
    map.relayout();
};
function resizeMap() {
    var mapContainer = document.getElementById('map');
    mapContainer.style.width = '100px';
    mapContainer.style.height = '150px'; 
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
                    <div v-for="index in 1">
                        <div class="carousel-item active" data-bs-interval="2000*{{ index }}">
                            <div class="carouselItemBlock">
                                {{ index }}
                                <img src="@/assets/img/HotPlace/testImg.png" class="d-block w-100" alt="...">
                            </div>
                        </div>
                        <div class="carousel-item active" data-bs-interval="2000*{{ index }}">
                            <div class="carouselItemBlock">
                                {{ index }}
                                <img src="@/assets/img/HotPlace/testImg2.jpg" class="d-block w-100" alt="...">
                            </div>
                        </div>
                    </div>
                    <!--
                        <div class="carousel-item">
                        map
                            <KakaoMap @onLoadKakaoMap="onLoadKakaoMap" :lat="coordinate.lat" :lng="coordinate.lng" :draggable="true">
                            </KakaoMap>
                        </div>
                    -->
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
                        서울특별시 강남구 테헤란로 212
                    </div>
                </div>
                <div class="postTitle">
                    핫플레이스 장소 이름
                </div>
                <div class="divisionLineRow"></div>
                <div class="writerAndCommentCount">
                    글쓴이 | 3 Comments
                </div>
                <div class="visitedDate">
                    2024.05.15
                </div>
                <div class="postContents">
                    핫플레이스 설명 입니다
                </div>
                <div class="createdDate">
                    2024.05.16 17:26:29
                </div>
            </div>
            <div class="divisionLineRow"></div>
            <div class="likeDiv">
                <img src="@/assets/img/common/heart.png" height="20px" style="margin-right: 5px;">
                4 people like this
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
    height: 90%;
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
    height: 2px;
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
    height: 70%;
    font-size: 23px;
}
.createdDate{
    font-size: 15px;
    color: grey;
}
.likeDiv{
    width: 100%;
    height: 5%;
}
</style>