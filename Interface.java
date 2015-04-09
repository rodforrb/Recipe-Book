package recipebook;

import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
    private static Scanner in = new Scanner(System.in);

    private static void output(RecipeADT r){
        if (r == null){
            System.out.printf("ERROR: Null Recipe.");
            return;
        }
        String format =  "\n%-50s\n%-50s\n%-50s\n%-50s\n%-50s\n";
        System.out.printf(format,
                "Recipe Name: " + r.getRecipeName(),
                "Rating: " +Double.toString(r.getrating()) + " / 100.0", 
                "Prep Time: " + r.getPrepTime(),
                "Total Time: " + r.getTotalTime(),
                "Servings: " + r.getServingSize());
        System.out.println("\nAbout The Recipe");
        System.out.println("");
        String[] atr = r.getAboutThisRecipe().split(" ");
        int line_max = 0;
        for (String s:atr){
            line_max++;
            if (line_max > 10){
                System.out.println(" " + s);
                line_max = 0;
            }
            else{
                System.out.print(" " + s);
            }
            
        }
        System.out.println("\n\nIngredients");
        for (String s : r.getIngredients()){
            System.out.println(" >>" + s);
        }
        System.out.println("\nDirections");
        int step = 0;
        for (String s: r.getDirections()){
            step++;
            System.out.println(" " + step+". "+s);
        }
    }

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
        if (includes.size() == 0) return results; // nothing to search for
        ArrayList<String> excludes = selectAll(input, '-', ' ');
        
        // get all included recipes
        RBT<String, RecipeADT> inc = new RBT<String, RecipeADT>();
        String ingredient = includes.get(0);
        for (RecipeADT r : Main.recipesByIngredient.find(ingredient)) {
            inc.insert(r.getRecipeName(), r);
        }
        
        RBT<String, RecipeADT> current;
        for (int i = 1; i < includes.size(); i++) {
            current = new RBT<String, RecipeADT>();
            for (RecipeADT r : Main.recipesByIngredient.find(includes.get(i))) {
                // insert only if the recipe exists in the list already (intersection of two lists)
                if (inc.find(r.getRecipeName()) != null) current.insert(r.getRecipeName(), r);
            }
            inc = current;
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
                int i = 1;
                for (RecipeADT r : results) {
                    System.out.println(Integer.toString(i) + ": " + r.getRecipeName());
                    i++;
                }
                System.out.print("Choice: ");
                int choice = in.nextInt();
                if (0 < choice && choice < results.size()) output(results.get(choice-1));
            }
            
            
            
            // for next loop
            search = getInput();
        }
        
        /* program ending stuff */
        
        // close scanner
        in.close();
    }
}
