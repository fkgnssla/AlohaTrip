<script setup>
import {ref, onMounted} from "vue";
import PlanListItem from "@/components/plan/item/PlanListItem.vue";
import { getPlanList, createPlan, deletePlan } from "@/api/plan.js";
import { getMemberId } from "@/util/storageUtil";

const plans = ref([])
const onGetPlanList = () => {
    getPlanList(
        6,
        response => {
            plans.value = response.data
        },
        error => console.log(error)
    )
}

onMounted(() => {
    onGetPlanList()
})

const showModal = ref(false);
const planTitle = ref('');
const planStartDate = ref('');
const planEndDate = ref('');

const openModal = () => {
    showModal.value = true; // 모달 열기
}

const closeModal = () => {
    showModal.value = false;
    planTitle.value = '';
    planStartDate.value = '';
    planEndDate.value = '';
};

const savePlan = () => {
    // 여행 계획 저장 로직
    planStartDate.value = planStartDate.value + 'T00:00:00';
    planEndDate.value = planEndDate.value + 'T00:00:00';
    const plan = {
        "memberId": getMemberId(),
        "title": planTitle.value,
        "startDate": planStartDate.value,
        "endDate": planEndDate.value
    }

    console.log("새로운 계획: ", JSON.stringify(plan));
    createPlan(
        plan,
        (response) => {
            console.log("계획 생성완료!")
            onGetPlanList()
        },
        (err) => console.log(err)
    )

    closeModal(); // 모달 닫기
};

const onDelete = (planId) => {

    deletePlan(
        planId,
        (response) => {
            console.log("삭제 완료!");
            plans.value=[]
            onGetPlanList()
        },
        (err) => console.log(err)
    )
}
</script>

<template>
    <div id="carouselExampleAutoplaying" class="carousel carousel-dark slide mt-3">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselExampleAutoplaying" :data-bs-slide-to="idx" 
                v-for="(plan, idx) in plans" :class="{ 'active': idx === 0 }" :aria-current="idx === 0 ? 'true' : null"></button>
        </div>
        <div class="carousel-inner p-5" v-if="plans.length>0">
            <PlanListItem v-for="(plan, idx) in plans" :plan="plan" :key="plan.planId" :flag="idx === 0" @delete-plan="onDelete" />
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

    <div>
    <!-- 모달 토글 버튼 -->
    <div class="text-center mt-3 mb-3">
        <a class="btn btn-success" @click="openModal">여행 계획 만들기!</a>
    </div>

    <!-- 모달 -->
    <div v-if="showModal" class="modal" @click.self="closeModal">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <!-- 모달 헤더 -->
          <div class="modal-header">
            <h5 class="modal-title">여행 계획 만들기</h5>
            <button type="button" class="btn-close" @click="closeModal"></button>
          </div>
          
          <!-- 모달 바디 -->
          <div class="modal-body">
            <!-- 제목 입력 -->
            <div class="mb-3">
              <label for="planTitle" class="form-label">제목</label>
              <input type="text" class="form-control" id="planTitle" v-model="planTitle">
            </div>
            
            <!-- 날짜 선택 -->
            <div class="mb-3">
              <label for="planDate" class="form-label">시작 날짜</label>
              <input type="date" class="form-control" id="planDate" v-model="planStartDate">
            </div>

            <div class="mb-3">
              <label for="planDate" class="form-label">끝날짜</label>
              <input type="date" class="form-control" id="planDate" v-model="planEndDate">
            </div>
          </div>
          
          <!-- 모달 푸터 -->
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="closeModal">취소</button>
            <button type="button" class="btn btn-primary" @click="savePlan">저장</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 모달 스타일 (부트스트랩 클래스 사용) */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-dialog {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.modal-content {
  padding: 20px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #ddd;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>