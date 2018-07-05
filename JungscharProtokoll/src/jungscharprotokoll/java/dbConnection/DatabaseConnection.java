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
import static java.sql.JDBCType.NULL;
import java.util.ArrayList;
import jungscharprotokoll.java.model.Leiter;

/**
 *
 * @author chris
 */
public class DatabaseConnection {

    private Connection connection;

    private static DatabaseConnection instance;

    public static DatabaseConnection getInstance() {
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }

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

    public void writeToDatabase() throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "INSERT INTO testtabelle "
                + "VALUES (1,'Groeber', 'Christian', '5606')";
        stmt.executeUpdate(sql);
    }

    public ArrayList<Leiter> getLeiter() throws SQLException {
        ArrayList<Leiter> leiter = new ArrayList<>();
        Statement stmt = connection.createStatement();
        String str = "Select * From leiter";
        ResultSet resultSet = stmt.executeQuery(str);
        while (resultSet.next()) {
            Leiter l = new Leiter(resultSet.getString(3), resultSet.getString(2));
            leiter.add(l);
        }
        return leiter;
    }

    public void writeNewLeiter(String name, String lastName, String email, String gruppe, String position) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "INSERT INTO leiter "
                + "VALUES (" + "'" + getNextLeiterNumber() + "', '" + lastName + "', '" + name + "', '";
        if(email.equals("")){
            sql += NULL + "', '";
        }else{
            sql += email + "', '";
        }
        if(gruppe.equals("")){
            sql += NULL + "', '";
        }else{
            sql += gruppe + "', '";
        }if(position.equals("")){
            sql += NULL + "')";
        }else{
            sql += position + "')";
        }
        stmt.executeUpdate(sql);
    }
    
    public int getNextLeiterNumber() throws SQLException{
        int toReturn = 0;
        Statement stmt = connection.createStatement();
        String str = "Select * From leiter";
        ResultSet resultSet = stmt.executeQuery(str);
        while(resultSet.next()){
            toReturn = resultSet.getInt(1);
        }
        return toReturn + 1;
    }
}
