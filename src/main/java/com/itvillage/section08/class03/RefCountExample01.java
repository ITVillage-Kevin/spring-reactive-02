package com.itvillage.section08.class03;

import com.itvillage.utils.Logger;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * refCount() Operator
 *  - 다수의 Subscriber와 Flux를 공유한다.
 *  - Cold Sequence를 Hot Sequence로 변환한다.
 *  - 파라미터로 입력한 숫자만큼의 구독이 발생하는 시점에 connect()가 자동으로 호출된다.
 *  - 파라미터로 입력한 숫자만큼의 구독이 취소되면 Upstream 소스와의 연결을 해제한다.
 */
public class RefCountExample01 {
    public static void main(String[] args) throws InterruptedException {
        Flux<Long> publisher =
                Flux
                    .interval(Duration.ofMillis(500))
//                    .publish().autoConnect(1);
                    .publish().refCount(1);
        Disposable disposable =
                publisher.subscribe(data -> Logger.info("# subscriber 1: {}", data));

        Thread.sleep(2100L);
        disposable.dispose();

        publisher.subscribe(data -> Logger.info("# subscriber 2: {}", data));

        Thread.sleep(2500L);
    }
}
