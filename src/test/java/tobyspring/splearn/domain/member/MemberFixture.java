package tobyspring.splearn.domain.member;

import org.instancio.Instancio;
import org.instancio.Select;
import org.springframework.lang.NonNull;
import org.springframework.test.util.ReflectionTestUtils;
import tobyspring.splearn.application.member.provided.MemberRegisterRequest;

import static org.instancio.Select.*;

public class MemberFixture {

    public static @NonNull MemberRegisterRequest createMemberRegisterRequest(String email) {
        return Instancio.of(MemberRegisterRequest.class)
                .set(field(MemberRegisterRequest::email), email)
                .create();
    }

    public static @NonNull MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest(Instancio.gen().net().email().get());
    }

    public static @NonNull PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }

    public static Member createMember() {
        return Member.register(createMemberRegisterRequest().toInfo(), createPasswordEncoder());
    }

    public static Member createMember(Long id) {
        Member member = Member.register(createMemberRegisterRequest().toInfo(), createPasswordEncoder());
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }

    public static Member createMember(String email) {
        return Member.register(createMemberRegisterRequest(email).toInfo(), createPasswordEncoder());
    }

    public static Member createActiveMember() {
        Member member = createMember();
        member.activate();
        return member;
    }
}
