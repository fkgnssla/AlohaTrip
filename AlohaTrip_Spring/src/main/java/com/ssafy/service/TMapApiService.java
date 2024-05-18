package com.ssafy.service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class TMapApiService {
    
	public String walkTime(double startX, double startY, double endX, double endY) throws IOException {
		
		OkHttpClient client = new OkHttpClient();
		String encodeStartName = URLEncoder.encode("천우네오젠아파트", StandardCharsets.UTF_8.toString());
		String encodeEndName = URLEncoder.encode("대일고등학교", StandardCharsets.UTF_8.toString());
		
        MediaType mediaType = MediaType.parse("application/json");
        String requestBodyJson = String.format("{\"startX\":%.12f,\"startY\":%.12f,\"endX\":%.12f,\"endY\":%.12f,\"startName\":\"%s\",\"endName\":\"%s\"}",
                startX, startY, endX, endY, encodeStartName, encodeEndName);
        RequestBody body = RequestBody.create(mediaType, requestBodyJson);

        Request request = new Request.Builder()
                .url("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("appKey", "e8wHh2tya84M88aReEpXCa5XTQf3xgo01aZG39k5")
                .build();

        Response response = client.newCall(request).execute();
        int totalTime = 0;
        
        String result = null;
        if (response.isSuccessful()) {
            System.out.println("API 호출 성공");
            
            String responseBody = response.body().string();

            // JSON 파싱
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray featuresArray = jsonResponse.getJSONArray("features");

            // 첫 번째 feature의 properties에서 totalTime 추출
            if (featuresArray.length() > 0) {
                JSONObject firstFeature = featuresArray.getJSONObject(0);
                JSONObject properties = firstFeature.getJSONObject("properties");
                totalTime = properties.getInt("totalTime");
                
                int h = totalTime/3600;
                int m = (totalTime - h*3600)/60;
                
                if(h>0) result = h + "시간 " + m + "분";
                else if(m>=0) result = m == 0 ? "1분" : m + "분";
                
                System.out.println("도보 소요 시간: " + result);
            } else {
                System.out.println("Feature가 없습니다.");
            }
        } else {
            System.out.println("API 호출 실패");
            System.out.println("에러 코드: " + response.code());
            System.out.println("에러 메시지: " + response.message());
        }
		
		return result;
	}
	
	public Map<String, String> carTime(double startX, double startY, double endX, double endY) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		String requestBodyJson = String.format("{\"startX\":%.12f,\"startY\":%.12f,\"endX\":%.12f,\"endY\":%.12f,\"totalValue\":2}",
                startX, startY, endX, endY);
		RequestBody body = RequestBody.create(mediaType, requestBodyJson);
		Request request = new Request.Builder()
		  .url("https://apis.openapi.sk.com/tmap/routes?version=1&callback=function")
		  .post(body)
		  .addHeader("accept", "application/json")
		  .addHeader("content-type", "application/json")
		  .addHeader("appKey", "e8wHh2tya84M88aReEpXCa5XTQf3xgo01aZG39k5")
		  .build();

		Response response = client.newCall(request).execute();
		String responseBody = response.body().string();
		System.out.println(responseBody);
		
		String result = null;
		try {
            JSONObject json = new JSONObject(responseBody);
            JSONArray features = json.getJSONArray("features");

            JSONObject feature = features.getJSONObject(0);
            JSONObject properties = feature.getJSONObject("properties");
            int totalTime = properties.getInt("totalTime");
            int taxiFare = properties.getInt("taxiFare");
                
            int h = totalTime/3600;
            int m = (totalTime - h*3600)/60;
                
            if(h>0) result = h + "시간 " + m + "분";
            else if(m>=0) result = m == 0 ? "1분" : m + "분";
                
            System.out.println("자동차 소요 시간: " + result);
            System.out.println("예상 택시비: " + taxiFare);
            
            Map<String, String> map = new HashMap<>();
            map.put("carArrivalTime", result);
            map.put("taxiFare", taxiFare +"원");
            
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	public String transportTime(double startX, double startY, double endX, double endY) throws IOException {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		String requestBodyJson = String.format("{\"startX\":%.12f,\"startY\":%.12f,\"endX\":%.12f,\"endY\":%.12f,\"format\":\"json\",\"count\":1}",
                startX, startY, endX, endY);
		RequestBody body = RequestBody.create(mediaType, requestBodyJson);
		Request request = new Request.Builder()
		  .url("https://apis.openapi.sk.com/transit/routes/sub")
		  .post(body)
		  .addHeader("accept", "application/json")
		  .addHeader("content-type", "application/json")
		  .addHeader("appKey", "e8wHh2tya84M88aReEpXCa5XTQf3xgo01aZG39k5")
		  .build();

		String result = null;
		try {
			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();
			
			JSONObject json = new JSONObject(responseBody);
			System.out.println(json.toString());
			json = json.getJSONObject("metaData");
			json = json.getJSONObject("plan");
	        JSONArray itineraries = json.getJSONArray("itineraries");
	
	        JSONObject data = itineraries.getJSONObject(0);
	 
	        int totalTime = data.getInt("totalTime");
	        System.out.println(totalTime);
	        int totalWalkTime = data.getInt("totalWalkTime");
	        
	        int h = totalTime/3600;
	        int m = (totalTime - h*3600)/60;
	            
	        if(h>0) result = h + "시간 " + m + "분";
	        else if(m>=0) result = m == 0 ? "1분" : m + "분";
	        
	        System.out.println("대중교통: " + result);
			return result;
		} catch (JSONException e) {
            e.printStackTrace();
        }
		
		return null;
	}
}
