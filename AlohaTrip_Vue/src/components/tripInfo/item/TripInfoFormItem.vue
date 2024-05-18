<script setup>
import { ref } from "vue"
import axios from "axios";
import { useRoute, useRouter } from "vue-router"
import { createTripInfo, getTripInfoDetail, updateTripInfo } from "@/api/tripInfo";
import { getAccessToken, getMemberId } from "@/util/storageUtil";

const route = useRoute()
const router = useRouter()

const props = defineProps({ type: String })
const isUseId = ref(false)

const tripInfo = ref({
    memberId: getMemberId(),
    title: "",
    contents: ""
})

if (props.type === "update") {
  let { id } = route.params
  console.log(id + "번글 얻어와서 수정할거야")
  getTripInfoDetail(
    id,
    ({ data }) => {
      tripInfo.value = data
      console.log(tripInfo.value)
      isUseId.value = true
    },
    (error) => {
      console.log(error)
    }
  )
  isUseId.value = true
}

function onSubmit() {
  console.log(props.type === "update")
  props.type === "update" ? onUpdateTripInfo() : onCreateTripInfo()
}

function onCreateTripInfo() {
  createTripInfo(
    tripInfo.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다."
      if (response.status == 200) msg = "글등록이 완료되었습니다."
      alert(msg)
      moveList()
    },
    (error) => console.log(error)
  )
}

function onUpdateTripInfo() {
  tripInfo.value.createdDate = null
  console.log(JSON.stringify(tripInfo.value))
  updateTripInfo(
    tripInfo.value,
    (response) => {
      let msg = "글등록 처리시 문제 발생했습니다."
      if (response.status == 200) msg = "글수정이 완료되었습니다."
      alert(msg)
      moveList()
    },
    (error) => console.log(error)
  )
}

function moveList() {
  router.replace({ name: "tripInfoShareList" })
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <!-- <div class="userIdLabel text-start" v-if="type !== 'update'">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="tripInfo.memberId"
        :disabled="isUseId"
        placeholder="작성자ID"
      />
    </div> -->
    <div class="titleLabel text-start">
      <label for="subject" class="form-label">제목 : </label>
      <input type="text" class="form-control" v-model="tripInfo.title" placeholder="제목" />
    </div>
    <hr>
    <div class="contentLabel text-start">
      <textarea class="form-control" v-model="tripInfo.contents" rows="10"></textarea>
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
.userIdLabel{
  color: rgb(70, 70, 70);
  font-size: 15px;
  font-weight: bolder
}
.titleLabel{
  margin-top: 15px;
  margin-bottom: 30px;
  color: rgb(70, 70, 70);
  font-size: 15px;
  font-weight: bolder
}
.contentLabel{
  margin-top: 30px;
}
.buttonGather{
  margin-top: 60px;
}
.btnCreate{
  border-width: 2px;
  border-color: #4AD597;
  background-color: #4AD597;
  color :white;
  font-weight: bold;
  margin: 0 5px;
}
.btnUpdate{
  border-width: 2px;
  border-color: #4AD597;
  background-color: #4AD597;
  color :white;
  font-weight: bold;
  margin: 0 5px;
}
.btnList{
  border-width: 2px;
  border-color: rgb(148, 148, 148);
  color :rgb(148, 148, 148);
  font-weight: bold;
  margin: 0 5px;
}
</style>
