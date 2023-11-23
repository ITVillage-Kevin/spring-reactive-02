package com.itvillage.section03.class01;

import com.itvillage.common.TimezoneNotFoundException;
import com.itvillage.utils.Logger;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

/**
 * onErrorMap 활용 예제
 *  - worldtimeapi.org Open API를 이용해서 서울의 현재 시간을 조회한다.
 *  - 404 Not Found가 발생할 경우, HttpClientErrorException을 조금 더 구체적인 TimezoneNotFoundException으로 변경한다.
 */
public class OnErrorMapExample02 {
    private final static URI WORLD_TIME_URI = UriComponentsBuilder.newInstance().scheme("http")
            .host("worldtimeapi.org")
            .port(80)
            .path("/api/timezone/Asia/Mars") // 잘못된 URI 입력
            .build()
            .encode()
            .toUri();

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Mono.fromSupplier(() ->
                        restTemplate.exchange(WORLD_TIME_URI, HttpMethod.GET, new HttpEntity<String>(headers), String.class)
                )
                .onErrorMap(HttpClientErrorException.class, (HttpClientErrorException ex) -> {
                    if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return new TimezoneNotFoundException(ex.getResponseBodyAsString());
                    }
                    return new HttpClientErrorException(ex.getStatusCode());
                })
                .map(response -> {
                    DocumentContext jsonContext = JsonPath.parse(response.getBody());
                    String dateTime = jsonContext.read("$.datetime");
                    return dateTime;
                })
                .subscribe(Logger::onNext,
                        Logger::onError,
                        Logger::onComplete);
    }
}
