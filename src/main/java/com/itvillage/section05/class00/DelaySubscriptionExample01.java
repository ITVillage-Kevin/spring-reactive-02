package com.itvillage.section05.class00;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * delaySubscription 기본 개념 예제
 *  - 파라미터로 입력한 시간만큼 구독을 지연 시킨다.
 */
public class DelaySubscriptionExample01 {
    public static void main(String[] args) {
        Logger.info("# Start");

        Flux
            .range(1, 10)
            .doOnSubscribe(subscription -> Logger.doOnSubscribe())
            .delaySubscription(Duration.ofSeconds(2))
            .subscribe(Logger::onNext);

        TimeUtils.sleep(2500);
    }
}
