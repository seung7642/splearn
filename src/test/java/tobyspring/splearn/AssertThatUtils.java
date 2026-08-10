package tobyspring.splearn;

import org.assertj.core.api.AssertProvider;
import org.assertj.core.api.Assertions;
import org.springframework.lang.NonNull;
import org.springframework.test.json.JsonPathValueAssert;
import tobyspring.splearn.application.member.provided.MemberRegisterRequest;

import java.util.function.Consumer;

public class AssertThatUtils {

    public static @NonNull Consumer<AssertProvider<JsonPathValueAssert>> notNull() {
        return value -> Assertions.assertThat(value).isNotNull();
    }

    public static @NonNull Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(MemberRegisterRequest request) {
        return value -> Assertions.assertThat(value).isEqualTo(request.email());
    }
}
