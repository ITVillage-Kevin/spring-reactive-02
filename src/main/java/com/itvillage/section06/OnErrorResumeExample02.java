package com.itvillage.section06;

import com.itvillage.common.Member;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Mono;

/**
 * then 활용 예제
 *  - 1 개의 task가 모두 끝났을 때, Complete Signal을 전달해서 추가 task를 수행하는 예제
 */
public class OnErrorResumeExample02 {
    public static void main(String[] args) {
        String email = "kevin@gmail.com";
//        String email = "tom@gmail.com";
        verifyExistMember(email)
                .onErrorResume(throwable -> {
                    // 에러 처리 로직을 추가
                    Logger.onError(throwable);
                    return Mono.empty(); // 빈 Mono를 반환하여 이후 동작이 실행되지 않도록 함
                })
                .flatMap(notUse -> saveMember(
                                        Member.builder()
                                                .id(1L)
                                                .email(email)
                                                .name("Kevin")
                                                .build())
                )
            .subscribe(
                    createdMember -> Logger.onNext("created member", createdMember.getEmail()),
                    Logger::onError,
                    () -> Logger.onComplete()
            );
    }

    private static Mono<Member> verifyExistMember(String email) {
        return selectFromMemberWhere(email)
                .switchIfEmpty(Mono.just(Member.builder().build())) // saveMember()를 실행시킬 수 있도록 비어있는 member를 리턴한다.
                .flatMap(foundMember -> {
                    if (foundMember != null && foundMember.getEmail() != null) {
                        return Mono.error(new RuntimeException("Member exists"));
                    }
                    return Mono.just(foundMember);
                });
    }

    private static Mono<Member> selectFromMemberWhere(String email) {
        // 파라미터로 전달 받은 email로 DB에 조회한걸로 가정.
        Logger.info("# select from member where email=" + email);
        Mono existMember = Mono.just(Member.builder().id(1L).email("kevin@gmail.com").name("Kevin").build());

        return email.equals("kevin@gmail.com") ? existMember : Mono.empty();
    }

    private static Mono<Member> saveMember(Member member) {
        // DB에 저장한걸로 가정
        Logger.info("# insert into member...");
        return Mono.just(member);
    }
}


