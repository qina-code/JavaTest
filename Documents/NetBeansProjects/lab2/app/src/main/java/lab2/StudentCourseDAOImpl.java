/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * A concrete class to implement StudentCourseDAO
 */
public class StudentCourseDAOImpl implements StudentCourseDAO{
    private Connection connection;

    public StudentCourseDAOImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * conduct insert operation on StudentCourse table
     * @param studentCourse the object to be inserted 
     * @return Boolean value if the insert operation is successful or not
     */
    @Override
    public boolean insertStudentCourse(StudentCourseDTO studentCourse) {
        String query = "INSERT INTO Studentcourse (studentId, courseId, term, year) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE term = VALUES(term), year = VALUES(year)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentCourse.getStudentId());
            statement.setString(2, studentCourse.getCourseId());
            statement.setInt(3, studentCourse.getTerm());
            statement.setString(4, studentCourse.getYear());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Instead of e.printStackTrace(); you can handle it as below:
            String error = "Error inserting course or student with ID " + studentCourse.getCourseId() + ": " + e.getMessage() + 
                    studentCourse.getStudentId()+ ": " + e.getMessage();
            // Here you could log the error to a file or console in a more controlled manner
            
            return false;
        }
    }
}
