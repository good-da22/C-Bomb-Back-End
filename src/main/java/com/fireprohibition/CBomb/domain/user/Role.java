package com.fireprohibition.CBomb.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

	//스프링 싴리티에서는 권한 코드에 항상 ROLE_이 앞에 있어야만 한다.
	// 그래서 코드별 키 값을 ROLE_ xxx 로 지정한다.

	GUEST("ADMIN", "어드민"),
	USER("USER", "사용자");

	private final String key;
	private final String title;
}
