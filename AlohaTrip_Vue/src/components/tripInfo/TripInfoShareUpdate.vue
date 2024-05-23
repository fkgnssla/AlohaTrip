<script setup>
import TripInfoFormItem from "@/components/tripInfo/item/TripInfoFormItem.vue"
</script>

<template>
    <div>
        <div class="travelInfoShareBanner">
            <div class="position-absolute top-50 start-50 translate-middle">여행 정보 : 업데이트</div>
        </div>
        <div class="container text-center postWriteForm">
            <TripInfoFormItem type="update" />
        </div>
    </div>
</template>

<style scoped>
/*
@font-face {
            font-family: 'NanumSquareNeo-Variable';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/NanumSquareNeo-Variable.woff2') format('woff2');
            font-weight: bold;
            font-style: normal;
        }
        *, h1 {
            font-family: 'NanumSquareNeo-Variable';
        }
*/
.travelInfoShareBanner{
  background-image: linear-gradient( rgba(0,0,0, 0.55), rgba(0,0,0, 0.55) ),url("@/assets/img/travelInfoShare/bannerImg.png");
  height: 200px;
  color: #7AEBB8;
  font-weight: bold;
  font-size: 35px;
  position: relative;
}
.postWriteForm{
    margin-top: 60px;
    margin-bottom: 30px;
}
</style>