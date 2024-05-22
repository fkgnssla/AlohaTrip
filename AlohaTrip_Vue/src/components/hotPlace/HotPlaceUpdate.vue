<script setup>
import HotPlaceFormItem from "@/components/hotPlace/item/HotPlaceFormItem.vue";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const { id } = route.params;
</script>

<template>
    <div>
        <div class="hotPlaceBanner">
            <div class="position-absolute top-50 start-50 translate-middle">Brag of Hotplace : Update Post</div>
        </div>
        <div class="contents text-center postWriteForm">
            <HotPlaceFormItem type="update" :id="id"/>
        </div>
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
.contents{
    margin-top: 100px;
    margin-bottom: 50px;
    height: 900px;
    width: 100%;
}
.postWriteForm{
    margin-top: 60px;
    margin-bottom: 30px;
}
</style>