<script setup>

</script>

<template>
    <div>
        <section class="h-100">
        <div class="container h-100">
            <div class="row justify-content-sm-center h-100">
                <div class="col-xxl-4 col-xl-5 col-lg-5 col-md-7 col-sm-9">
                    <div class="text-center my-5">
                        <a href="index.jsp">
                            
                        </a>
                    </div>
                    <div class="card shadow-lg">
                        <div class="card-body p-5">
                            <h1 class="fs-4 card-title fw-bold mb-4">로그인</h1>
                            <form class="needs-validation" novalidate="" autocomplete="off">
                                <div class="mb-3">
                                    <label class="mb-2 text-muted" for="id">아이디</label>
                                    <input id="id" type="text" class="form-control" name="id" required>
                                </div>

                                <div class="mb-3">
                                    <div class="mb-2 w-100">
                                        <label class="text-muted" for="password">비밀번호</label>
                                    </div>
                                    <input id="password" type="text" class="form-control" name="password" required readonly>
                                </div>

                                <div class="d-flex align-items-center">
                                    <div >
                                        <a href="http://localhost:8080/oauth2/authorization/kakao">
                                            <img src="../assets/img/kakao_login.png"/>
                                        </a>
                                    </div>
                                    <button id="updateBtn" type="button" class="btn btn-primary text-white ms-auto">
                                        확인
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