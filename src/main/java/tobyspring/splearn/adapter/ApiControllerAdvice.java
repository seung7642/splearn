package tobyspring.splearn.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tobyspring.splearn.domain.member.DuplicateEmailException;
import tobyspring.splearn.domain.member.DuplicateProfileException;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler({DuplicateEmailException.class, DuplicateProfileException.class})
    public ProblemDetail duplicateExceptionHandler(RuntimeException exception) {
        return getProblemDetail(HttpStatus.CONFLICT, exception);
    }

    private static @NonNull ProblemDetail getProblemDetail(HttpStatus status, Exception exception) {
        // RFC 9457
        // 예외는 응답을 어떻게 줄까?
        // 기본 HTTP 상태코드만으로는 이메일 중복에 해당하는 정보를 제공하지 못 한다.
        // 물론, 409 CONFLICT를 줄 수도 있겠지만, 이것만으로는 이게 이메일 중복이라는 정보를 알 수 없다.
        // 그래서 기존에는 {status: , error:, data: } 같은 응답 바디로 주기도 했다.
        // 그런데 ProblemDetail이 나온 이상 이렇게 반환할 필요는 없다고 생각한다.
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());

        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("exception", exception.getClass().getSimpleName());

        return problemDetail;
    }
}
