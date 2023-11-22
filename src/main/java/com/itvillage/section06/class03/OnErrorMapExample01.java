package com.itvillage.section06.class03;

import com.itvillage.common.CannotDivideByZeroException;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * onErrorMap 기본 개념 예제
 *  - Upstream에서 error signal이 전송되면 에러 정보(예외)를 전달 받아 또 다른 타입의 예외로 변환해서 Downstream으로 전송한다.
 */
public class OnErrorMapExample01 {
    public static void main(String[] args) {
        Flux.just(1, 3, 0, 6, 8)
                .filter(num -> num % 3 == 0) // 3으로 나누어 떨어지는 숫자만 필터링 하기 위한 작업. 0도 포함된다.
                .doOnNext(Logger::doOnNext)
                .map(num -> (num * 2) / num) // 0으로 나눌 수 없으므로 ArithmeticException이 발생한다.
                .onErrorMap(error -> new CannotDivideByZeroException(error.getMessage()))
                .subscribe(
                        Logger::onNext,
                        Logger::onError
                );

    }
}
