package groom.backend.application.avoidance.model.impl;

import groom.backend.application.avoidance.dto.response.CongestionPredResult;
import groom.backend.application.avoidance.dto.response.CongestionStatistics;
import groom.backend.application.avoidance.model.spec.CongestionPredictModel;
import groom.backend.domain.avoidance.enums.Weekday;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CongestionPredictModelImpl implements CongestionPredictModel {

  @Override
  public CongestionPredResult predictCongestion(
          List<CongestionStatistics> congestionStatistics
  ) {

    if (congestionStatistics == null || congestionStatistics.isEmpty()) {
      log.warn("[CongestionPredict] input statistics is empty");
      return null;
    }

    Weekday weekday = congestionStatistics.getFirst().weekday();
    int recommendedHour = 0;

    // 임시 강수 확률
    // TODO : Weather 도메인 연동
    // TODO : 기상청 연계 및 API 캐싱이 되어있지 않을 시 작동 로직 설계
    int rainProbability = 60;

    log.info("[CongestionPredict] start prediction, rainProbability={}%", rainProbability);

    List<Integer> finalPredicted = congestionStatistics.stream().map(stat -> { int baseCongestion = stat.popMeanMax();
      log.debug("[CongestionPredict] baseCongestion={}", baseCongestion);

      // 1. 기본 혼잡도 100%
      double predicted = baseCongestion;

      // 2. 강수 확률 가중치
      if (rainProbability > 50) {
        int excess = rainProbability - 50;
        double decreaseRate = excess * 0.005; // 0.5%
        predicted = predicted * (1 - decreaseRate);

        log.debug(
                "[CongestionPredict] rain effect applied, excess={}, decreaseRate={}%",
                excess,
                decreaseRate * 100
        );
      }

      // 3. 노이즈 (±1~5%)
      double noiseRate = (1 + (Math.random() * 4)) / 100.0; // 0.01 ~ 0.05
      boolean plus = Math.random() < 0.5;

      predicted = plus
              ? predicted * (1 + noiseRate)
              : predicted * (1 - noiseRate);

      log.debug(
              "[CongestionPredict] noise applied, direction={}, rate={}%",
              plus ? "+" : "-",
              noiseRate * 100
      );

      // 4. 혼잡도 100 단위 이하 절삭
      Integer predictedCongestion = ((int) predicted / 100) * 100;

      log.info(
              "[CongestionPredict] result weekday={}, hour={}, predicted={}",
              stat.weekday(),
              stat.hour(),
              predictedCongestion
      );

      return predictedCongestion;
    }).toList();

    int minimumCongestion = Integer.MAX_VALUE;
    for (int i = 9; i < 23; i++) {
      if (recommendedHour == 0 || minimumCongestion > finalPredicted.get(i)) {
        recommendedHour = i;
        minimumCongestion = finalPredicted.get(i);
      }

    }

    return new CongestionPredResult(
            weekday,
            recommendedHour,
            finalPredicted
    );
  }
}

