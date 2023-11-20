package com.itvillage.section06.class01;

import com.itvillage.common.Book;
import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.IllegalFormatException;

/**
 * onErrorReturn Operator 예제
 *  - 하나 이상의 onErrorReturn()을 사용하여 지정된 타입의 예외가 발생할 경우에만 대체 값을 emit하도록 할 수 있다.
 */
public class OnErrorReturnExample02 {
    public static void main(String[] args) {
        getBooks()
                .map(book -> book.getPenName().toUpperCase())
                .onErrorReturn(NullPointerException.class, "no pen name")
                .onErrorReturn(IllegalFormatException.class, "Illegal pen name")
                .subscribe(Logger::info, Logger::onError);
    }

    public static Flux<Book> getBooks() {
        return Flux.fromIterable(SampleData.books);
    }
}