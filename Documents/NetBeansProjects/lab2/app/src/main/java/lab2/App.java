/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
 /**
  * @author Qina Yu (student number: 041093626)
  * the main entrance of the program
  * Parses the file: bulk-import.csv
  *  Validates each item in each row and updates the database accordingly.
  */
public class App {
    /**
     * Parses the file: bulk-import.csv
     * Validates each item in each row and updates the database accordingly.
     * @param args - 
     */
    
    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        // Open the properties file to load database configurations
        Database.openPropsFile();
        LocalDateTime now = LocalDateTime.now();
        try {
            // Establish database connection
            Connection connection = Database.getConnection();

            // Initialize DAOs with the database connection
            StudentDAO studentDAO = new StudentDAOImpl(connection);
            CourseDAO courseDAO = new CourseDAOImpl(connection);
            StudentCourseDAO studentCourseDAO = new StudentCourseDAOImpl(connection);
            DataValidator validator = new DataValidator();

            // Initialize CsvProcessingService with DAOs and validator
            CsvProcessingLogic csvService = new CsvProcessingLogic(studentDAO, courseDAO, studentCourseDAO, validator);

            // Path to the CSV file
            String csvFilePath = "../app/data/bulk-import.csv";

            // Process the CSV file
            List<String> successMessages = new ArrayList<>();
            List<String> errorMessages = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
                String line;
                int lineNumber = 0;
                while ((line = br.readLine()) != null) {
                    lineNumber++;
                    if (lineNumber == 1) continue; // Skip header

                    String[] values = line.split(",");
                    List<String> messages = csvService.processCsvLine(values, lineNumber);
                    for (String message : messages) {
                    if (message.contains("Success")) {
                        successMessages.add(message);
                    } else {
                        errorMessages.add(message);
                    }
                }
                }
            }
            // Generate report based on messages collected from CSV processing
            String successFilePath = Paths.get("../app/data/import-report.md").toString();
            String errorFilePath = Paths.get("../app/data/error-report.md").toString();
           
            generateReport(successFilePath, "Success Report",dtf.format(now),  successMessages);
            generateReport(errorFilePath, "Error Report", dtf.format(now),errorMessages);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
    * a method to write params into report
    * @param filePath the report path
    * @param reportTitle report name to be written
    * @param dateTime the date and time when report generated
    * @param messages the content of the report
    */
    
  
    public static void generateReport(String filePath, String reportTitle, String dateTime, List<String> messages) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filePath)) {
            writer.println("# " + reportTitle );
             writer.println("Date and Time: " + dateTime);
            for (String message : messages) {
                writer.println("- " + message);
            }
        } catch (Exception e) {
            System.err.println("Failed to write to " + filePath + ": " + e.getMessage());
        }
    }
}