package com.seek.job;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.FileInputStream;
import java.util.Properties;

public class BusStationInfo {
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            // API 키와 파라미터 설정
            String serviceKey =  properties.getProperty("api.key"); // 여기에 당신의 API 키를 입력하세요.
            String stationCoordinateX = "37.583853";
            String stationCoordinateY = "126.999977";
            String stationCoordinateRadius = "100";

            // URL 구성
            String urlString = "http://ws.bus.go.kr/api/rest/stationinfo/getStaionsByPosList"
                    + "?serviceKey=" + serviceKey
                    + "&tmX" + stationCoordinateX
                    + "&tmY" + stationCoordinateY
                    + "&radius" + stationCoordinateRadius;


            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/xml");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // 성공적으로 호출되었을 때
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 출력 결과 확인
                System.out.println("API 호출 결과: ");
                System.out.println(response.toString());
            } else {
                System.out.println("API 호출 실패. 응답 코드: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
