package com.itvillage.section06.class00;

import com.itvillage.utils.Logger;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.zip.DataFormatException;

/**
 * error Operator 활용 예제 코드
 * - Sequence 외부에서 체크 예외 발생 시, Mono 또는 Flux로 래핑해서 onError Signal을 전송할 수 있다.
 */
@Slf4j
public class ErrorExample03 {
    public static void main(String[] args) {
        Flux
            .just('a', 'b', 'c', '3', 'd')
            .flatMap(letter -> {
                try {
                    return convert(letter);
                } catch (DataFormatException e) {
                    return Mono.error(e);
                }
            })
            .subscribe(Logger::onNext,
                    Logger::onError);
    }

    private static Mono<String> convert(char ch) throws DataFormatException {
        // error signal이 아니라 Java의 체크 예외를 throw하는 클래스를 이용하는 경우.
        CharacterValidator.isAlphabeticCharacter(ch);
        return Mono.just("Converted to " + Character.toUpperCase(ch));
    }
}