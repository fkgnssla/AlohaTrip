<script setup>
const props = defineProps({
  attraction: Object
});
</script>

<template>
    <v-timeline-item dot-color="#FFbbb9" size="small">
        <div class="d-flex container">
            <div class="row">
                <div>
                    <div class="attractionTitle">{{ attraction.attractionDto.title }}</div>
                    <div class="addressAndDeleteBtn">
                        <div class="attractionAddress">{{ attraction.attractionDto.addr1 }}</div>
                        <div class="col-1">
                            <img src="../../../assets/img/plan/trash.jpeg" class="img-fluid" @click="$emit('onDeleteRoute', attraction.planAttractionId)" width="30px"/>
                        </div>
                    </div>   
                    <div class="divisionLine"></div>
                    <div class="attractionMemo">
                        {{ attraction.memo }}
                    </div>
                </div>
            </div>
        </div>
    </v-timeline-item>

    <v-timeline-item
        dot-color="#7AEBB8"
        size="small"
        v-if="attraction.moveDataMap != null"
    >
        <div class="container">
            <div class="row">
                <div class="col">
                    <div class="imgDiv">
                        <img src="../../../assets/img/plan/walk.png" class="text-center" style="width: 30px; height: 30px;"/>
                    </div>
                    <div class="info">도보 {{ attraction.moveDataMap.walkArrivalTime }}</div>
                </div>
                <div class="col">
                    <div class="imgDiv">
                        <img src="../../../assets/img/plan/car.png" class="text-center" style="width: 30px; height: 30px;"/>
                    </div>
                    <div class="info">자동차 {{ attraction.moveDataMap.carArrivalTime }}</div>
                </div>
                <div class="col">
                    <div class="imgDiv">
                        <img src="../../../assets/img/plan/taxi.png" class="text-center" style="width: 30px; height: 30px;"/>
                    </div>
                    <div class="info">택시 요금 {{ attraction.moveDataMap.taxiFare }}</div>
                </div>
                <div class="col">
                    <div class="imgDiv">
                        <img src="../../../assets/img/plan/bus.png" class="text-center" style="width: 30px; height: 30px;"/>
                    </div>
                    <div class="info">대중교통 {{ attraction.moveDataMap.transportArrivalTime }}</div>
                </div>
            </div>
        </div>
    </v-timeline-item>
</template>
<style scoped>
.container{
    width: 550px;
}
.attractionTitle{
    font-size: 25px;
}
.addressAndDeleteBtn{
    display: flex;
	justify-content: start;
}
.attractionAddress{
    font-size: 15px;
    color : grey;
    width: 95%;
}
.divisionLine{
    width: 550px;
    height: 1px;
    background-color: lightgray;
    margin-top: 10px;
    margin-bottom: 10px;
}
.attractionMemo{
    font-size: 15px;
    color : grey;
}
.imgDiv{
    width: fit-content;
    margin: 0 auto;
}
.imgDiv img{
    margin-bottom: 10px;
}
.info{
    width: fit-content;
    margin: 0 auto;
    font-size: 80%;
}
</style>