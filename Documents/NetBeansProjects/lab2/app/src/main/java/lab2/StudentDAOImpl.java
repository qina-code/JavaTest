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
 * A concrete class to implement StudentDAO
 */
public class StudentDAOImpl implements StudentDAO {
    

   private Connection connection;

    public StudentDAOImpl(Connection connection) {
        this.connection = connection;
    }
    
     /**
     * conduct insert operation on Student table
     * @param student the object to be inserted 
     * @return Boolean value if the insert operation is successful or not
     */
    @Override
    public boolean insertStudent(StudentDTO student) {
        String query = "INSERT INTO Student (studentId, firstName, lastName) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE firstName = VALUES(firstName), lastName = VALUES(lastName)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getStudentId());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            // Instead of e.printStackTrace(); you can handle it as below:
            String error = "Error inserting student with ID " + student.getStudentId() + ": " + e.getMessage();
            // Here you could log the error to a file or console in a more controlled manner
           
            return false;
        }
    }
}