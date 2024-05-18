<script setup>

</script>

<template>
  <footer>
    <div class="footerDiv">
      <div>
        <img src="@/assets/img/common/footerLogo.png" style="height: 60px;">
      </div>
      <div class="footerElement">
        <div>
          본 사이트의 콘텐츠는 저작권법의 보호를 받는 바 무단 전재, 복사, 배포 등을 금합니다.
        </div>
        <div style="margin-top: 5px;">
          Copyright © ENJOYTRAVEL All Rights Reserved.
        </div>
      </div>
      <div class="clearDiv"></div>
    </div>
  </footer>
</template>

<style scoped>
footer{
  background-color: #20332A;
  width: 100%;
  padding-top: 10px;
  padding-bottom: 10px;
  /* position : fixed; */
  bottom : 0; 
  margin-top: 50px;
}
.footerDiv{
  margin: 0 auto;
  width: fit-content;
  display: flex;
  align-items: center;
}
.footerElement{
  color : #F7F9FA;
  font-size: 50%;
  margin-left: 30px;
}
.clearDiv{
  clear: both;
}
</style>