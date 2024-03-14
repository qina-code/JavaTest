/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package lab2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        // Create a mock CSV file with invalid data
        String mockCsvContent = "InvalidID,John,Doe,InvalidCourse,Programming,InvalidTerm,2025";
        Files.writeString(tempDir.resolve("mock-bulk-import.csv"), mockCsvContent);
        
        // Assuming Database properties and connections are mocked or set to a test configuration
    }

    @Test
    void testErrorReportCreation() {
        // Assuming App has been modified to take file paths as arguments or can be set programmatically
        String[] args = new String[] {tempDir.resolve("mock-bulk-import.csv").toString()};
        App.main(args);

        // Assuming the App class writes the error report to a known location, adjust as necessary
        Path errorReportPath = tempDir.resolve("error-report.md");

        assertTrue(Files.exists(errorReportPath), "Error report was not created.");

        
    }
}
