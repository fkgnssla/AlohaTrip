<script setup>
import TripInfoShareListItem from "@/components/tripInfo/item/TripInfoShareListItem.vue"
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getTripInfoList, searchTripInfoList } from "@/api/tripInfo.js";
import { getMemberId } from "@/util/storageUtil";

const router = useRouter();
const tripInfos = ref([]);
const searchKeyword = ref("");
const memberId = getMemberId();

onMounted(() => {
  getTripInfoList(
    (res) => tripInfos.value = res.data,
    (error) => console.log(error)
  )
    // axios.get("http://localhost:8080/tripInfoShare")
    //     .then((res) => tripInfos.value = res.data)
    //     .catch((error) => console.log(error))
})

const moveCreate = () => {
  router.push({ name: "tripInfoShareCreate" })
}

const onSearchTripInfo = () => {
  console.log(searchKeyword.value)
  searchTripInfoList(
    searchKeyword.value,
    response => {
      console.log(response.data);
      tripInfos.value = response.data;
    },
    error => console.log(error)    
  )
}

</script>

<template>
  <div>
    <div class="travelInfoShareBanner">
      <div class="position-absolute top-50 start-50 translate-middle">여행 정보 공유</div>
    </div>
    <div class="container text-center">
      <div class="wirteArticlBtnDiv" v-if="memberId !== null">
        <button type="button" class="btn wirteArticle position-absolute bottom-0 end-0" @click="moveCreate">
          글쓰기
        </button>
      </div>
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
            <TripInfoShareListItem v-for="tripInfo in tripInfos" :tripInfo="tripInfo" />
          </tbody>
        </table>
      </div>
      <div>
        <form>
        <div class="input-group input-group-sm researchTextDiv">
          <input
            type="text"
            class="form-control"
            placeholder="검색어..."
            v-model="searchKeyword"
          />
          <button class="btn btn-primary researchTextBtn" type="button" @click="onSearchTripInfo">검색</button>
        </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.travelInfoShareBanner{
  background-image: linear-gradient( rgba(0,0,0, 0.55), rgba(0,0,0, 0.55) ),url("@/assets/img/travelInfoShare/bannerImg.png");
  background-repeat : no-repeat;
  background-size : cover;
  height: 200px;
  color: #7AEBB8;
  font-weight: bold;
  font-size: 35px;
  position: relative;
}
.container{
  margin-bottom: 30px;
}

.wirteArticlBtnDiv{

  position: relative;
  height: 80px;
}
.wirteArticle{
  background-color: #4AD597;
  color: white;
  font-weight: bold;
  font-size: smaller;
  border: none;
}
.wirteArticle:hover{
  background-color: white;
  color: #4AD597;
  font-weight: bold;
  font-size: smaller;
}
.tableDiv{
  margin-top: 15px;
}
table{
  border-collapse : collapse;
  border-top: 1px solid #20332A;

}
.researchTextDiv{
  width: 40%;
  margin: 0 auto;
}
.researchTextBtn{
  width: 65px;
  background-color: #4AD597;
  border: none;
  color: white;
}
.researchTextBtn:hover{
  background-color: white;
  border-left : 1px solid lightgrey;
  color: #4AD597
}
</style>