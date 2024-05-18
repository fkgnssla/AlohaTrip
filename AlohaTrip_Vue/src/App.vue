<script setup>
import { RouterLink, RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue"
import TheFooter from "@/components/layout/TheFooter.vue"
</script>

<template>
  <div>
    <TheHeadingNavbar />
    <RouterView/>
    <TheFooter />
  </div>
</template>

<style scoped>
@font-face {
    font-family: 'NanumSquareNeo';
    font-weight: 800;
    font-style: normal;
    src: url('https://webfontworld.github.io/NanumSquareNeo/NanumSquareNeo-dEb.eot');
    src: url('https://webfontworld.github.io/NanumSquareNeo/NanumSquareNeo-dEb.eot?#iefix') format('embedded-opentype'),
         url('https://webfontworld.github.io/NanumSquareNeo/NanumSquareNeo-dEb.woff2') format('woff2'),
         url('https://webfontworld.github.io/NanumSquareNeo/NanumSquareNeo-dEb.woff') format('woff'),
         url('https://webfontworld.github.io/NanumSquareNeo/NanumSquareNeo-dEb.ttf') format("truetype");
    font-display: swap;
} 
* {
    font-family: 'NanumSquareNeo', sans-serif;
}
</style>
