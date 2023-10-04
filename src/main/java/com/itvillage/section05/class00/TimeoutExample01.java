package com.itvillage.section05.class00;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * timeout 기본 개념 예제
 *  - 파라미터로 입력한 시간 안에 Upstream에서 데이터가 emit 되지 않으면 TimeoutException을 발생시킨다.
 */
public class TimeoutExample01 {
    public static void main(String[] args) {
        requestToServer()
                .timeout(Duration.ofSeconds(2))
                .subscribe(response -> Logger.onNext(response),
                        error -> Logger.onError(error));

        TimeUtils.sleep(3500);
    }

    private static Mono<String> requestToServer() {
        return Mono.just("complete to process request from client successfully")
                .delayElement(Duration.ofSeconds(3));
    }
}
