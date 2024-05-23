<script setup>
import { RouterLink } from "vue-router";
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import { getMemberId } from "@/util/storageUtil";
import axios from "axios";
import { getMember } from "@/api/member";

const memberId = ref(null);
const profileImgSrc = ref(null);
const nickname = ref(null);
onMounted(() => {

    profileImgSrc.value = window.localStorage.getItem('profileImgSrc');
    nickname.value = window.localStorage.getItem('nickname');
    // 현재 페이지의 URL에서 쿼리 문자열을 가져옴
    const queryString = window.location.search;

    // URLSearchParams 객체 생성
    const urlParams = new URLSearchParams(queryString);

    //쿼리스트링 값 추출
    const accessToken = urlParams.get('accessToken');
    const refreshToken = urlParams.get('refreshToken');
    memberId.value = urlParams.get('memberId');

    // 토큰 값 출력
    console.log('Access Token:', accessToken);
    console.log('Refresh Token:', refreshToken);
    console.log('memberId:', memberId.value);

    if(accessToken !== null && refreshToken !== null && memberId.value !== null) {
      window.localStorage.setItem('accessToken', accessToken);
      window.localStorage.setItem('refreshToken', refreshToken);
      window.localStorage.setItem('memberId', memberId.value);

      getMember(
        memberId.value,
        (response) => {
          window.localStorage.setItem('profileImgSrc', response.data.profileImgSrc)
          if(response.data.nickname === null) window.localStorage.setItem('nickname', response.data.name)
          else window.localStorage.setItem('nickname', response.data.nickname)

          profileImgSrc.value = window.localStorage.getItem('profileImgSrc')
          nickname.value = window.localStorage.getItem('nickname');
        },
        err => console.log(err)
      )
    } else {
      memberId.value = getMemberId();
    }
  }
)

const router = useRouter();
const onLogout = () => {
  window.localStorage.removeItem('accessToken');
  window.localStorage.removeItem('refreshToken');
  window.localStorage.removeItem('memberId');
  window.localStorage.removeItem('profileImgSrc');

  memberId.value = null;
  router.push({ name: "main" });
}
</script>

<template>
<nav class="navbar navbar-expand-lg bg-body-tertiary ">
  <div class="container-fluid ">
    <div class="logoImgDiv aligin-self-start">
      <router-link :to="{ name: 'main' }" class="navbar-brand">
        <img src="@/assets/img/common/logo.png" alt="Logo" width="100">
      </router-link>
    </div>
    <div class="navbarDiv">
      <div>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      </div>
      <div class="collapse navbar-collapse justify-content-end" id="navbarNavDropdown">
        <ul class="navbar-nav">
          <li class="nav-item text-end">
            <router-link :to="{ name: 'attraction' }">지역별 여행지</router-link>
          </li>
          <li class="nav-item text-end">
            <router-link :to="{ name: 'planAll' }">여행 계획 둘러보기</router-link>
          </li>
          <li class="nav-item text-end" v-if="memberId !== null">
            <router-link :to="{ name: 'plan' }">나만의 여행 계획</router-link>
          </li>
          <li class="nav-item text-end">
            <router-link :to="{ name: 'bragOfHotPlace'}">핫플 자랑하기</router-link>
          </li>
          <li class="nav-item text-end trvelInfo">
            <router-link :to="{ name: 'tripInfoShare' }">여행 정보 공유</router-link>
          </li>
          <div v-if="memberId == null">
            <li class="nav-item text-end">
            <router-link :to="{ name: 'login' }">로그인</router-link>
          </li>
          </div>
          <div v-if="memberId !== null">
            <li class="nav-item text-end">
              <router-link :to="{ name: 'memberForm' }">마이페이지</router-link>
            </li>
          </div>
          <div v-if="memberId !== null">
            <li class="nav-item text-end" @click="onLogout">
                <router-link>로그아웃</router-link>
            </li>
          </div>
          <div v-if="memberId !== null" class="userImgInfo">
            <li class="nav-item text-end">
              <v-avatar>
                <v-img
                  alt="John"
                  :src="profileImgSrc"
                ></v-img>
              </v-avatar>
              {{ nickname }}님 반갑습니다!
            </li>
          </div>
        </ul>
      </div>
    </div>
  </div>
</nav>
</template>

<style scoped>
.logoImgDiv{
  margin-top: 0%;
  margin-bottom: auto;
  margin-left: 10%;
}
.navbarDiv{
  margin-right : 10%;
}
button{
  float: right;
  margin-top: 12%;
  margin-bottom: 15%
}
#navbarNavDropdown{
  clear: both;
}
li {
  white-space:nowrap;
  margin: 5px 15px;
}
li a {
  text-decoration: none;
  color : #4AD597;
  font-weight: bold;
}
a:hover{
  background-color:#F8F9FA;
  color: #4AD597;
}
</style>