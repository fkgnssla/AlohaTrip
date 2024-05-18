<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getTripInfoDetail, deleteTripInfo } from "@/api/tripInfo";

const route = useRoute();
const router = useRouter();

// const postno = ref(route.params.postno);
const { id } = route.params;

const tripInfo = ref({});

onMounted(() => {
  tripInfoDetail();
  console.log(tripInfo)
});

const tripInfoDetail = () => {
    getTripInfoDetail(
    id,
    ({ data }) => {
        tripInfo.value = data;
    },
    (error) => {
        console.log(error);
    }
  );
};

function moveList() {
  router.push({ name: "tripInfoShareList" });
}

function moveModify() {
  router.push({ name: "tripInfoShareUpdate", params: { id } });
}

function onDeleteTripInfo() {
  console.log(id + "번글 삭제하러 가자!!!");

  deleteTripInfo(
    id,
    (response) => {
      if (response.status == 200) moveList();
    },
    (error) => {
      console.log(error);
    }
  );
}
</script>

<template>
  <div>
    <div class="travelInfoShareBanner">
      <div class="position-absolute top-50 start-50 translate-middle">Travel Info Share : Detail Post</div>
    </div>
    <div class="container text-center post">
      <div class="postInfo">
        <div class="text-start postTitle ">
          {{ tripInfo.title }}
        </div>
        <div class="postDetailInfo">
          <div class="postWriter">
            {{ tripInfo.name }}
          </div>
          <span class="divisionLine">&nbsp&nbsp|&nbsp&nbsp</span>
          <div class="dateCreated">
            {{ tripInfo.createdDate }}
          </div>
          <div class="viewAndComment">
            <div class="view">
              <img src="@/assets/img/common/viewIcon.png" height="20px">
              {{ tripInfo.views }}
            </div>
            &nbsp&nbsp
            <div class="comments">
              <img src="@/assets/img/common/commentIcon.png" height="20px">
              3
            </div>
          </div>
          <div class="clearDiv"></div>
        </div>
      </div>
      <hr style="border:0; height:1px; background: gray;">
      <div class="postContent">
        <div class="text-start">{{ tripInfo.contents }}</div>
      </div>
      <hr style="border:0; height:1px; background: gray;">
      <div class="d-flex justify-content-end buttonGather">
        <button type="button" class="btn btnUpdate" @click="moveModify">
          글수정
        </button>
        <button type="button" class="btn btnDelete" @click="onDeleteTripInfo">
          글삭제
        </button>
        <button type="button" class="btn btnList" @click="moveList">
          글목록
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.travelInfoShareBanner{
  background-image: linear-gradient( rgba(0,0,0, 0.55), rgba(0,0,0, 0.55) ),url("@/assets/img/travelInfoShare/bannerImg.png");
  height: 200px;
  color: #7AEBB8;
  font-weight: bold;
  font-size: 35px;
  position: relative;
}
.post{
  margin-top: 60px;
}
.postTitle{
  font-size: 30px;
  font-weight: bold;
  color: rgb(39, 39, 39);
}
.postDetailInfo{
  display: flex;
  align-items: center;
}
.postWriter{
  color: gray;
  font-size: 12px;
}
.divisionLine{
  color: gray;
  font-size: 12px;
}
.dateCreated{
  color: gray;
  font-size: 12px;
}
.viewAndComment{
  margin-left: auto;
  color: gray;
  font-size: 12px;
}
.viewAndComment div{
  display: inline;
}
.clearDiv{
  clear: both;
}
.postContent{
  min-height: 100px;
  padding: 0 5%;
}
.buttonGather{
  margin-bottom: 30px;
}
.btnUpdate{
  border-width: 2px;
  border-color: rgb(148, 148, 148);
  color :rgb(148, 148, 148);
  font-weight: bold;
  margin: 0 5px;
}
.btnDelete{
  border-width: 2px;
  border-color: rgb(148, 148, 148);
  color :rgb(148, 148, 148);
  font-weight: bold;
  margin: 0 5px;
}
.btnList{
  border-width: 2px;
  border-color: #4AD597;
  background-color: #4AD597;
  color :white;
  font-weight: bold;
  margin: 0 5px;
}
</style>
