package recipebook;

import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
    private static Scanner in = new Scanner(System.in);

    // prompts the user for an input and returns the string
    private static String getInput() {
        String input = "";
        while (input.equals("")) {
            System.out.print("Search: ");
            input = in.nextLine();
        }
        return input;
    }

    private static ArrayList<RecipeADT> search(String input) {
        ArrayList<RecipeADT> results = new ArrayList<RecipeADT>();
        input = input.replaceAll("\\ ", "");
        String[] searches = input.split(",");
        //TODO need the hash table complete...
        
        // get recipes by ingredient for each search query
        // intersect results
        
        return null;
    }
    
    // main interaction with user
    public static void run() {
        ArrayList<RecipeADT> results;
        String search = getInput();
        
        // main loop
        while (!search.equals("exit") && !search.equals("quit")) {
            // do things with search string
            System.out.println(search);
            
            // for next loop
            search = getInput();
        }
        in.close();
    }
}
