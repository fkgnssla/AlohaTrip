import { localAxios } from "@/util/http-commons";
import { getAccessToken, getMemberId } from "@/util/storageUtil";

const local = localAxios();

function createHotPlaceInfo(formData, success, fail) {
  local
    .post(`/bragOfHotPlace/create`, formData, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`,
        "Content-Type": null,
      },
    })
    .then(success)
    .catch(fail);
}

function getHotPlaceInfoDetail(id, success, fail) {
  local
    .get(`/bragOfHotPlace/hotPlaceDetail?Id=${id}`, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`,
      },
    })
    .then(success)
    .catch(fail);
}

function getBestotPlaceInfoList(success, fail) {
  local
    .get(`/bragOfHotPlace/hotPlaceBestList`, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`,
      },
    })
    .then(success)
    .catch(fail);
}

function getHotPlaceInfoList(pageNum, success, fail) {
  local
    .get(`/bragOfHotPlace/hotPlaceList?pageNum=${pageNum}`, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`,
      },
    })
    .then(success)
    .catch(fail);
}

function updateHotPlaceInfo(formData, success, fail) {
  local
    .post(`/bragOfHotPlace/update`, formData, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`,
        "Content-Type": null,
      },
    })
    .then(success)
    .catch(fail);
}

function postDeletePost(Id, success, fail) {
  local
    .delete(`/bragOfHotPlace/delete/${Id}`, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`,
      },
    })
    .then(success)
    .catch(fail);
}

export {
  createHotPlaceInfo,
  getHotPlaceInfoDetail,
  getBestotPlaceInfoList,
  getHotPlaceInfoList,
  updateHotPlaceInfo,
  postDeletePost,
};
