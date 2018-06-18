/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.sql.Date;

/**
 *
 * @author chris
 */
public class DatabaseConnection {
    
    private Connection connection;

    public boolean connectToMysql(String host, String database, String user, String passwd) {
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String connectionCommand = "jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + passwd + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(connectionCommand);
            System.out.println(true);
            return true;

        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
            System.out.println("false");
            System.out.println(ex);
            return false;
        }
    }
    
    public void writeToDatabase() throws SQLException{
        Statement stmt = connection.createStatement();
        String sql = "INSERT INTO testtabelle " 
                + "VALUES (1,'Groeber', 'Christian', '5606')";
        stmt.executeUpdate(sql);
    }
}
