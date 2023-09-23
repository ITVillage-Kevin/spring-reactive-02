package com.itvillage.section04.class00;

import com.itvillage.utils.Logger;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

public class DoOnXxxxExample01 {
    public static void main(String[] args) {
        Flux
            .range(1, 5)
            .doFinally(signalType -> Logger.doFinally(signalType))
            .doOnNext(data -> Logger.doOnNext("range", data))
            .doOnRequest(data -> Logger.doOnRequest(data))
            .doOnSubscribe(subscription -> Logger.doOnSubscribe())
            .doFirst(() -> Logger.doFirst())
            .filter(num -> num % 2 == 1)
            .doOnNext(data -> Logger.doOnNext("filter", data))
            .doOnComplete(Logger::doOnComplete)
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
