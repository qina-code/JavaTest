/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab2;



import java.util.ArrayList;
import java.util.List;
/**
 * a class to process csv file line by line
 */
public class CsvProcessingLogic {
    private final StudentDAO studentDAO;
    private final CourseDAO courseDAO;
    private final StudentCourseDAO studentCourseDAO;
    private final DataValidator validator;

    public CsvProcessingLogic(StudentDAO studentDAO, CourseDAO courseDAO, StudentCourseDAO studentCourseDAO, DataValidator validator) {
        this.studentDAO = studentDAO;
        this.courseDAO = courseDAO;
        this.studentCourseDAO = studentCourseDAO;
        this.validator = validator;
    }

   public List<String> processCsvLine(String[] values, int lineNumber) {
    List<String> messages = new ArrayList<>();
    StringBuilder errorMessageBuilder = new StringBuilder();
    StringBuilder successMessageBuilder = new StringBuilder();

    if (!validator.validateStudentId(values[0].trim())) {
        errorMessageBuilder.append("Invalid Student ID: ").append(values[0].trim()).append("; ");
    }

    if (!validator.validateCourseId(values[3].trim())) {
        errorMessageBuilder.append("Invalid Course ID: ").append(values[3].trim()).append("; ");
    }
    
    int termDbValue = validator.termToDatabaseValue(values[5].toUpperCase().trim());
    if (!validator.validateTerm(values[5].trim()) || termDbValue == -1) {
        errorMessageBuilder.append("Invalid Term: ").append(values[5].trim()).append("; ");
    }

    if (!validator.validateYear(values[6])) {
        errorMessageBuilder.append("Invalid Year: ").append(values[6].trim()).append("; ");
    }

    if (errorMessageBuilder.length() > 0) {
        // If there are any validation errors, add them to messages
        messages.add("Row " + lineNumber + ": Validation failed - " + errorMessageBuilder.toString());
    } else {
        // If validation passes, attempt to insert data and generate success/failure messages
        try {
            StudentDTO studentDTO = new StudentDTO(values[0].trim(), values[1].trim(), values[2].trim());
            CourseDTO courseDTO = new CourseDTO(values[3].trim(), values[4].trim());
            StudentCourseDTO studentCourseDTO = new StudentCourseDTO(values[0].trim(), values[3].trim(), termDbValue, values[6].trim());

            boolean studentInserted = studentDAO.insertStudent(studentDTO);
            boolean courseInserted = courseDAO.insertCourse(courseDTO);
            boolean studentCourseInserted = studentCourseDAO.insertStudentCourse(studentCourseDTO);

            if (studentInserted && courseInserted && studentCourseInserted) {
                successMessageBuilder.append("Row ").append(lineNumber).append(" Success: Student, Course, and Enrollment data inserted.");
                messages.add(successMessageBuilder.toString());
            } else {
                // Here you might want to handle partial failures differently
                // For simplicity, adding a generic success message
                errorMessageBuilder.append("Row ").append(lineNumber).append(" Partial Failure: Check data consistency.");
                messages.add(errorMessageBuilder.toString());
            }
        } catch (Exception e) {
            errorMessageBuilder.append("Row ").append(lineNumber).append(": Database error - ").append(e.getMessage());
            messages.add(errorMessageBuilder.toString());
        }
    }

    return messages;
}
}
