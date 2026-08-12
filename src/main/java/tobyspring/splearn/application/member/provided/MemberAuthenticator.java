package tobyspring.splearn.application.member.provided;

import jakarta.validation.Valid;
import tobyspring.splearn.domain.member.Member;

/**
 * 회원 인증
 *   - ACTIVE 상태인 회원만 로그인할 수 있다.
 */
public interface MemberAuthenticator {

    Member login(@Valid MemberLoginRequest loginRequest) throws LoginFailedException;
}
