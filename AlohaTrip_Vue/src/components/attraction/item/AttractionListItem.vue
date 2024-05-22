<script setup>
import { ref, onMounted} from "vue";
const props = defineProps({
    markerInfo: Object
});
console.log(props.markerInfo.firstImage)
</script>

<template>
    <div class="attractionDiv">
        <div class="attractionInfo">
            <div class="imgDiv">
                <img :src="markerInfo.firstImage" v-if="markerInfo.firstImage!=''">
                <img :src="markerInfo.firstImage2" v-if="markerInfo.firstImage=='' && markerInfo.firstImage2 !=''">
                <img src="@/assets/img/common/noImage.png" v-if="markerInfo.firstImage=='' && markerInfo.firstImage2 ==''" >
            </div>
            <div class="attractionContents">
                <div class="attractionTitle">
                    {{ markerInfo.title }}
                </div>
                <div class="divisionLine"></div>
                <div class="attractionAddress">
                    {{ markerInfo.addr1 }} {{ markerInfo.addr2 }}
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.attractionDiv{
    margin: 20px;
    border-left: 5px solid lightgray;
}
.attractionInfo{
    display: flex;
    justify-content: left;
}
.imgDiv{
    margin-left: 10px;
    margin-right: 20px;
    width: 120px;
    height: 120px;
}
img{
    /* background-color: aqua; */
    width: 120px;
    height: 120px;
}
.attractionContents{
    width: 70%;
}
.attractionTitle{
    font-size: 20px;
    margin-top: 20px;
}
.divisionLine{
    background-color: lightgray;
    height: 1px;
    margin-bottom: 5px;
    width: 100%;
}
.attractionAddress{
    color: grey;
}

</style>