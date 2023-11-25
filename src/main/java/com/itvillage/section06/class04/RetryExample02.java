package com.itvillage.section06.class04;

import com.itvillage.common.Book;
import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Collectors;

/**
 * retry 활용 예제
 *   - 지정된 시간 안에 특정 도서 정보를 가지고 오지 못할 경우, TimeoutException이 발생한다.
 *   - retry()를 이용해 재시도한다.
 */
public class RetryExample02 {
    public static void main(String[] args) throws InterruptedException {
        getAllBooksFromRemoteDB()
                .collect(Collectors.toSet())
                .subscribe(bookSet ->
                                bookSet.stream()
                                        .forEach(book -> Logger.onNext("book name: {}, price: {}",
                                                            book.getBookName(), book.getPrice())));

        Thread.sleep(12000);
    }

    private static Flux<Book> getAllBooksFromRemoteDB() {
        final int[] count = {0};
        return Flux
                .fromIterable(SampleData.books)
                .delayElements(Duration.ofMillis(500))
                .map(book -> {
                    try {
                        count[0]++;
                        if (count[0] == 3) {
                            Thread.sleep(2000);
                        }
                    } catch (InterruptedException e) {

                    }

                    return book;
                })
                .timeout(Duration.ofSeconds(2))
                .doOnNext(book -> Logger.doOnNext(book.getBookName(), book.getPrice()))
                .retry(1);
    }
}