<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";
const router = useRouter();
const props = defineProps({
    bestHotPlaceInfo: Object
});
const month = ref("")
const imgAddress = ref("");
onMounted(() => {
    if (props.bestHotPlaceInfo.visitedDate[1] == 1) {
        month.value = "Jan"
    }
    else if (props.bestHotPlaceInfo.visitedDate[1] == 2) {
        month.value = "Feb"
    }else if (props.bestHotPlaceInfo.visitedDate[1] == 3) {
        month.value = "Mar"
    }else if (props.bestHotPlaceInfo.visitedDate[1] == 4) {
        month.value = "Apr"
    }else if (props.bestHotPlaceInfo.visitedDate[1] == 5) {
        month.value = "May"
    }else if (props.bestHotPlaceInfo.visitedDate[1] == 6) {
        month.value = "Jun"
    }else if (props.bestHotPlaceInfo.visitedDate[1] == 7) {
        month.value = "Jul"
    }else if (props.bestHotPlaceInfo.visitedDate[1] == 8) {
        month.value = "Aug"
    }else if (props.bestHotPlaceInfo.visitedDate[1] == 9) {
        month.value = "Sep"
    }else if (props.bestHotPlaceInfo.visitedDate[1] == 10) {
        month.value = "Oct"
    }else if (props.bestHotPlaceInfo.visitedDate[1] == 11) {
        month.value = "Nov"
    }else {
        month.value = "Dec"
    }

    imgAddress.value = props.bestHotPlaceInfo.ImgAddress.replace('C:\\SSAFY\\08_enjoytrip_final\\AlohaTrip_Vue', '').replaceAll('\\','/');
    console.log(imgAddress)
    // backgroundImgAdress = "url("+imgAddress.value+")"

})

const moveDetail = () => {
    console.log(props.bestHotPlaceInfo)
    // router.push({ name: "tripInfoShareDetail", params: { id: props.bestHotPlaceInfo.hotPlaceId }})
}
</script>

<template>
    <div class="post" @click="moveDetail">
        <div>
            <div class="postImgDiv">
                <img class="postImg" :src="imgAddress" />
            </div>
            <div class="postDate">
                <div class="postDateDay">
                    {{ bestHotPlaceInfo.visitedDate[2] }}
                </div>
                <div class="postDateDayMonthYear">
                    {{ month }}.{{ bestHotPlaceInfo.visitedDate[0] }}
                </div>
            </div>
            <div class="postInfo">
                <div class="postTitle">
                    {{ bestHotPlaceInfo.hotPlaceName }}
                </div>
                <div class="postDetailInfo">
                    <div class="postWriter">
                        {{ bestHotPlaceInfo.writerName }}
                    </div>
                    |
                    <div class="postCommentView">
                        {{ bestHotPlaceInfo.views }} Views
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.post{
    background-color: white;
    box-shadow: 5px 5px 5px 5px lightgray;
    position: relative;
    width: 1200px;
    margin-bottom: 50px;
    margin-right: 50px;
    margin-left: 50px;
}
.postImgDiv{
    width: 1200px;
    max-height: 300px;
    overflow: hidden;
}
.postImg{
    max-height: initial;
    margin-top: -50%;
    width: 1200px;
}
.postInfo{
    width: 1200px;
    height: 150px;
    padding-top: 40px;
    padding-left: 30px;
}
.postDate{
    background-color: #84C0A5;
    width: 130px;
    height: 130px;
    position: absolute;
    top: 45%;
    left: 80%;
    border-radius: 10%;
    color: white;
    text-align: center;
}
.postDateDay{
    font-size: 60px;
    font-weight: bold;
    margin-bottom: -10px;
}
.postDateDayMonthYear{
    font-size: 20px;
}
.postTitle{
    color: #20332A;
    font-weight: bold;
    font-size: 30px;
    text-align: left;
}
.postDetailInfo{
    text-align: left;
}
.postDetailInfo div{
    display: inline-block;
    margin-top: 10px;
}
</style>