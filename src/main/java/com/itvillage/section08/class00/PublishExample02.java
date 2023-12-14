package com.itvillage.section08.class00;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.sql.Time;
import java.time.Duration;

/**
 * publish() 활용 예제
 *  - 다수의 Subscriber와 Flux를 공유한다.
 *  - Cold Sequence를 Hot Sequence로 변환한다.
 *  - connect()가 호출 되기 전 까지는 데이터를 emit하지 않는다.
 */
public class PublishExample02 {
    private static ConnectableFlux<String> publisher;
    private static int checkedAudienceNumbers;
    static {
        publisher =
                Flux
                    .just("Concert part1", "Concert part2", "Concert part3")
                    .delayElements(Duration.ofMillis(300L))
                    .publish();
    }

    public static void main(String[] args) {
        checkAudienceNumbers();

        TimeUtils.sleep(500L);
        publisher.subscribe(data -> Logger.info("# audience 1 is watching {}", data));
        checkedAudienceNumbers++;

        checkAudienceNumbers();

        TimeUtils.sleep(500L);
        publisher.subscribe(data -> Logger.info("# audience 2 is watching {}", data));
        checkedAudienceNumbers++;

        checkAudienceNumbers();

        TimeUtils.sleep(500L);
        publisher.subscribe(data -> Logger.info("# audience 3 is watching {}", data));

        TimeUtils.sleep(1000L);
    }

    public static void checkAudienceNumbers() {
        if (checkedAudienceNumbers >= 2) {
            publisher.connect();
        }
    }
}
