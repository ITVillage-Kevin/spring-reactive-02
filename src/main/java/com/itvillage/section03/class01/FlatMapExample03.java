package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * flatMap 기본 개념 예제
 *  - 비동기적으로 동작할 경우, emit 되는 순서를 보장하지 않는다.
 */
public class FlatMapExample03 {
    public static void main(String[] args) {
        Flux
            .range(2, 8)
            .flatMap(dan -> Flux
                                .range(1, 9)
                                .publishOn(Schedulers.parallel())
                                .map(n -> dan + " * " + n + " = " + dan * n))

            .subscribe(Logger::onNext);

        TimeUtils.sleep(200L);
    }
}
