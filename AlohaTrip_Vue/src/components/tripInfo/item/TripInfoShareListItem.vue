<script setup>
import { useRouter } from "vue-router";
const props = defineProps({
  tripInfo: Object
});

const router = useRouter();
const moveDetail = () => {
  console.log(props.tripInfo.id)
  router.push({ name: "tripInfoShareDetail", params: { id: props.tripInfo.id }})
}
</script>

<template>
  <tr @click="moveDetail">
    <td style="width: 15%;">{{ tripInfo.id }}</td>
    <td style="width: 45%;">{{ tripInfo.title }}</td>
    <!--<td>{{ tripInfo.name }}</td>-->
    <td style="width: 15%;">{{ tripInfo.views }}</td>
    <td>{{ tripInfo.createdDate }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>