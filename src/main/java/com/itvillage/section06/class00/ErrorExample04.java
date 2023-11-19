package com.itvillage.section06.class00;

import com.itvillage.common.Member;
import com.itvillage.utils.Logger;
import reactor.core.publisher.Mono;

/**
 * error Operator 활용 예제 코드
 * - 입력으로 전달 받은 데이터의 유효성 검증에 실패할 경우 onError signal을 전송하는 예제
 */
public class ErrorExample04 {
    private final static String EXIST_EMAIL = "kevin@gmail.com";
    private final static String NOT_EXIST_EMAIL = "tom@gmail.com";

    public static void main(String[] args) {
        verifyExistMember(EXIST_EMAIL)
//        verifyExistMember(NOT_EXIST_EMAIL)
                .flatMap(notUse -> saveMember(
                                        Member.builder()
                                                .id(1L)
                                                .email(NOT_EXIST_EMAIL)
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

        return email.equals(EXIST_EMAIL) ? existMember : Mono.empty();
    }

    private static Mono<Member> saveMember(Member member) {
        // DB에 저장한걸로 가정
        Logger.info("# insert into member...");
        return Mono.just(member);
    }
}


