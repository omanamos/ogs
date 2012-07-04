package com.ogs.test;

import static com.ogs.grounder.Main.parseXml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.ogs.datastore.Ingredient;
import com.ogs.datastore.MatchedGroceryItem;
import com.ogs.grounder.IngredientMatcher;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException,
                                                  SQLException {
        Class.forName("org.sqlite.JDBC");
        Connection conn =
            DriverManager.getConnection("jdbc:sqlite:database");
        System.out.print("Loading database...");
        IngredientMatcher matcher = new IngredientMatcher(conn);
        System.out.println("Done!");

        Scanner input = new Scanner(System.in);
        System.out.print("Enter an ingredient (<rtn> to quit)): ");
        String line = input.nextLine();
        while (line == null || !line.equals("")) {
            System.out.println("Results:");
            System.out.println("-----------------");
            int cnt = 0;
            for (MatchedGroceryItem item : matcher.match(new Ingredient(line))) {
                if (cnt > 10)
                    break;
                System.out.println(item);
                cnt++;
            }
            System.out.print("Enter an ingredient (<rtn> to quit)): ");
            line = input.nextLine();
        }
        System.out.println("Goodbye!");
    }
}
