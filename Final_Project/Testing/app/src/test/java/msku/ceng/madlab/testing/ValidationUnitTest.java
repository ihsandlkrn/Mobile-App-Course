package msku.ceng.madlab.testing;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class ValidationUnitTest {


    private boolean isValidPassword(String password) {
        // 8 character, 1 number, 1 Big latter
        String regex = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,}$";
        return password != null && password.matches(regex);
    }

    @Test
    public void passwordValidator_CorrectPassword_ReturnsTrue() {
        // True password format
        assertTrue(isValidPassword("Ahmet123"));
    }

    @Test
    public void passwordValidator_ShortPassword_ReturnsFalse() {
        // less than 8 characters
        assertFalse(isValidPassword("Ahmet1"));
    }

    @Test
    public void passwordValidator_NoDigit_ReturnsFalse() {
        // No number
        assertFalse(isValidPassword("Ahmetkoc"));
    }

    @Test
    public void passwordValidator_NoUpperCase_ReturnsFalse() {
        // Does not contains uppercase letter
        assertFalse(isValidPassword("ahmet123"));
    }
}