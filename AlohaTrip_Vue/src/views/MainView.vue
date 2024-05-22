<script setup></script>

<template>
  <div>
    <video width="640" height="360" autoplay loop muted>
        <source src="../assets/video/seoul.mp4" type="video/mp4">
        Your browser does not support the video tag.
    </video>
  </div>
</template>

<style scoped>
.hotPlaceBanner{
  background-image: linear-gradient( rgba(0,0,0, 0.55), rgba(0,0,0, 0.55) ),url("@/assets/img/common/MainBannerImg.jpg");
  background-repeat : no-repeat;
  background-size : cover;
  height: 900px;
  color: #7AEBB8;
  font-weight: bold;
  font-size: 35px;
  position: relative;
}
</style>
