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
        return input.toLowerCase();
    }
    
    private static ArrayList<String> selectAll(String str, char start, char end) {
        /**
         * returns a list of all strings prefaced and followed by given characters
         * @param str, the string to look through
         * @param start, the character before strings to select
         * @param end, the character after strings to select
         */
        ArrayList<String> ret = new ArrayList<String>();
        String builder = "";
        boolean open = false;
        for (char c : str.toCharArray()) {
            if (c == start) {
                builder = "";
                open = true;
            }
            else if (c == end && open) {
                ret.add(builder);
                builder = "";
                open = false;
            }
            else builder += c;
        }
        // add string at the end of the word
        if (open) ret.add(builder);
        
        return ret;
    }

    private static ArrayList<RecipeADT> search(String input) {
        ArrayList<RecipeADT> results = new ArrayList<RecipeADT>();
        ArrayList<String> includes = selectAll(input, '+', ' ');
        ArrayList<String> excludes = selectAll(input, '-', ' ');
        
        // get all included recipes
        RBT<String, RecipeADT> inc = new RBT<String, RecipeADT>();
        for (String s : includes) {
            for (RecipeADT r : Main.recipesByIngredient.find(s)) {
                inc.insert(r.getRecipeName(), r);
            }
        }
        
        // get all excluded recipes
        RBT<String, RecipeADT> exc = new RBT<String, RecipeADT>();
        for (String s : excludes) {
            for (RecipeADT r : Main.recipesByIngredient.find(s)) {
                exc.insert(r.getRecipeName(), r);
            }
        }
        
        // find results
        for (RecipeADT r : inc.list()) {
            // do not add if recipe is in excluded list
            if (exc.find(r.getRecipeName()) == null) results.add(r);
        }
        
        return results;
    }
    
    // print a help message
    private static void help() {
        System.out.println();
        System.out.println("Enter ingredients separated by spaces, \nprefixed by '+' to include and \nprefixed by '-' to exclude.");
        System.out.println("ex. Search: +a +b -c +d -e \nwill return results including a, b, and d, but excluding c and e.");
    }
    
    // main interaction with user
    public static void run() {
        System.out.println("Type 'help' for instructions.");
        ArrayList<RecipeADT> results;
        String search = getInput();
        
        // main loop
        while (!search.equals("exit") && !search.equals("quit")) {
            // do things with search string
            if (search.equals("help")) help();
            else {
                results = search(search);
                for (RecipeADT r : results) {
                    System.out.println(r.getRecipeName());
                }
            }
            
            
            
            // for next loop
            search = getInput();
        }
        
        /* program ending stuff */
        
        // close scanner
        in.close();
    }
}
