import { localAxios } from "@/util/http-commons";
import { getAccessToken, getMemberId } from "@/util/storageUtil";

const local = localAxios();

function createLike(like, success, fail) {
  local.post('/like/create', JSON.stringify(like), {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}

function deleteLike(likeId, success, fail) {
    local.delete(`/like/delete/${likeId}`, {
        headers: {
          Authorization: `Bearer ${getAccessToken()}`
        }
      }).then(success).catch(fail);
}


export {
  createLike, deleteLike
}