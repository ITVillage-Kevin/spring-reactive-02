package com.itvillage.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {
    private Long id;
    private String email;
    private String name;
}
