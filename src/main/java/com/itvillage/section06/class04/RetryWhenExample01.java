package com.itvillage.section06.class04;

import com.itvillage.common.Book;
import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * retryWhen 기본 개념 예제 코드
 *  - 파라미터로 지정한 RetrySpec에서 지정한 조건에 따라 데이터 emit을 재시도 한다.
 */
public class RetryWhenExample01 {
    public static void main(String[] args) throws InterruptedException {
        int bookId = 1;

        getBookFromServer(bookId)
                .retryWhen(
                Retry
                            .backoff(2, Duration.ofSeconds(2))
                            .jitter(0.5)
                            .filter(throwable -> throwable instanceof TimeoutException)
                )
                .subscribe(
                        responseEntity -> {
                            Book book = responseEntity.getBody();
                            Logger.info(" # book name: {}, author name: {}",
                                    book.getBookName(), book.getAuthorName());
                        },
                        Logger::onError);

        TimeUtils.sleep(13000);
    }

    private static Mono<ResponseEntity<Book>> getBookFromServer(int bookId) {
        return Mono
                .just(SampleData.findBookById(bookId))
                .doOnNext(Logger::doOnNext)
                .delayElement(Duration.ofMillis(2500))
                .map(book -> ResponseEntity.ok(book))
                .timeout(Duration.ofSeconds(2));
    }
}