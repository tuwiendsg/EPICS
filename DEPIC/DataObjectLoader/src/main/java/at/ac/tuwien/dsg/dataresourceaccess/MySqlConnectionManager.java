/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataresourceaccess;

import java.sql.*;
import java.util.*;
import com.mysql.jdbc.Driver;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mitdacit
 */
public class MySqlConnectionManager {
 //private static String connectionString = "jdbc:mysql://localhost:3306/it?user=root;password=";
    //private static String connectionString = "jdbc:mysql://10.8.52.21/it?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";

    private String connectionString;
    private String userName;
    private String password;

    public MySqlConnectionManager() {
    }

    public MySqlConnectionManager(String connectionString, String userName, String password) {
        this.connectionString = connectionString;
        this.userName = userName;
        this.password = password;
    }

    

    public ResultSet ExecuteQuery(String sql) {
        ResultSet result = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionURL = connectionString;
            Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement stmt = connection.createStatement();
            String mysql = sql;

            result = stmt.executeQuery(mysql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySqlConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySqlConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public int ExecuteUpdate(String sql) {
        int result = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionURL = connectionString;
            Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            Statement stmt = connection.createStatement();
            String mysql = sql;

            result = stmt.executeUpdate(mysql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySqlConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySqlConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

}
