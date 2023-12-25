package org.filehide.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection connection = null;
    public static Connection getConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/filehideproject?useSSL=false", "root", "santhu");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("Connection established");
        return connection;
    }
    public static void closeConnection(){
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Connection closed");
    }
}
