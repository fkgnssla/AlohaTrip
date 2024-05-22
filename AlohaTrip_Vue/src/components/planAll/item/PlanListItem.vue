<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";

const router = useRouter();
const props = defineProps({
  plan: Object,
});

const moveDetail = () => {
  console.log("선택한 Plan 식별자: " + props.plan.planId)
  router.push({ name: "planAllDetail", params: { planId: props.plan.planId }})
}
</script>

<template>
    <div class="planDiv">
        <v-card class="mx-auto" min-width="344">
            <v-img
            height="300px"
            :src="plan.imgSrc"
            cover
            ></v-img>

            <v-card-title> {{ plan.title }} </v-card-title>

            <v-card-subtitle> {{ plan.nickname }} </v-card-subtitle>

            <v-card-actions>
            <v-spacer></v-spacer>
            
            <v-btn color="#4AD597" text="들어가기" @click="moveDetail" />
            </v-card-actions>
        </v-card>
    </div>
</template>

<style scoped>
.planDiv{
  box-shadow: 10px 30px 30px rgba(128, 128, 128, 0.25), 0 10px 10px rgba(0,0,0,0.22);
}
</style>