package tobyspring.splearn.domain.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailTest {

    @Test
    void equality() {
        var email1 = new Email("test@example.com");
        var email2 = new Email("test@example.com");

        assertEquals(email1, email2);
    }
}