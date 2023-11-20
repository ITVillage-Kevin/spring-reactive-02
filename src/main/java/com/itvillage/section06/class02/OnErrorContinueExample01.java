package com.itvillage.section06.class02;

import com.itvillage.utils.Logger;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * onErrorContinue Operator 예제
 *  - 예외가 발생했을 때, 예외를 발생시킨 데이터를 건너뛰고 Upstream에서 emit된 다음 데이터를 처리한다.
 *  - onErrorContinue() Operator는 명확하지 않은 Sequence의 동작으로 인해 개발자가 의도하지 않은 상황을 발생시킬 수 있기 때문에 신중하게 사용해야 한다.
 */
public class OnErrorContinueExample01 {
    public static void main(String[] args) {
        Flux
            .just(1, 2, 4, 0, 6, 12)
            .map(num -> 12 / num)
            .onErrorContinue((error, num) -> Logger.onError("error: {}, num: {}", error, num))
            .subscribe(Logger::onNext,
                        Logger::onError);
    }
}