package com.ogs.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException,
                                                      SQLException {
		Class.forName("org.sqlite.JDBC");
	    Connection conn =
                DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Ryan\\" +
                                            "Desktop\\Dev\\workspace\\data\\" +
                                            "data");
	    Statement stat = conn.createStatement();

	    ResultSet rs = stat.executeQuery("select base_name from fresh;");
	}
}
