package com.ogs.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ogs.datastore.Inventory;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException,
                                                  SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn =
            DriverManager.getConnection("jdbc:sqlite:../data/data");
        Inventory inv = Inventory.buildFromSql(conn);
        System.out.println(inv);
    }
}
