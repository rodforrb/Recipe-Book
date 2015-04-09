package recipebook;

import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
    private static Scanner in = new Scanner(System.in);

    private static void print(RecipeADT r){
        if (r == null){
            System.out.printf("ERROR: Null Recipe.");
            return;
        }
        String format =  "\n%-50s\n%-50s\n%-50s\n%-50s\n%-50s\n";
        System.out.printf(format,
                "Recipe Name: " + r.getRecipeName(),
                "Rating: " +Double.toString(r.getRating()) + " / 100.0", 
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
        for (String s : r.getMeasurements()){
            System.out.println("    " + s);
        }
        System.out.println("\nDirections");
        int step = 0;
        for (String s: r.getDirections()){
            step++;
            System.out.println("    " + step + ". " + s);
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
        // used to store results compared on rating
        RBT<RecipeADT, RecipeADT> results = new RBT<RecipeADT, RecipeADT>();
        // get all include queries
        ArrayList<String> includes = selectAll(input, '+', ' ');
        if (includes.size() == 0) return results.keys(); // nothing to search for
        // get all exclude queries
        ArrayList<String> excludes = selectAll(input, '-', ' ');
        
        // get all included recipes
        RBT<RecipeADT, RecipeADT> inc = new RBT<RecipeADT, RecipeADT>();
        String ingredient = includes.get(0);
        for (RecipeADT r : Main.recipesByIngredient.find(ingredient)) {
            inc.insert(r, r);
        }
        
        RBT<RecipeADT, RecipeADT> current;
        for (int i = 1; i < includes.size(); i++) {
            current = new RBT<RecipeADT, RecipeADT>();
            for (RecipeADT r : Main.recipesByIngredient.find(includes.get(i))) {
                // insert only if the recipe exists in the list already (intersection of two lists)
                if (inc.find(r) != null) current.insert(r, r);
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
            if (exc.find(r.getRecipeName()) == null) results.insert(r, null);
        }
        
        return results.keys();
    }
    
    private static void output(ArrayList<RecipeADT> results) {
        int page = 0;
        int j;
        
        // continue loop until returned
loop:   while (true) {
            cls();
            System.out.println(Integer.toString(results.size()) + " results.");
            for (int i = 1; i < 10; i++) {
                j = i - 1 + page*10;
                // only print up to range
                if (j >= results.size()) break;
                
                System.out.println(Integer.toString(i) + ": " + results.get(j).getRecipeName());
            }
            System.out.println("0: Previous \nEnter: next");
            
            
            // to get user recipe choice
            int choice = -1;
            String input;
            while (choice < 1 || (results.size() - page*10) < choice) {
                try {
                    System.out.print("Choice: ");
                    input = in.nextLine();
                    
                    // go to next page
                    if (input.equals("") && page*10 < results.size()) {
                        page++;
                        continue loop;
                    }
                    
                    // get int from input
                    choice = Integer.parseInt(input);
                    
                    // go to previous page
                    if (choice == 0 && page > 0) {
                        page--;
                        continue loop;
                    }
                    
                } catch (Exception e) {
                    // return on other input
                    return;
                }
            }
                
            // valid input given, print recipe
            cls();
            print(results.get(page*10+choice-1));
            
            // wait for user input
            System.out.println("Press enter to return.");
            in.nextLine();
        }
    }
    
    // print a help message
    private static void help() {
        cls();
        System.out.println("Enter ingredients separated by spaces, \nprefixed by '+' to include and \nprefixed by '-' to exclude.");
        System.out.println("ex. Search: +a +b -c +d -e \nwill return results including a, b, and d, but excluding c and e.");
    }
    
    // print lines to clear the console
    private static void cls() {
        System.out.println("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
        System.out.println("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
        System.out.println("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
    }
    
    // main interaction with user
    public static void run() {
        cls();
        System.out.println("Type 'help' for instructions.");
        ArrayList<RecipeADT> results;
        String search = getInput();
        
        // main loop
        while (!search.equals("exit") && !search.equals("quit")) {
            // do things with search string
            if (search.equals("help")) help();
            else {
                results = search(search);
                output(results);
                cls();
            }
            
            
            
            // for next loop
            search = getInput();
        }
        
        /* program ending stuff */
        
        // close scanner
        in.close();
    }
}
