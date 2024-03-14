/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package lab2;

import java.sql.Connection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author Qina&Kaiwen
 */
public class DatabaseTest {
    
    @BeforeAll
    static void setUpClass() {
        Database.openPropsFile();
    }

    @Test
    void testSingletonConnection() throws Exception {
        Connection firstConnection = Database.getConnection();
        Connection secondConnection = Database.getConnection();

        // Verify that both calls to getConnection return the same Connection instance
        assertSame(firstConnection, secondConnection, "Database should return the same connection instance on multiple calls.");
    }
    
}
