package com.itvillage.section03.class06;

import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

public class CollectMapExample01 {
    public static void main(String[] args) {
        Flux
                .range(0, 26)
                .collectMap(key -> SampleData.morseCodes[key], value -> transformToLetter(value))
                .subscribe(map -> Logger.onNext(map));
    }

    private static String transformToLetter(int value) {
        return Character.toString((char) ('a' + value));
    }
}
