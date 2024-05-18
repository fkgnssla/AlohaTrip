<script setup>
const props = defineProps({
  attraction: Object
});
</script>

<template>
    <v-timeline-item dot-color="pink" size="small">
        <div class="d-flex">
            <strong class="me-4">{{ attraction.attractionDto.title }}</strong>
            <div>
                <strong>{{ attraction.attractionDto.addr1 }}</strong>
                <div class="text-caption">
                    {{ attraction.memo }}
                </div>
            </div>
        </div>
    </v-timeline-item>

    <v-timeline-item
        dot-color="teal-lighten-3"
        size="small"
        v-if="attraction.moveDataMap != null"
    >
        <div class="container">
            <div class="row">
                <div class="col">
                    <img src="../../../assets/img/plan/walk.png" class="text-center" style="width: 40px; height: 40px;"/>
                    <p>도보 {{ attraction.moveDataMap.walkArrivalTime }}</p>
                </div>
                <div class="col">
                    <img src="../../../assets/img/plan/car.png" class="text-center" style="width: 40px; height: 40px;"/>
                    <p>자동차 {{ attraction.moveDataMap.carArrivalTime }}</p>
                </div>
                <div class="col">
                    <img src="../../../assets/img/plan/taxi.png" class="text-center" style="width: 40px; height: 40px;"/>
                    <p>택시 요금 {{ attraction.moveDataMap.taxiFare }}</p>
                </div>
                <div class="col">
                    <img src="../../../assets/img/plan/bus.png" class="text-center" style="width: 40px; height: 40px;"/>
                    <p>대중교통 {{ attraction.moveDataMap.transportArrivalTime }}</p>
                </div>
            </div>
        </div>
    </v-timeline-item>
</template>
<style scoped>

</style>