package com.itvillage.section04.class00;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * doOnTerminate()와 doAfterTerminate()의 차이점을 이해하기 위한 예제
 *  - doOnTerminate()는 upstream operator가 종료할 때 호출된다.
 *  - doAfterTerminate는 전체 Sequence가 종료할 때 Downstream에서 Upstream으로 전파(propagation)되면서 호출된다.
 */
public class DoAfterTerminateExample01 {
    public static void main(String[] args) {
        Flux
            .just("HI", "HELLO")
            .filter(data -> data.equals("HI"))
            .doOnTerminate(() -> Logger.doOnTerminate("filter"))
            .doAfterTerminate(() -> Logger.doAfterTerminate("filter"))
            .map(data -> data.toLowerCase())
            .doOnTerminate(() -> Logger.doOnTerminate("map"))
            .doAfterTerminate(() -> Logger.doAfterTerminate("map"))
            .subscribe(
                    data -> Logger.onNext(data),
                    error -> {},
                    () -> Logger.doOnComplete());
    }
}
