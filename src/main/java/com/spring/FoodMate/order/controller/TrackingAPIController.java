package com.spring.FoodMate.order.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.*;

@RestController
public class TrackingAPIController {
	
	private final Dotenv dotenv = Dotenv.load();
	private final String TRACKING_CLIENT_ID = dotenv.get("TRACKING_CLIENT_ID");
	private final String TRACKING_CLIENT_SECRET = dotenv.get("TRACKING_CLIENT_SECRET");

    @RequestMapping(value = "/api/tracking", method = RequestMethod.POST)  // "/api/tracking" 경로로 POST 요청을 받음
    public ResponseEntity<Map<String, String>> trackPackage(@RequestBody Map<String, String> requestData) {
        // 요청 본문에서 "carrierId"와 "trackingNumber"를 Map으로 받아옴
        String carrierId = requestData.get("carrierId");
        String trackingNumber = requestData.get("trackingNumber");

        // 외부 API의 URL을 지정
        String url = "https://apis.tracker.delivery/graphql";

        // RestTemplate 객체 생성: 외부 API와의 HTTP 통신을 담당
        RestTemplate restTemplate = new RestTemplate();

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);  // 요청의 Content-Type을 JSON으로 설정
        headers.set("Authorization", "TRACKQL-API-KEY " + TRACKING_CLIENT_ID + ":" + TRACKING_CLIENT_SECRET);  // API Key를 Authorization 헤더에 추가
        
        String query = """
                query Track($carrierId: ID!, $trackingNumber: String!) {
                  track(carrierId: $carrierId, trackingNumber: $trackingNumber) {
                    lastEvent {
                      time
                      status {
                        code
                      }
                    }
                  }
                }""";
        
        // 요청 본문에 포함될 쿼리 및 변수 설정
        // 쿼리 내용은 외부 API에서 요청할 데이터를 결정하는 GraphQL 쿼리문
        Map<String, Object> variables = new HashMap<>();
        variables.put("carrierId", carrierId);  // 전달받은 carrierId를 변수에 설정
        variables.put("trackingNumber", trackingNumber);  // 전달받은 trackingNumber를 변수에 설정

        // 요청 본문을 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);  // 쿼리문을 요청 본문에 추가
        requestBody.put("variables", variables);  // 변수를 요청 본문에 추가

        // HttpEntity는 HTTP 요청 본문과 헤더를 포함하는 객체
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // RestTemplate을 사용해 외부 API로 POST 요청을 보내고, 응답을 받아옴
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        String statusCode = "UNKNOWN";  // 기본 상태 코드

        // 응답에서 상태 코드 추출
        if (response.getBody() != null) {
        	System.out.println("디버깅 중, response.getBody() = " + response.getBody());
            // 응답 처리 로직 (예: JSON 파싱 후 상태 코드 추출)
            // 예시: 추출한 상태 코드가 "DELIVERED"일 경우
            if (response.getBody().contains("DELIVERED")) {
                statusCode = "DELIVERED";
            } else if (response.getBody().contains("NOT_FOUND")) {
            	statusCode = "NOT_FOUND";
            } else {
                statusCode = "IN_TRANSIT";  // 그 외 상태
            }
        }

        // 클라이언트로 상태 코드 반환
        Map<String, String> result = new HashMap<>();
        result.put("statusCode", statusCode);

        return ResponseEntity.ok(result);
    }
}