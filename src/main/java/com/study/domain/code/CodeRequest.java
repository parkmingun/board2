package com.study.domain.code;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeRequest {
    private Long id;        // PK
    private String cdGroup; // 코드그룹
    private String name;    // 코드이름
}
