package com.ssafy.service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TMapApiService {
    @Value("${tmap.appKey}")
    private String appKey;

    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json");
    private static final OkHttpClient client = new OkHttpClient();

    private static final String WALK_URL = "https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function";
    private static final String CAR_URL = "https://apis.openapi.sk.com/tmap/routes?version=1&callback=function";
    private static final String TRANSPORT_URL = "https://apis.openapi.sk.com/transit/routes/sub";

    public String walkTime(double startX, double startY, double endX, double endY) throws IOException {
        String encodeStartName = URLEncoder.encode("천우네오젠아파트", StandardCharsets.UTF_8.toString());
        String encodeEndName = URLEncoder.encode("대일고등학교", StandardCharsets.UTF_8.toString());
        String requestBodyJson = createWalkRequestBody(startX, startY, endX, endY, encodeStartName, encodeEndName);

        Response response = makePostRequest(WALK_URL, requestBodyJson);

        return parseWalkTimeResponse(response);
    }

    public Map<String, String> carTime(double startX, double startY, double endX, double endY) throws IOException {
        String requestBodyJson = createCarRequestBody(startX, startY, endX, endY);

        Response response = makePostRequest(CAR_URL, requestBodyJson);

        return parseCarTimeResponse(response);
    }

    public String transportTime(double startX, double startY, double endX, double endY) throws IOException {
        String requestBodyJson = createTransportRequestBody(startX, startY, endX);

        Response response = makePostRequest(TRANSPORT_URL, requestBodyJson);

        return parseTransportTimeResponse(response);
    }

    private String createWalkRequestBody(double startX, double startY, double endX, double endY, String encodeStartName, String encodeEndName) {
        return String.format("{\"startX\":%.12f,\"startY\":%.12f,\"endX\":%.12f,\"endY\":%.12f,\"startName\":\"%s\",\"endName\":\"%s\"}",
                startX, startY, endX, endY, encodeStartName, encodeEndName);
    }

    private String createCarRequestBody(double startX, double startY, double endX, double endY) {
        return String.format("{\"startX\":%.12f,\"startY\":%.12f,\"endX\":%.12f,\"endY\":%.12f,\"totalValue\":2}",
                startX, startY, endX, endY);
    }

    private String createTransportRequestBody(double startX, double startY, double endX) {
        return String.format("{\"startX\":%.12f,\"startY\":%.12f,\"endX\":%.12f,\"endY\":%.12f,\"format\":\"json\",\"count\":1}",
                startX, startY, endX, endX);
    }

    private Response makePostRequest(String url, String requestBodyJson) throws IOException {
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, requestBodyJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("appKey", appKey)
                .build();

        return client.newCall(request).execute();
    }

    private String parseWalkTimeResponse(Response response) throws IOException {
        if (response.isSuccessful()) {
            log.info("도보 소요 시간 API 호출 성공");
            String responseBody = response.body().string();
            try {
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray featuresArray = jsonResponse.getJSONArray("features");

                if (featuresArray.length() > 0) {
                    JSONObject firstFeature = featuresArray.getJSONObject(0);
                    JSONObject properties = firstFeature.getJSONObject("properties");
                    int totalTime = properties.getInt("totalTime");

                    return formatTime(totalTime);
                } else {
                    log.error("도보 소요 시간을 추출할 수 없는 경로입니다.");
                }
            } catch (JSONException e) {
                log.error("응답된 JSON 데이터에 원하는 정보가 없습니다.", e);
            }
        } else {
            handleError(response, "도보 소요 시간");
        }

        return null;
    }

    private Map<String, String> parseCarTimeResponse(Response response) throws IOException {
        Map<String, String> result = new HashMap<>();
        if (response.isSuccessful()) {
            log.info("자동차 소요 시간 API 호출 성공");
            String responseBody = response.body().string();
            try {
                JSONObject json = new JSONObject(responseBody);
                JSONArray features = json.getJSONArray("features");

                JSONObject feature = features.getJSONObject(0);
                JSONObject properties = feature.getJSONObject("properties");
                int totalTime = properties.getInt("totalTime");
                int taxiFare = properties.getInt("taxiFare");

                result.put("carArrivalTime", formatTime(totalTime));
                result.put("taxiFare", taxiFare + "원");
            } catch (JSONException e) {
                log.error("응답된 JSON 데이터에 원하는 정보가 없습니다.", e);
            }
        } else {
            handleError(response, "자동차 소요 시간");
        }

        return result;
    }

    private String parseTransportTimeResponse(Response response) throws IOException {
        if (response.isSuccessful()) {
            log.info("대중교통 소요 시간 API 호출 성공");
            String responseBody = response.body().string();
            try {
                JSONObject json = new JSONObject(responseBody);
                json = json.getJSONObject("metaData").getJSONObject("plan");
                JSONArray itineraries = json.getJSONArray("itineraries");

                JSONObject data = itineraries.getJSONObject(0);

                int totalTime = data.getInt("totalTime");
                int totalWalkTime = data.getInt("totalWalkTime");
                log.info("걷는 시간: {}", totalWalkTime);

                return formatTime(totalTime);
            } catch (JSONException e) {
                log.error("응답된 JSON 데이터에 원하는 정보가 없습니다.", e);
            }
        } else {
            handleError(response, "대중교통 소요 시간");
        }

        return null;
    }

    private void handleError(Response response, String logPrefix) throws IOException {
        String responseBody = response.body().string();
        JSONObject jsonResponse = new JSONObject(responseBody);
        log.error("{} API 호출 실패: {}", logPrefix, jsonResponse.get("error").toString());
    }

    private String formatTime(int totalTime) {
        int h = totalTime / 3600;
        int m = (totalTime - h * 3600) / 60;

        if (h > 0) return h + "시간 " + m + "분";
        return m == 0 ? "1분" : m + "분";
    }
}