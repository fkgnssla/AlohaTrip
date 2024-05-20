import { localAxios } from "@/util/http-commons";
import { getAccessToken, getMemberId } from "@/util/storageUtil";

const local = localAxios();

function getMember(memberId, success, fail) {
    local.get(`/member/` + memberId, {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      })
    .then(success)
    .catch(fail);
}

function updateMember(member, success, fail) {
    local.put(`/member/update`, JSON.stringify(member), {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      })
      .then(success).catch(fail);
}



export {
    getMember, updateMember
}