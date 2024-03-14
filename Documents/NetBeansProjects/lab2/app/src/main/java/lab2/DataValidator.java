/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab2;

/**
 * A data validator to test if data meet the following requirements:
 * 1. studentId - length 9 digits
 * 2. courseId - length 7 characters - 3 letters followed by 4 digits
 * 3. term - one of three options - "WINTER", "SUMMER", "FALL" - stored in the db as 1, 2, 3 respectively
 * 4. year - length 4 digits - minimum founding of Algonquin College to now
 */
public class DataValidator {
    private static final int FOUNDING_YEAR_ALGONQUIN_COLLEGE = 1967; // Assuming founding year

    public  boolean validateStudentId(String studentId) {
        return studentId.matches("\\d{9}");
    }

    public  boolean validateCourseId(String courseId) {
        if (courseId.length() == 7 &&
                courseId.matches("[a-zA-Z]{3}\\d{4}")){
            return true;
        }
        return false;
        
    }

    public  boolean validateTerm(String term) {
        return term.toUpperCase().trim().equals("WINTER") || term.toUpperCase().trim().equals("SUMMER") || term.toUpperCase().trim().equals("FALL");
    }

    public  boolean validateYear(String year) {
        int currentYear = java.time.Year.now().getValue();
        if (year.matches("\\d{4}")) {
            int yearInt = Integer.parseInt(year);
            return yearInt >= FOUNDING_YEAR_ALGONQUIN_COLLEGE && yearInt <= currentYear;
        }
        return false;
    }

    public int termToDatabaseValue(String term) {
        switch (term.toUpperCase().trim()) {
            case "WINTER":
                return 1;
            case "SUMMER":
                return 2;
            case "FALL":
                return 3;
            default:
                return -1; // Invalid term, handle appropriately
        }
    }
}

