import { localAxios } from "@/util/http-commons";
import { getAccessToken, getMemberId } from "@/util/storageUtil";

const local = localAxios();

function getTripInfoList(success, fail) {
    local.get(`/tripInfoShare`, {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

function createTripInfo(tripInfo, success, fail) {
    local.post(`/tripInfoShare/create`, JSON.stringify(tripInfo), {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

function getTripInfoDetail(id, success, fail) {
    local.get(`/tripInfoShare/${id}`, {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

function searchTripInfoList(keyword, success, fail) {
    local.get(`/tripInfoShare/search?keyword=${keyword}`, {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

function updateTripInfo(tripInfo, success, fail) {
    local.put(`/tripInfoShare/update`, JSON.stringify(tripInfo), {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

function deleteTripInfo(id, success, fail) {
    local.delete(`/tripInfoShare/delete/${id}`, {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

export {
    getTripInfoList, createTripInfo, getTripInfoDetail, searchTripInfoList, updateTripInfo, deleteTripInfo
};