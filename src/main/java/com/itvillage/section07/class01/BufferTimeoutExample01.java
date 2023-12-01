package com.itvillage.section07.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * bufferTimeout 기본 개념 예제
 *  - Upstream에서 emit되는 첫 번째 데이터부터 maxTime 내에 emit된 데이터를 List 버퍼로 한번에 emit한다.
 *  - maxTime 내에 버퍼에 담을 수 있는 최대 데이터 개수는 maxSize이다.
 *  - 마지막 버퍼가 포함하는 데이터는 maxSize보다 작거나 같다.
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
