package com.itvillage.section03.class01;

import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/**
 * flatMap 활용 예제
 *  - 특정 가격에 BTC를 구매했을 때 연도별 최고가일 경우 수익 금액 계산하기.
 *      수익 금액 = (BTC 최고 가격  * 투자 금액 / 구매 당시 가격 ) - 원금
 */
public class FlatMapExample04 {
    private static final int BUY_PRICE = 500;
    private static final int INVESTMENT_AMOUNT = 1000;

    public static void main(String[] args) {
        Flux
                .just(Tuples.of(BUY_PRICE, INVESTMENT_AMOUNT))
                .flatMap(buyInfo -> calculateMaxProfitPerYear(buyInfo))
                .subscribe(Logger::onNext);
    }

    private static Flux<Long> calculateMaxProfitPerYear(Tuple2<Integer, Integer> buyInfo) {
        return Flux
                .fromIterable(SampleData.btcTopPricesPerYear)
                .map(btcInfo -> btcInfo.getT2() * buyInfo.getT2() / buyInfo.getT1() - buyInfo.getT2());
    }
}
