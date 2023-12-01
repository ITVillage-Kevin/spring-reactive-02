package com.itvillage.section07.class00;

import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.math.MathFlux;

/**
 * window 활용 예제
 *  - window를 이용해 2021년도 월별 도서 판매량에서 분기별 판매량을 계산한다.
 *  - 즉, 월별 데이터를 3개월씩 window에 포함 한 후, 분기별 판매량을 계산한다.
 */
public class WindowExample02 {
    public static void main(String[] args) {
        Flux
            .fromIterable(SampleData.monthlyBookSales2021)
            .window(3)
            .flatMap(flux -> MathFlux.sumInt(flux))
            .subscribe(new BaseSubscriber<>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    subscription.request(2);
                }

                @Override
                protected void hookOnNext(Integer value) {
                    Logger.onNext(value);
                    request(2);
                }
            });
    }
}
