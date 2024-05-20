<script setup>
import { useRouter } from "vue-router";
const router = useRouter();
const props = defineProps({
  hotPlaceInfo: Object
});
const moveDetail = () => {
  console.log("클릭하였습니다!")
  console.log(props.hotPlaceInfo.hotPlaceId)
  /**
  router.push({ name: "tripInfoShareDetail", params: { id: props.tripInfo.id }})
   */
   router.push({ name: "hotPlaceDetail", params: { id: props.hotPlaceInfo.hotPlaceId }})
}
</script>

<template>
  <tr @click="moveDetail">
    <td style="width: 15%;">{{ hotPlaceInfo.hotPlaceId }}</td>
    <td style="width: 45%;">{{ hotPlaceInfo.hotPlaceName }}</td>
    <td style="width: 15%;">{{ hotPlaceInfo.views }}</td>
    <td>{{ hotPlaceInfo.createdDate[0] }}-{{ hotPlaceInfo.createdDate[1] }}-{{ hotPlaceInfo.createdDate[2] }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>