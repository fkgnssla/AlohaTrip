<script setup>
import HotPlaceListBestItem from "@/components/hotPlace/item/HotPlaceListBestItem.vue";
import HotPlaceListItem from "@/components/hotPlace/item/HotPlaceListItem.vue";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import { getBestotPlaceInfoList, getHotPlaceInfoList } from "@/api/hotPlace.js";
import { getMemberId } from "@/util/storageUtil";

const router = useRouter();
const bestHotPlaceList = ref([]);
const hotPlaceList = ref([]);
const pageNum = ref(1);
const lastPageNum = ref(10);

onMounted(() => {
  console.log(pageNum.value);
  getBestotPlaceInfoList(
    response => {
      console.log(response.data);
      bestHotPlaceList.value = response.data;
    },
    error => console.log(error)  
  )

  getHotPlaceInfoList(
    pageNum.value,
    response => {
      console.log(response.data);
      hotPlaceList.value = response.data.hotPlaceDtoList;
      lastPageNum.value = response.data.lastPage;
    },
    error => console.log(error)  
  )
})

const movePage = (pageNumber) => {
  console.log("버튼 테스트 중")
  console.log(pageNumber)

  if (pageNumber == "before") {
    if (pageNum.value > 1) { 
      pageNum.value -= 1
    }
  } else if (pageNumber == "after") {
    if (pageNum.value < lastPageNum.value) { 
      pageNum.value += 1
    }
  } else { 
    pageNum.value = pageNumber
  }
  console.log(pageNum.value)
  console.log(lastPageNum.value)
  console.log("========================================")
  getHotPlaceInfoList(
    pageNum.value,
    response => {
      console.log(response.data);
      hotPlaceList.value = response.data.hotPlaceDtoList;
      lastPageNum.value = response.data.lastPage;
    },
    error => console.log(error)  
  )
}

const moveCreate = () => {
  router.push({ name: "hotPlacePostCreate" })
}
</script>

<template>
    <div class="hotPlaceBanner">
      <div class="position-absolute top-50 start-50 translate-middle">핫플 자랑하기</div>
    </div>
    <div class="containerObj">
      <div class="postBestListObj"> 
        <div class="hotPlaceBestPostTitleTxt">
          HotPlace Top 5
        </div>
        <div class="divisionLine"></div>
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
      </div>
      <div class="divisionLine"></div>
      <div class="btnGroupDiv hotPlaceList">
        <div class="input-group searchHotPlace">
          <input type="text" class="form-control" placeholder="검색어 입력" aria-describedby="btnSearchHotPalce">
          <button class="btnSearchHotPalce" type="button" id="btnSearchHotPalce">
            검색
          </button>
        </div>
        <button type="button" class="btn wirteArticle" @click="moveCreate" v-show="getMemberId() !== null">
          글쓰기
        </button>
      </div>
      <div class="postListObj">
        <div class="tableDiv">
          <table class="table table-hover">
            <thead>
              <tr class="text-center">
                <th scope="col">글번호</th>
                <th scope="col">hotPlace 장소 이름</th>
                <!--<th scope="col">작성자</th>-->
                <th scope="col">조회수</th>
                <th scope="col">작성일</th>
              </tr>
            </thead>
            <tbody>
              <HotPlaceListItem  v-for="hotPlaceInfo in hotPlaceList" :hotPlaceInfo="hotPlaceInfo"/>
            </tbody>
          </table>
        </div>
      </div>
      <nav>
        <ul class="pagination">
          <li class="page-item" @click="movePage(1)"><a class="page-link"><<</a></li>
          <li class="page-item" @click="movePage('before')"><a class="page-link"><</a></li>
          
          <li class="page-item" v-for="page in lastPageNum" @click="movePage(page)">
            <a class="page-link">{{page}}</a>
          </li>
          
          <li class="page-item" @click="movePage('after')"><a class="page-link">></a></li>
          <li class="page-item" @click="movePage(lastPageNum)"><a class="page-link" >>></a></li>
        </ul>
      </nav>
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
.containerObj{
  width: fit-content;
  margin: 0 auto;
}
.divisionLine{
  background-color: lightgrey;
  height: 1px;
  width: 100%;
  margin-bottom: 50px;
}
.hotPlaceBestPostTitleTxt{
  color: #4AD597;
  font-weight: bold;
  font-size: 25px;
  margin-top: 100px;
}
.hotPlaceBestPostList{
  width: fit-content;
  margin: 0 auto;
}
.btnGroupDiv{
  position: relative;
  display: flex;
  margin-top: 100px;
  margin-bottom: 20px;
  height: 40px;
  padding-left: 57%;
}
.searchHotPlace{
  width: 400px;
  height: 40px;
}
.searchHotPlace input{
  border-radius: 10px;
}
.searchHotPlace button{
  width: 100px;
  border-radius: 10px;
  border: none;
  background-color: #4AD597;
  color: white;
  font-weight: bold;
  font-size: 17px;
}
.wirteArticle{
  background-color: white;
  border: 1px solid #4AD597;
  border-radius: 10px;
  width: 90px;
  height: 40px;
  margin-left: 20px;
  color: #4AD597;
  font-weight: bold;
  font-size: 17px;
}
.tableDiv{
  margin-top: 15px;
  width: 1200px;
  margin: 0 auto;
}
table{
  border-collapse : collapse;
  border-top: 1px solid #20332A;
}
nav{
  margin-top: 50px;
}
.pagination{
  margin: 0 auto;
  width: fit-content;
}
.pagination li{
  margin-left: 10px;
  text-align: center;
}
.pagination li a{
  margin-left: 10px;
  text-align: center;
  color: #20332A;
}
</style>