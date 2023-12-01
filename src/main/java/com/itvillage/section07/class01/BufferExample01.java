package com.itvillage.section07.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * buffer 기본 개념 예제
 * - Upstream에서 emit되는 첫 번째 데이터부터 maxSize 숫자만큼의 데이터를 List 버퍼로 한번에 emit한다.
 * - 마지막 버퍼가 포함하는 데이터는 maxSize보다 작거나 같다.
 */
public class BufferExample01 {
    public static void main(String[] args) {
        Flux
            .range(1, 95)
            .buffer(10)
            .subscribe(buffer -> Logger.onNext(buffer));
    }
}
