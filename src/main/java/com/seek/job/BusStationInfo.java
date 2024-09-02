package com.seek.job;

import com.seek.model.BusStation;
import com.seek.util.XMLParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.List;

public class BusStationInfo {
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/config.properties"));
            // API 키와 파라미터 설정
            String serviceKey = properties.getProperty("api.key"); // 여기에 당신의 API 키를 입력하세요.
            String stationCoordinateX = "126.999957";
            String stationCoordinateY = "37.583851";
            String stationCoordinateRadius = "500";

            // URL 구성
            String urlString = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos"
                    + "?serviceKey=" + serviceKey
                    + "&tmX=" + stationCoordinateX
                    + "&tmY=" + stationCoordinateY
                    + "&radius=" + stationCoordinateRadius;

            System.out.println(urlString);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

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

                // XML 파싱
                List<BusStation> busStations = XMLParser.parseBusStations(response.toString());

                // 파싱 결과 출력
                for (BusStation station : busStations) {
                    System.out.println(station);
                }

            } else {
                System.out.println("API 호출 실패. 응답 코드: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
