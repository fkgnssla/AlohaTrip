<script setup>
import { getMember } from "@/api/member";
import { getMemberId } from "@/util/storageUtil";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const memberId = getMemberId();
const member = ref({
    name: "",
    profileImgSrc: ""
});
onMounted(() => {
    console.log("asdas")
    getMember(
        memberId,
        (response) => {
            member.value = response.data
            console.log(member.value)
        },
        (err) => console.log(err)
    )
})

const onMove = () => {
    router.push({ name: "memberUpdate" })
}

</script>

<template>
    <div>
        <section class="h-100">
        <div class="container h-100">
            <div class="row justify-content-sm-center h-100">
                <div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
                    <div class="card shadow-lg">
                        <div class="card-body p-5">
                            <h1 class="fs-4 card-title fw-bold mb-4 text-center">내 정보</h1>
                            <div class="text-center my-5">
                                <v-avatar size="100">
                                    <v-img alt="John" :src="member.profileImgSrc"></v-img>
                                </v-avatar>
                            </div>
                            <form class="needs-validation" novalidate="" autocomplete="off">
                                <div class="mb-3">
                                    <label class="mb-2 text-muted" for="name">이름</label>
                                    <input id="name" type="text" class="form-control" required readonly disabled :value="member.name">
                                </div>

                                <div class="mb-3">
                                    <div class="mb-2 w-100">
                                        <label class="text-muted" for="nickname">닉네임</label>
                                    </div>
                                    <input id="nickname" type="text" class="form-control" required readonly disabled v-if="member.nickname !== null" :value="member.nickname">
                                    <input id="nickname" type="text" class="form-control" required readonly disabled v-else placeholder="닉네임을 설정해주세요!">
                                </div>

                                <div class="d-flex align-items-center">
                                    <button id="updateBtn" type="button" class="btn btn-primary text-white ms-auto" @click="onMove">
                                        정보 수정하기
                                    </button>
                                </div>
                            </form>
                        </div>
                        
                    </div>
                    <div class="text-center mt-5 text-muted">
                        Copyright &copy; SSAFY_11기_16반_김형민_최유리
                    </div>
                </div>
            </div>
        </div>
    </section>
    </div>
</template>

<style scoped>

</style>