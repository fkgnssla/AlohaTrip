<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted, watch } from "vue";
import { getTotalPage, getPlanListByPage } from "@/api/plan"
import PlanListItem from "@/components/planAll/item/PlanListItem.vue";


const page = ref();
const plans = ref();
const totalPage = ref();

onMounted(() => {
    getTotalPage(
        (response) => {
            totalPage.value = response.data;
            console.log(totalPage.value);
        },
        (error) => console.error(error)
    )
    getPlanListByPage(
        1,
        (response) => {
          plans.value = response.data;
          console.log(plans.value);
        },
        (error) => console.error(error)
    )
}),

watch(page, (newValue, oldValue) => {
  console.log("페이지 변경 => ", oldValue, "to", newValue);
  getPlanListByPage(
    newValue,
    (response) => { plans.value = response.data; },
    (error) => console.error(error)
  )
});
</script>

<template>
    <div class="travelInfoShareBanner">
      <div class="position-absolute top-50 start-50 translate-middle">여행 계획 둘러보기</div>
    </div>
    <div class="planList" >
        <v-sheet min-height="200" class="d-flex flex-wrap listSheet">
          <v-sheet v-for="plan in plans" :key="plan.planId" class="hover-up planSheet" >
            <PlanListItem :plan="plan"/>
          </v-sheet>
        </v-sheet>

        <div class="text-center">
            <v-pagination v-model="page" :length="totalPage" :total-visible="4"></v-pagination>
        </div>
    </div>
</template>

<style scoped>
.travelInfoShareBanner{
  background-image: linear-gradient( rgba(0,0,0, 0.55), rgba(0,0,0, 0.55) ),url("@/assets/img/planAll/bannerImg.png");
  background-repeat : no-repeat;
  background-size : cover;
  height: 200px;
  color: #7AEBB8;
  font-weight: bold;
  font-size: 35px;
  position: relative;
  margin-bottom: 100px;
}
.listSheet{
  margin: 0 auto;
  width: fit-content;
}
.planSheet{
  width: 344px;
  margin: 60px;
}
.hover-up {
  transition: transform 0.3s ease-in-out;
}

.hover-up:hover {
  transform: translateY(-20px);
}
</style>