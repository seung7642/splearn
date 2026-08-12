package tobyspring.splearn.application.member.provided;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tobyspring.splearn.domain.member.MemberInfoUpdateInfo;

public record MemberInfoUpdateRequest(
        @Size(min = 5, max = 20) String nickname,
        @NotNull @Size(max = 15) String profileAddress,
        @NotNull String introduction
) {
    public MemberInfoUpdateInfo toInfo() {
        return new MemberInfoUpdateInfo(nickname, profileAddress, introduction);
    }
}
