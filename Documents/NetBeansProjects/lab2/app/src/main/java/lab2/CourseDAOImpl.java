/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * A concrete class to implement CourseDAO
 */
public class CourseDAOImpl implements CourseDAO {
    private Connection connection;

    public CourseDAOImpl(Connection connection) {
        this.connection = connection;
    }
    /**
     *  
     * conduct insert operation on Course table
     * @param course the object to be inserted 
     * @return Boolean value if the insert operation is successful or not
    */    
    @Override
    public boolean insertCourse(CourseDTO course) {
     String query = "INSERT INTO course (courseId, courseName) VALUES (?, ?) ON DUPLICATE KEY UPDATE courseName = VALUES(courseName)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, course.getCourseId());
            statement.setString(2, course.getCourseName());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Instead of e.printStackTrace(); you can handle it as below:
            String error = "Error inserting course with ID " + course.getCourseId() + ": " + e.getMessage();
            // Here you could log the error to a file or console in a more controlled manner
           
            return false;
        }
    }
    
}
