<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";

const props = defineProps({
  plan: Object,
  flag: Boolean
});

const router = useRouter();
const flag = ref();
const startDate = ref("");
const endDate = ref("");
onMounted(() => {
  console.log("플래그: ", props.flag)
  flag.value = props.flag

  startDate.value = `${props.plan.startDate[0]}년 ${props.plan.startDate[1]}월 ${props.plan.startDate[2]}일`
  endDate.value = `${props.plan.endDate[0]}년 ${props.plan.endDate[1]}월 ${props.plan.endDate[2]}일`
})

const moveDetail = () => {
  console.log("선택한 Plan 식별자: " + props.plan.planId)
  router.push({ name: "planDetail", params: { planId: props.plan.planId }})
}
</script>

<template>
    <div class="carousel-item text-center" :class="{ 'active': flag }" >
      <div class="card" style="margin: 0 auto; min-height: 400px; max-width: 400px; box-shadow: -5px -5px 30px 5px rgba(74, 213, 151, 1), 5px 5px 30px 5px blue;">
          <img v-if="plan.imgSrc === ''" src="../../../assets/img/common/logo.png" class="card-img-top img-fluid" alt="..." style="max-width: 100%; max-height: 70%; ">
          <img v-else :src="plan.imgSrc" class="card-img-top img-fluid" alt="..." style="max-width: 100%; max-height: 70%; ">
          <div class="card-body">
            <h5 class="card-title">{{ plan.title }}</h5>
            <p class="card-text">{{ startDate }} ~ {{ endDate }}</p>
            <a href="#" class="btn btn-primary me-5" @click="moveDetail">들어가기</a>
            <a class="btn btn-danger" @click="$emit('deletePlan', props.plan.planId)">삭제하기</a>
          </div>
      </div>
    </div>
</template>

<style scoped>

</style>