package com.alp.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static String mysqlDriverName = "com.mysql.jdbc.Driver";

    public static Connection conn() {
        Connection con = null;
        try {
            Class.forName(mysqlDriverName);
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter", "root", "root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return con;
    }
}
