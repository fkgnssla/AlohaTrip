import { localAxios } from "@/util/http-commons";
import { getAccessToken, getMemberId } from "@/util/storageUtil";

const local = localAxios();

function createHotPlaceInfo(hotPlaceInfo, success, fail) {
  local
    .post(`/bragOfHotPlace/create`, hotPlaceInfo, {
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

function getHotPlaceInfoDetail(id, success, fail) {
  local
    .get(`/bragOfHotPlace/hotPlaceDetail/${id}`, {
      headers: {
        Authorization: `Bearer ${getAccessToken()}`,
      },
    })
    .then(success)
    .catch(fail);
}

export { getHotPlaceInfoList, getHotPlaceInfoDetail, createHotPlaceInfo };
