package tobyspring.splearn.application.instructor.required;

import org.springframework.data.repository.Repository;
import tobyspring.splearn.domain.instructor.Instructor;

import java.util.Optional;

public interface InstructorRepository extends Repository<Instructor, Long> {

    // JPA에는 기본적으로 `persist()`, `merge()` 메서드가 있다.
    // save 메서드는 `persist()`와 `merge()`를 모두 호출한다.
    Instructor save(Instructor instructor);

    Optional<Instructor> findById(Long instructorId);

    Optional<Instructor> findByMemberId(Long memberId);
}
