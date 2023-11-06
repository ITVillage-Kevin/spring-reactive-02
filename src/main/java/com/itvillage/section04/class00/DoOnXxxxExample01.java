package com.itvillage.section04.class00;

import com.itvillage.utils.Logger;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * DoOnXXXX() Operator의 기본 개념 예제
 */
public class DoOnXxxxExample01 {
    public static void main(String[] args) {
        Flux
            .range(1, 5)
            .doFinally(signalType -> Logger.info("doFinally() > range"))
            .doOnNext(data -> Logger.doOnNext("range", data))
            .doOnRequest(n -> Logger.info("doOnRequest > range: {}", 1))
            .doOnSubscribe(subscription -> Logger.info("doOnSubscribe() > range"))
            .doFirst(() -> Logger.info("doFirst() > range"))
            .doOnComplete(() -> Logger.info("doOnComplete() > range"))
            .filter(num -> num % 2 == 1)
            .doOnNext(data -> Logger.doOnNext("filter", data))
            .doOnRequest(n -> Logger.info("doOnRequest > filter: {}", 1))
            .doOnSubscribe(subscription -> Logger.info("doOnSubscribe() > filter"))
            .doFinally(signalType -> Logger.info("doFinally() > filter"))
            .doOnComplete(() -> Logger.info("doOnComplete() > filter"))
            .doFirst(() -> Logger.info("doFirst() > filter"))
            .subscribe(new BaseSubscriber<>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(1);
                }

                @Override
                protected void hookOnNext(Integer value) {
                    Logger.info("# hookOnNext: {}", value);
                    request(1);
                }
            });
    }
}
