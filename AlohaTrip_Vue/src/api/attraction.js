import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getAttractionList(sidoCode, gugunCode, contentId, keyword, success, fail) {
    local.get(`/attraction/list?sidoCode=${sidoCode}&gugunCode=${gugunCode}&contentId=${contentId}&keyword=${keyword}`).then(success).catch(fail);
}

function getGugunAddress(sidoCode, success, fail) {
    local.get(`/address/gugun?sidoCode=${sidoCode}`).then(success).catch(fail);
}

export {
    getAttractionList, getGugunAddress
}