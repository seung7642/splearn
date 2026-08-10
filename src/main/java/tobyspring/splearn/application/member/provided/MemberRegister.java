package tobyspring.splearn.application.member.provided;

import jakarta.validation.Valid;
import tobyspring.splearn.domain.member.Member;

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {

    /**
     * 흔히 애플리케이션(내부, 헥사곤)에서 외부(어댑터)로 데이터를 반환할 때,
     * JPA 엔티티를 반환하지 말고, DTO로 변환해서 반환하라고들 하는데,
     * 난(토비) JPA 엔티티를 반환해도 괜찮다고 생각한다.
     *
     * @param registerRequest
     * @return
     */
    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);

    Member deactivate(Long memberId);

    Member updateInfo(Long memberId, @Valid MemberInfoUpdateRequest memberInfoUpdateRequest);
}
