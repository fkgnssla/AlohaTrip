<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted } from "vue";
import { updatePlan } from "@/api/plan";

const props = defineProps({
  plan: Object,
  flag: Boolean
});

const router = useRouter();
const flag = ref();
const startDate = ref("");
const endDate = ref("");

onMounted(() => {
  console.log("플래그: ", props.flag)
  flag.value = props.flag

  if (props.plan.startDate !== null) {
    startDate.value = `${props.plan.startDate[0]}년 ${props.plan.startDate[1]}월 ${props.plan.startDate[2]}일`
    endDate.value = `${props.plan.endDate[0]}년 ${props.plan.endDate[1]}월 ${props.plan.endDate[2]}일` 
  }
})

const emit = defineEmits('updatePlan')
const moveDetail = () => {
  console.log("선택한 Plan 식별자: " + props.plan.planId)
  router.push({ name: "planDetail", params: { planId: props.plan.planId }})
}

const onPlanUpdate = () => {
  emit('updatePlan', props.plan.planId, planTitle.value, planStartDate.value, planEndDate.value);
  closeModal();
}

const showModal = ref(false);
const planTitle = ref('');
const planStartDate = ref('');
const planEndDate = ref('');

const openModal = () => {
  planTitle.value = props.plan.title;
  planStartDate.value = `${props.plan.startDate[0]}-${String(props.plan.startDate[1]).padStart(2, '0')}-${String(props.plan.startDate[2]).padStart(2, '0')}`;
  planEndDate.value = `${props.plan.endDate[0]}-${String(props.plan.endDate[1]).padStart(2, '0')}-${String(props.plan.endDate[2]).padStart(2, '0')}`;
    
  showModal.value = true; // 모달 열기
}

const closeModal = () => {
    showModal.value = false;
    planTitle.value = '';
    planStartDate.value = '';
    planEndDate.value = '';
};
</script>

<template>
    <div class="carousel-item text-center" :class="{ 'active': flag }" >
      <div class="card" style="margin: 0 auto; min-height: 400px; max-width: 400px; box-shadow: 5px 5px lightgray;">
          <img v-if="plan.imgSrc === '' || plan.imgSrc === null" src="../../../assets/img/common/logo.png" class="card-img-top img-fluid" alt="..." style="width: 100%; height: 300px; ">
          <img v-else :src="plan.imgSrc" class="card-img-top img-fluid" alt="..." style="width: 100%; height: 300px; ">
          <div class="card-body">
            <h5 class="card-title">{{ plan.title }}</h5>
            <p class="card-text" v-if="startDate === ''">날짜 미정</p>
            <p class="card-text" v-else>{{ startDate }} ~ {{ endDate }}</p>
            <a class="btn btnMove me-5" @click="moveDetail">들어가기</a>
            <a class="btn btnUpdate me-5" @click="openModal">수정하기</a>
            <a class="btn btnDelete" @click="$emit('deletePlan', props.plan.planId)">삭제하기</a>
          </div>
      </div>
    </div>

    <!-- 모달 -->
    <div>
      <div v-if="showModal" class="modal" @click.self="closeModal">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <!-- 모달 헤더 -->
            <div class="modal-header">
              <h5 class="modal-title">여행 계획 수정하기</h5>
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
              <a class="btn btnCancel " @click="closeModal">취소</a>
              <a class="btn btnSave" @click="onPlanUpdate">저장</a>
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

.btnMove{
    background-color: #7AEBB8;
    border: none;
    color: white;
}
.btnMove:hover{
  border: 1px solid white;
  color: #7AEBB8;
}
.btnUpdate{
    background-color: #7AEBB8;
    border: none;
    color: white;
}
.btnUpdate:hover{
  border: 1px solid white;
  color: #7AEBB8;
}
.btnDelete{
  background-color: white;
  border: 1px solid #7AEBB8;
  color: #7AEBB8;
}
.btnDelete:hover{
  border: 1px solid white;
  color: #7AEBB8;
}


.btnSave{
    background-color: #7AEBB8;
    border: none;
    color: white;
}
.btnCancel{
  background-color: white;
  border: 1px solid #7AEBB8;
  color: #7AEBB8;
}
.btnSave:hover{
  border: none;
  color: #7AEBB8;
}
.btnCancel:hover{
  border: none;
  color: #7AEBB8;
}
</style>