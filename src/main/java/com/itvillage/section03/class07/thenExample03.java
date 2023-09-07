package com.itvillage.section03.class07;

import com.itvillage.common.Member;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Mono;

/**
 * then 활용 예제
 *  - 1 개의 task가 모두 끝났을 때, Complete Signal을 전달해서 추가 task를 수행하는 예제
 */
public class thenExample03 {
    public static void main(String[] args) {
        String email = "kevin@gmail.com";
//        String email = "tom@gmail.com";
        verifyExistMember(email)
            .then(
                    saveMember(
                            Member.builder()
                                    .id(1L)
                                    .email(email)
                                    .name("Kevin")
                                    .build()))
            .subscribe(
                    createdMember -> Logger.onNext("created member", createdMember.getEmail()),
                    Logger::onError,
                    () -> {}
            );
    }

    private static Mono<Void> verifyExistMember(String email) {
        return selectFromMemberWhere(email)
                .flatMap(foundMember -> {
                    if (foundMember != null) {
                        return Mono.error(new RuntimeException("Member exists"));
                    }
                    return Mono.empty();
                });

    }

    private static Mono<Member> selectFromMemberWhere(String email) {
        // 파라미터로 전달 받은 email로 DB에 조회한걸로 가정.
        Logger.info("# select from member where email=" + email);
        Mono<Member> existMember = Mono.just(Member.builder().id(1L).email("kevin@gmail.com").name("Kevin").build());

        return email.equals("kevin@gmail.com") ? existMember : Mono.empty();
    }

    private static Mono<Member> saveMember(Member member) {
        // DB에 저장한걸로 가정
        Logger.info("# insert into member...");
        return Mono.just(member);
    }
}


