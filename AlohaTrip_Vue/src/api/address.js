import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getSidoAddress(success, fail) {
    local.get(`/address/sido`).then(success).catch(fail);
}

function getGugunAddress(sidoCode, success, fail) {
    local.get(`/address/gugun?sidoCode=${sidoCode}`).then(success).catch(fail);
}

export {
    getSidoAddress, getGugunAddress
}