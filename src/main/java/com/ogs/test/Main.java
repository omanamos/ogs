package com.ogs.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.google.common.collect.Sets;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ogs.datastore.Ingredient;
import com.ogs.datastore.MatchedGroceryItem;
import com.ogs.datastore.MatchedIngredient;
import com.ogs.datastore.Recipe;
import com.ogs.grounder.Grounder;
import com.ogs.grounder.IngredientMatcher;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException,
                                                  IOException {
        if (args.length == 0 || args[0].equals("-i")) {
            runInteractive();
        } else if (args[0].equals("-b")){
            runBatch(args[1]);
        } else {
            System.out.println("Nothing to do!");
        }
    }

    private static void runBatch(String outputPath) throws IOException, SQLException,
                                                           ClassNotFoundException {
        System.out.print("Loading database...");
        Grounder g = new Grounder();
        System.out.println("Done!");

        System.out.print("Grounding recipes...");
        Set<Recipe<Ingredient>> recipes = parseXml("data/raw.xml");
        Set<Recipe<MatchedIngredient>> results = g.ground(recipes);
        BufferedWriter out = new BufferedWriter(new FileWriter(new File(outputPath)));
        for (Recipe<MatchedIngredient> matchedRecipe : results) {
            out.write(matchedRecipe + "\n");
            out.flush();
        }
        out.close();
        System.out.println("Done!");
    }

    public static Set<Recipe<Ingredient>> parseXml(String recipeFileName) {
        Document document = null;
        SAXReader reader = new SAXReader();
        try
            {
                document = reader.read( recipeFileName );
            }
        catch (DocumentException e)
            {
                e.printStackTrace();
            }
        Set<Recipe<Ingredient>> rtn = Sets.newHashSet();
        Element root = document.getRootElement();

        // iterate through child elements of root
        for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
            Element element = i.next();
            rtn.add(Recipe.recipeFromElement(element));
        }
        
        return rtn;
    }

    private static void runInteractive() throws SQLException, ClassNotFoundException {
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
