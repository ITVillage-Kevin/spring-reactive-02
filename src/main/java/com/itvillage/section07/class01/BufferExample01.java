package com.itvillage.section07.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * buffer 기본 개념 예제
 * - Upstream에서 emit되는 데이터가 maxSize 숫자만큼 버퍼에 채워지면 버퍼를 비운다.
 * - 버퍼에서 비워진 데이터는 List 형태로 한 번에 Downstream에 emit된다.
 */
public class BufferExample01 {
    public static void main(String[] args) {
        Flux
            .range(1, 95)
            .buffer(10)
            .subscribe(buffer -> Logger.onNext(buffer));
    }
}
