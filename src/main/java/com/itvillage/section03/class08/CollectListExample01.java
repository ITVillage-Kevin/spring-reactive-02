package com.itvillage.section03.class08;

import com.itvillage.common.SampleData;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

/**
 * collectList 활용 예제
 * - emit된 세 개의 모스 부호를 List<String> 타입으로 Downstream에 emit한다.
 */
public class CollectListExample01 {
    public static void main(String[] args) {
        Flux
            .just("...", "---", "...")
            .map(code -> transformMorseCode(code))
            .collectList()
            .subscribe(list -> Logger.onNext(String.join("", list)));
    }

    public static String transformMorseCode(String morseCode) {
        return SampleData.morseCodeMap.get(morseCode);
    }
}
