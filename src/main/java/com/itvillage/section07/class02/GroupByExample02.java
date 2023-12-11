package com.itvillage.section07.class02;

import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * groupBy(keyMapper, valueMapper) 기본 개념 예제
 *  - emit되는 데이터를 keyMapper로 생성한 key를 기준으로 그룹화 한 후에
 *  valueMapper를 통해 그룹화 된 데이터를 다른 형태로 가공 처리한 후, Downstream으로 emit한다.
 */
public class GroupByExample02 {
    public static void main(String[] args) {
        Flux
            .fromIterable(SampleData.books)
            .groupBy(book -> book.getAuthorName(),
                    book -> book.getBookName() + "(" + book.getAuthorName() + ")")
            .flatMap(groupedFlux -> groupedFlux.collectList())
            .subscribe(booksByAuthor ->
                    Logger.onNext(booksByAuthor));
    }
}
