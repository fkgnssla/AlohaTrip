<script setup>
import { getPlanTop3 } from "@/api/plan";
import { getBestotPlaceInfoList} from "@/api/hotPlace.js";
import PlanList from "@/components/plan/PlanList.vue";
import PlanListItem from "@/components/planAll/item/PlanListItem.vue";
import { onMounted, ref } from "vue";
import HotPlaceListBestItem from "@/components/hotPlace/item/HotPlaceListBestItem.vue";
import TripInfoShareListItem from "@/components/tripInfo/item/TripInfoShareListItem.vue"
import { getTripInfoList } from "@/api/tripInfo.js";

const plans = ref();
const bestHotPlaceList = ref([]);
const tripInfos = ref([]);
onMounted(() => {
  getPlanTop3Func();
  getBestotPlaceInfoListFunc();
  getTripInfoListFunc();
})

function getPlanTop3Func() { 
  getPlanTop3(
    response => {
      console.log(response.data)
      plans.value = response.data;
    },
    err => console.log(err)
  )
}

function getBestotPlaceInfoListFunc() {
  getBestotPlaceInfoList(
    response => {
      console.log(response.data);
      bestHotPlaceList.value = response.data;
    },
    error => console.log(error)  
  )
} 

function getTripInfoListFunc() {
  getTripInfoList(
    (res) => tripInfos.value = res.data,
    (error) => console.log(error)
  )
 }

</script>

<template>
  <div class="video-container">
    <video autoplay loop muted>
        <source src="@/assets/video/seoul.mp4" type="video/mp4">
    </video>
  </div>

  <div class="hotPlaceBanner"></div>
  <div class="hotPlaceBannerTitle">
    <div class="titleDiv">
      <div class="title">Aloha Trip</div>
    </div>
  </div>
  <div class="blankDiv"></div>
  <div class="mt-4 text-center tripPlan">
    <div class="subTitle">요즘 뜨는 여행 계획</div>
    <div class="travelPlanDiv">
    <v-sheet min-height="200" class="d-flex flex-wrap listSheet justify-center align-center">
      <v-sheet v-for="plan in plans" :key="plan.planId" class="hover-up planSheet travelPlan" >
        <PlanListItem :plan="plan"/>
      </v-sheet>
    </v-sheet>
    </div>
  </div>
  <div class="blankDiv"></div>
  <div class="subTitle">Top 5 핫플레이스</div>
  <div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-inner hotPlaceBestPostList">
      <div v-for="bestHotPlaceInfo in bestHotPlaceList">
        <div class="carousel-item active" data-bs-interval="2000">
          <HotPlaceListBestItem :bestHotPlaceInfo="bestHotPlaceInfo"/>
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
  <div class="blankDiv"></div>
  <div class="subTitle">요즘 뜨는 여행 정보</div>
  <div class="tableDiv">
    <table class="table table-hover">
      <thead>
        <tr class="text-center">
          <th scope="col">글번호</th>
          <th scope="col">제목</th>
          <th scope="col">조회수</th>
          <th scope="col">작성일</th>
        </tr>
      </thead>
      <tbody>
        <TripInfoShareListItem v-for="tripInfo in tripInfos" :tripInfo="tripInfo" />
      </tbody>
    </table>
  </div>
  <div class="blankDiv"></div>
  <div class="blankDiv"></div>
</template>

<style scoped>
.video-container {
  width: 100vw; /* Viewport width */
  height: 60vh;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}

.video-container video {
  width: 100%;

  height: auto; /* Maintain aspect ratio */
  display: block;
}

.hotPlaceBanner{
  width: 100vw;
  height: 65vh;
  position: absolute;
  top: 10vh;
  background-color: white;
  opacity: 35%;
  /* opacity: 0%; */
}
.hotPlaceBannerTitle{
  width: 100vw;
  height: 60vh;
  position: absolute;
  top: 8%;
}
.titleDiv{
  height: 60vh;
  width: fit-content;
  margin: 0 auto;
  display: flex;
  align-items: center;
  font-size: 100px;

}
.title{
  color: #14221c;
  background:linear-gradient(180deg,rgba(255,255,255,0) 90%, #4AD597 10%);
 }
.blankDiv{
  height: 120px;
}
.subTitle{
  font-size: 30px;
  background:linear-gradient(180deg,rgba(255,255,255,0) 95%, #4AD597 10%);
  width: 80%;
  background:linear-gradient(180deg,rgba(255,255,255,0) 55%, #4AD597 10%);
  width: fit-content;
  margin: 0 auto;
  margin-bottom: 50px;
}
.travelPlanDiv{
  width: fit-content;
  margin: 0 auto;
}
.travelPlan{
  margin-right: 40px;
  margin-left: 40px;
}
.hotPlaceBestPostList{
  width: fit-content;
  margin: 0 auto;
}
.tableDiv{
  width:62%;
  margin: 0 auto;
}
table{
  border-collapse : collapse;
  border-top: 1px solid #20332A;

}
</style>
