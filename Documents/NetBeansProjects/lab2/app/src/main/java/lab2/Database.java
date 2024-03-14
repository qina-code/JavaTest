/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * open database properties file 
 * establish database connection by loading properties
 */
public class Database {
    private static final String PROP_FILE_PATH = "../app/data/database.properties";
    private static Properties dbProperties = new Properties();
    private static Connection connection = null;

    private Database() { }

    
    public static Connection getConnection() throws Exception {
        if (connection == null) {
            String url = "jdbc:" + dbProperties.getProperty("db") + "://" +
                         dbProperties.getProperty("host") + ":" + dbProperties.getProperty("port") + "/" +
                         dbProperties.getProperty("name");
            connection = DriverManager.getConnection(url, dbProperties.getProperty("user"), dbProperties.getProperty("pass"));
        }
        return connection;
    }

    static String[] openPropsFile() {
         try (InputStream input = new FileInputStream(PROP_FILE_PATH)) {
            dbProperties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error loading database properties.", ex);
        }
        return null;
    }
}
