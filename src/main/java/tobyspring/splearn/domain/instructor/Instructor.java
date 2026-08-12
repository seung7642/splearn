package tobyspring.splearn.domain.instructor;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;
import tobyspring.splearn.domain.AbstractEntity;
import tobyspring.splearn.domain.member.Member;

import static org.springframework.util.Assert.*;

@Entity
@Getter
@ToString(callSuper = true, exclude = {"member"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Instructor extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    Member member;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    InstructorStatus status;

    public static Instructor apply(Member member) {
        // null 체크를 따로 하지 않는 이유는 어차피 null이면 아래 코드에서 예외가 발생할 것이기 때문
        state(member.isActive(), "등록 완료 상태가 아닌 회원은 강사 신청을 할 수 없습니다.");

        Instructor instructor = new Instructor();
        instructor.member = member;
        instructor.status = InstructorStatus.PENDING;

        return instructor;
    }

    public void approve() {
        state(status == InstructorStatus.PENDING, "승인 대기 상태가 아닌 강사는 승인할 수 없습니다.");

        status = InstructorStatus.ACTIVE;
    }

    public void reject() {
        state(status == InstructorStatus.PENDING, "승인 대기 상태가 아닌 강사는 거절할 수 없습니다.");

        status = InstructorStatus.REJECTED;
    }

    public boolean isActive() {
        return status == InstructorStatus.ACTIVE;
    }

    public void ensureActive() {
        state(isActive(), "ACTIVE 상태가 아닙니다.");
    }
}
