import { localAxios } from "@/util/http-commons";
import { getAccessToken, getMemberId } from "@/util/storageUtil";

const local = localAxios();

function getPlanList(memberId, success, fail) {
    local.get(`/plan/list?memberId=${getMemberId()}`, {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      })
    .then(success)
    .catch(fail);
}

function getPlanListByPage(page, success, fail) {
  local.get(`/plan/listAll?page=${page}`, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`
      }
    })
  .then(success)
  .catch(fail);
}

function getTotalPage(success, fail) {
  local.get(`/plan/totalPage`, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`
      }
    })
  .then(success)
  .catch(fail);
}

function getPlanDetail(planId, success, fail) {
    local.get(`/plan/info/${planId}`, {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      })
      .then(success).catch(fail);
}

function addRoute(newRoute, success, fail) {
    localAxios().post('/plan/addRoute', JSON.stringify(newRoute), {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

function deleteRoute(planAttractionId, success, fail) {
    local.delete(`/plan/deleteRoute/${planAttractionId}`, {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

function createPlan(plan, success, fail) {
    local.post(`/plan/create`, JSON.stringify(plan), {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

function deletePlan(planId, success, fail) {
    local.delete(`/plan/delete/${planId}`, {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

function copyPlan(plan, success, fail) {
  local.post(`/plan/copy`, plan, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`
      }
    }).then(success).catch(fail);
}


export {
    getPlanList, getPlanDetail, getPlanListByPage, getTotalPage, addRoute, deleteRoute, createPlan, deletePlan, copyPlan
}