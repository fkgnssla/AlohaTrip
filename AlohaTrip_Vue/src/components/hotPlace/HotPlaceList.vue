<script setup>
import HotPlaceListBestItem from "@/components/hotPlace/item/HotPlaceListBestItem.vue";
import HotPlaceListItem from "@/components/hotPlace/item/HotPlaceListItem.vue";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
</script>

<template>
    <div class="hotPlaceBanner">
      <div class="position-absolute top-50 start-50 translate-middle">Brag of Hotplace</div>
    </div>
    <div class="containerObj">
      <div class="postBestListObj"> 
        <div class="hotPlaceBestPostTitleTxt">
          HotPlace Top 5
        </div>
        <div class="divisionLine"></div>
        <div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel">
          <div class="carousel-inner hotPlaceBestPostList">
            <div v-for="index in 5">
              <div class="carousel-item active" data-bs-interval="2000*{{ index }}">
                {{ index }}
                <HotPlaceListBestItem />
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
      <div class="btnGroupDiv">
        <div class="input-group searchHotPlace">
          <input type="text" class="form-control" placeholder="검색어 입력" aria-describedby="btnSearchHotPalce">
          <button class="btnSearchHotPalce" type="button" id="btnSearchHotPalce">
            검색
          </button>
        </div>
        <button type="button" class="btn wirteArticle">
          글쓰기
        </button>
      </div>
      <div class="postListObj">
        <div class="tableDiv">
          <table class="table table-hover">
            <thead>
              <tr class="text-center">
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <!--<th scope="col">작성자</th>-->
                <th scope="col">조회수</th>
                <th scope="col">작성일</th>
              </tr>
            </thead>
            <tbody>
              <HotPlaceListItem v-for="index in 10"/>
            </tbody>
          </table>
        </div>
      </div>
      <nav>
        <ul class="pagination">
          <li class="page-item"><a class="page-link" href="#"><<</a></li>
          <li class="page-item"><a class="page-link" href="#"><</a></li>
          <li class="page-item"><a class="page-link" href="#">1</a></li>
          <li class="page-item"><a class="page-link" href="#">2</a></li>
          <li class="page-item"><a class="page-link" href="#">3</a></li>
          <li class="page-item"><a class="page-link" href="#">></a></li>
          <li class="page-item"><a class="page-link" href="#">>></a></li>
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