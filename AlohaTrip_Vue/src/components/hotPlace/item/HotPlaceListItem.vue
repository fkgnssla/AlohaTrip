<script setup>
import { useRouter } from "vue-router";
const router = useRouter();
const moveDetail = () => {
  console.log("클릭하였습니다!")
  /**
  router.push({ name: "tripInfoShareDetail", params: { id: props.tripInfo.id }})
   */
   router.push({ name: "hotPlaceDetail", params: { id: "test" }})
}
</script>

<template>
  <tr @click="moveDetail">
    <td style="width: 15%;">1</td>
    <td style="width: 45%;">제목입니당</td>
    <td style="width: 15%;">13</td>
    <td>2024.05.16 14:18:29</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>