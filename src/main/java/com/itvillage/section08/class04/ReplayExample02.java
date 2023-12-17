package com.itvillage.section08.class04;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * replay() 개념 예제
 *  - 다수의 Subscriber와 Flux를 공유한다.
 *  - Cold Sequence를 Hot Sequence로 변환한다.
 *  - connect()가 호출 되기 전 까지는 데이터를 emit하지 않는다.
 *  - 파라미터로 지정한 개수 만큼 마지막에 emit된 데이터를 캐시한다.
 */
public class ReplayExample02 {
    public static void main(String[] args) {
        ConnectableFlux<Integer> flux =
                Flux
                    .range(1, 5)
                    .delayElements(Duration.ofMillis(300L))
                    .replay(2);

        TimeUtils.sleep(500L);
        flux.subscribe(data -> Logger.onNext("subscriber1: ", data));

        TimeUtils.sleep(200L);
        flux.subscribe(data -> Logger.onNext("subscriber2: ", data));

        flux.connect();

        TimeUtils.sleep(1000L);
        flux.subscribe(data -> Logger.onNext("subscriber3: ", data));

        TimeUtils.sleep(2000L);
    }
}