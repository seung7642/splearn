package tobyspring.splearn.domain.instructor;

import jakarta.persistence.Embeddable;

@Embeddable
public enum InstructorStatus {
    PENDING, ACTIVE, REJECTED
}
