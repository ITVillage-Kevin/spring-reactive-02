package com.itvillage.section07.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * bufferTimeout 기본 개념 예제
 *  - Upstream에서 emit된 데이터가 버퍼에 채워질 때, maxTime에 도달하면 버퍼를 비운다.
 *  - maxTime에 도달하기 전에 maxSize 만큼의 데이터가 버퍼에 채워지면 maxTime까지 기다리지 않고, 버퍼를 비운다.
 *
 */
public class BufferTimeoutExample01 {
    public static void main(String[] args) {
        Flux
            .range(1, 20)
            .map(num -> {
                try {
                    if (num < 10) {
                        Thread.sleep(100L);
                    } else {
                        Thread.sleep(300L);
                    }
                } catch (InterruptedException e) {}
                return num;
            })
            .bufferTimeout(3, Duration.ofMillis(400L))
            .subscribe(buffer -> Logger.onNext(buffer));
    }
}
