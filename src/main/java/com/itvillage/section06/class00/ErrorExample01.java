package com.itvillage.section06.class00;

import com.itvillage.utils.Logger;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * error Operator 기본 개념 예제
 *  - 명시적으로 error signal을 발생시켜야 하는 경우
 */
public class ErrorExample01 {
    public static void main(String[] args) {
        Flux
            .range(1, 5)
            .flatMap(num -> {
                if ((num * 2) % 3 == 0) {
                    return Mono.error(
                            new IllegalArgumentException("Not allowed multiplication of 3"));
                } else {
                    return Mono.just(num * 2);
                }
            })
            .subscribe(Logger::onNext,
                    Logger::onError);
    }
}