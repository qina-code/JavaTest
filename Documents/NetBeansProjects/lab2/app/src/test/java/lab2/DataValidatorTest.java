/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package lab2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Qina&Kaiwen
 */
public class DataValidatorTest {
    
    private final DataValidator validator = new DataValidator();

    @Test
    void testValidateStudentId() {
        assertTrue(validator.validateStudentId("123456789"));
        assertFalse(validator.validateStudentId("12345678")); // Less than 9 digits
        assertFalse(validator.validateStudentId("abcdefghi")); // Not digits
    }

    @Test
    void testValidateCourseId() {
        assertTrue(validator.validateCourseId("ABC1234"));
        assertFalse(validator.validateCourseId("AB12345")); // Not 3 letters followed by 4 digits
        assertFalse(validator.validateCourseId("ABCD123")); // More than 3 letters
        assertFalse(validator.validateCourseId("1234ABC")); // Digits before letters
    }

    @Test
    void testValidateTerm() {
        assertTrue(validator.validateTerm("WINTER"));
        assertTrue(validator.validateTerm("SUMMER"));
        assertTrue(validator.validateTerm("FALL"));
        assertFalse(validator.validateTerm("SPRING")); // Invalid term
        assertFalse(validator.validateTerm("")); // Empty string
    }

    @Test
    void testValidateYear() {
        assertTrue(validator.validateYear("2000")); // Within valid range
        assertFalse(validator.validateYear("1966")); // Before founding year
        int futureYear = java.time.Year.now().getValue() + 1;
        assertFalse(validator.validateYear(String.valueOf(futureYear))); // Future year
    }

    @Test
    void testTermToDatabaseValue() {
        assertEquals(1, validator.termToDatabaseValue("WINTER"));
        assertEquals(2, validator.termToDatabaseValue("SUMMER"));
        assertEquals(3, validator.termToDatabaseValue("FALL"));
        assertEquals(-1, validator.termToDatabaseValue("SPRING")); // Invalid term
    }
    
}
