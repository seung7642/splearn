package tobyspring.splearn.application.member.provided;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
class MemberAuthenticatorTest {

    @Autowired
    private MemberAuthenticator authenticator;

    @Autowired
    private MemberRegister memberRegister;
    @Autowired
    private MemberAuthenticator memberAuthenticator;

    @Test
    void login() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        Member member = memberRegister.register(registerRequest);
        member.activate();

        Member loggedInMember = memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), registerRequest.password()));

        assertThat(loggedInMember).isEqualTo(member);
    }

    @Test
    void loginFailedNotActive() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest);

        assertThatThrownBy(() -> memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), registerRequest.password())))
            .isInstanceOf(LoginFailedException.class);
    }

    @Test
    void loginFailedEmailNotExist() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        assertThatThrownBy(() -> memberAuthenticator.login(new MemberLoginRequest("notexist@email.com", registerRequest.password())))
                .isInstanceOf(LoginFailedException.class);
    }

    @Test
    void loginFailedWrongPassword() {
        var registerRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(registerRequest).activate();

        assertThatThrownBy(() -> memberAuthenticator.login(new MemberLoginRequest(registerRequest.email(), "wrongpassword")))
                .isInstanceOf(LoginFailedException.class);
    }
}