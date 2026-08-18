package tobyspring.splearn.application.instructor.provided;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tobyspring.splearn.application.member.provided.MemberRegister;
import tobyspring.splearn.domain.instructor.Instructor;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.support.stereotype.ApplicationServiceTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ApplicationServiceTest
@RequiredArgsConstructor
class InstructorFinderTest {

    final InstructorFinder instructorFinder;
    final InstructorApplication instructorApplication;
    final MemberRegister memberRegister;

    @Test
    void findByMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        member = memberRegister.activate(member.getId());

        Instructor instructor = instructorApplication.apply(new InstructorApplyRequest(member.getId()));

        Instructor found = instructorFinder.findByMember(member.getId()).orElseThrow();

        assertThat(instructor).isEqualTo(found);

        assertThat(instructorFinder.findByMember(Long.MAX_VALUE).isPresent()).isFalse();
    }
}