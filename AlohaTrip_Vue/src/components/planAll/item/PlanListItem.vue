<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";

const props = defineProps({
  plan: Object,
});
</script>

<template>
    <div>
        <v-card class="mx-auto" min-width="344">
            <v-img
            height="200px"
            :src="plan.imgSrc"
            cover
            ></v-img>

            <v-card-title> {{ plan.title }} </v-card-title>

            <v-card-subtitle> {{ plan.nickname }} </v-card-subtitle>

            <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="orange-lighten-2" text="들어가기"></v-btn>
            </v-card-actions>
        </v-card>
    </div>
</template>

<style scoped>

</style>