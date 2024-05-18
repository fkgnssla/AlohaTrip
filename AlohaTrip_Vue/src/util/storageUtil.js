function getAccessToken() {
    const accessToken = window.localStorage.getItem("accessToken")
    return accessToken;
}

function getMemberId() {
    const memberId = window.localStorage.getItem("memberId")
    return memberId;
}

export { getAccessToken, getMemberId }