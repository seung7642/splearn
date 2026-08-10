package tobyspring.splearn.domain.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static tobyspring.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.member.MemberFixture.createPasswordEncoder;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = createPasswordEncoder();
        member = Member.register(createMemberRegisterRequest().toInfo(), passwordEncoder);
    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void activate() {
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(() -> {
            member.deactivate();
        }).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(() -> {
            member.deactivate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isEqualTo(true);
        assertThat(member.verifyPassword("hello", passwordEncoder)).isEqualTo(false);
    }

    @Test
    void changePassword() {
        member.changePassword("verysecret2", passwordEncoder);

        assertThat(member.verifyPassword("verysecret2", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();

        assertThat(member.isActive()).isTrue();

        member.deactivate();

        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() ->
                Member.register(createMemberRegisterRequest("invalid email").toInfo(), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest().toInfo(), passwordEncoder);
    }

    @Test
    void updateInfo() {
        member.activate();

        var updateInfo = new MemberInfoUpdateInfo("Leo", "toby100", "자기소개");
        member.updateInfo(updateInfo);

        assertThat(member.getNickname()).isEqualTo(updateInfo.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(updateInfo.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(updateInfo.introduction());
    }

    @Test
    void updateInfoFail() {
        assertThatThrownBy(() -> {
            var updateInfo = new MemberInfoUpdateInfo("Leo", "toby100", "자기소개");
            member.updateInfo(updateInfo);
        }).isInstanceOf(IllegalStateException.class);
    }
}