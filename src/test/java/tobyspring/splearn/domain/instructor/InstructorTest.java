package tobyspring.splearn.domain.instructor;

import org.junit.jupiter.api.Test;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstructorTest {

    @Test
    void apply() {
        Member member = MemberFixture.createActiveMember();

        Instructor instructor = Instructor.apply(member);

        assertThat(instructor.member).isEqualTo(member);
        assertThat(instructor.status).isEqualTo(InstructorStatus.PENDING);
    }

    @Test
    void applyFailedMemberNotActive() {
        Member member = MemberFixture.createMember(); // PENDING

        assertThatThrownBy(() -> Instructor.apply(member))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void approve() {
        Instructor instructor = InstructorFixture.createInstructor();

        instructor.approve();

        assertThat(instructor.status).isEqualTo(InstructorStatus.ACTIVE);
    }

    @Test
    void approveFailed() {
        Instructor instructor = InstructorFixture.createActiveInstructor();

        assertThatThrownBy(() -> instructor.approve())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void reject() {
        Member member = MemberFixture.createActiveMember();
        Instructor instructor = Instructor.apply(member);

        instructor.reject();

        assertThat(instructor.status).isEqualTo(InstructorStatus.REJECTED);
    }

    @Test
    void rejectFailed() {
        Instructor instructor = InstructorFixture.createInstructor();
        instructor.reject();

        assertThatThrownBy(() -> instructor.approve())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void isActive() {
        Instructor instructor = InstructorFixture.createInstructor();

        assertThat(instructor.isActive()).isFalse();

        instructor.approve();
        assertThat(instructor.isActive()).isTrue();
    }

    @Test
    void ensureActive() {
        Instructor instructor = InstructorFixture.createInstructor();

        assertThatThrownBy(() -> instructor.ensureActive())
                .isInstanceOf(IllegalStateException.class);

        instructor.approve();

        instructor.ensureActive();
    }
}