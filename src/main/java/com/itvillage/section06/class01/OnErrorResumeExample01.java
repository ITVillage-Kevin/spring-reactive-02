package com.itvillage.section06.class01;

import com.itvillage.common.Book;
import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * onErrorResume 활용 예제
 *  - 예외가 발생했을 때, onError signal을 Downstream으로 전송하지 않고, 대체 Publisher로 데이터를 emit하고자 할 경우
 */
public class OnErrorResumeExample01 {
    public static void main(String[] args) {
        final String keyword = "DDD";
        getBooksFromCache(keyword)
                .onErrorResume(error -> getBooksFromDatabase(keyword))
                .subscribe(data -> Logger.onNext(data.getBookName()),
                        Logger::onError);
    }

    private static Flux<Book> getBooksFromCache(final String keyword) {
        return Flux
                .fromIterable(SampleData.books)
                .filter(book -> book.getBookName().contains(keyword))
                .switchIfEmpty(Flux.error(new NoSuchBookException("No such Book")));
    }

    private static Flux<Book> getBooksFromDatabase(final String keyword) {
        List<Book> books = new ArrayList<>(SampleData.books);
        books.add(new Book(11, "DDD: Domain Driven Design",
                "Joy", "ddd-man", 35000, 200));
        return Flux
                .fromIterable(books)
                .filter(book -> book.getBookName().contains(keyword))
                .switchIfEmpty(Flux.error(new NoSuchBookException("No such Book")));
    }

    private static class NoSuchBookException extends RuntimeException {
        NoSuchBookException(String message) {
            super(message);
        }
    }
}