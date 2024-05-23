<script setup></script>

<template>
  <div class="video-container">
    <video width="640" height="360" autoplay loop muted>
        <source src="../assets/video/seoul.mp4" type="video/mp4">
        Your browser does not support the video tag.
    </video>
  </div>
  <div class="mt-4 text-center">
    <h3>요즘 뜨는 여행 경로</h3>

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

.video-container {
  width: 100vw; /* Viewport width */
  height: 60vh; /* Viewport height */
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}

.video-container video {
  width: 100%;
  height: auto; /* Maintain aspect ratio */
  display: block;
}
</style>
