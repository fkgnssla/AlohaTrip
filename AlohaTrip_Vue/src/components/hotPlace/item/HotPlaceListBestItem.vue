<script setup>
import { useRouter } from "vue-router";
const router = useRouter();
const moveDetail = () => {
    console.log("클릭하였습니다!  : BestItem")
    /**
    router.push({ name: "tripInfoShareDetail", params: { id: props.tripInfo.id }})
    */
    router.push({ name: "hotPlaceDetail", params: { id: "test" }})
}
</script>

<template>
    <div class="post" @click="moveDetail">
        <div>
            <div class="postImg">
            </div>
            <div class="postDate">
                <div class="postDateDay">
                    15
                </div>
                <div class="postDateDayMonthYear">
                    Jan.2024
                </div>
            </div>
            <div class="postInfo">
                <div class="postTitle">
                    test용 제목입니다
                </div>
                <div class="postDetailInfo">
                    <div class="postWriter">
                        글쓴이
                    </div>
                    |
                    <div class="postCommentView">
                        03 Commnets
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.post{
    box-shadow: 5px 5px 5px 5px lightgray;
    position: relative;
    width: 1200px;
    margin-bottom: 50px;
    margin-right: 50px;
    margin-left: 50px;
}
.postImg{
    background-image: url("@/assets/img/HotPlace/testImg.png");
    background-repeat : no-repeat;
    background-size : cover;
    width: 1200px;
    height: 300px;
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