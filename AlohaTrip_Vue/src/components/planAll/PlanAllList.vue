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
    <div>
        <v-sheet
            class="d-flex align-content-start flex-wrap ms-16"
            min-height="200"
        >
            <v-sheet v-for="plan in plans" :key="plan.planId" class="ma-12 pa-2" >
                <PlanListItem :plan="plan" />
            </v-sheet>
        </v-sheet>

        <div class="text-center">
            <v-pagination v-model="page" :length="totalPage" :total-visible="4"></v-pagination>
        </div>
    </div>
</template>

<style scoped>

</style>