package com.itvillage.section05.class00;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * delayElements 기본 개념 예제
 *  - upstream에서의 데이터 emit을 파라미터로 입력한 시간만큼 지연 시킨다.
 *  - delayElements를 거친 데이터는 별도의 쓰레드(parallel)에서 실행된다.
 */
public class DelayElementsExample01 {
    public static void main(String[] args) {
        Flux
            .range(1, 10)
            .delayElements(Duration.ofMillis(500))
            .doOnNext(num -> Logger.doOnNext(num))
            .subscribe(Logger::onNext);

        TimeUtils.sleep(6000);
    }
}
