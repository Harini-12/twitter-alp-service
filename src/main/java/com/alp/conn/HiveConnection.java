package com.alp.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HiveConnection {
    private static String hiveDriverName = "org.apache.hive.jdbc.HiveDriver";

    public static Connection conn() {
        Connection con = null;
        try {
            Class.forName(hiveDriverName);
            con = DriverManager.getConnection("jdbc:hive2://35.226.227.94:10000/twitter", "balachandrank30",
                    "balachandrank30");
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
