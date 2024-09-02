package com.seek.job;

import java.io.FileInputStream;
import java.util.Properties;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

@Component
public class BusStationJob {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Autowired
  public BusStationJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Job createJob() {
    return jobBuilderFactory.get("busStationJob")
        .incrementer(new RunIdIncrementer())
        .flow(createStep())
        .end()
        .build();
  }

  @Bean
  public Step createStep() {
    return stepBuilderFactory.get("busStationStep")
        .tasklet(createTasklet())
        .build();
  }

    @Bean
    public Tasklet createTasklet() {
        return (contribution, chunkContext) -> {
            // API 호출 로직
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream("src/main/resources/config.properties"));
                // API 키와 파라미터 설정
                String serviceKey =  properties.getProperty("api.key"); // 여기에 당신의 API 키를 입력하세요.
                String stationCoordinateX = "37.583853";
                String stationCoordinateY = "126.999977";
                String stationCoordinateRadius = "100";
                // URL 구성
                String urlString = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos"
                        + "?serviceKey=" + serviceKey
                        + "&tmX" + stationCoordinateX
                        + "&tmY" + stationCoordinateY
                        + "&radius" + stationCoordinateRadius;
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
                } else {
                    System.out.println("API 호출 실패. 응답 코드: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


      return RepeatStatus.FINISHED; // 성공적으로 종료
    };
  }
}
