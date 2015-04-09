package recipebook;

import java.util.ArrayList;
import java.util.Scanner;

public class Interface {
    private static Scanner in = new Scanner(System.in);

    // print a nicely formatted recipe
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
        String builder = "";  // temporary string
        boolean open = false; // whether the pair is open
        // for all letters..
        for (char c : str.toCharArray()) {
            // first character starts the word
            if (c == start) {
                builder = "";
                open = true;
            }
            // last character ends the word
            else if (c == end && open) {
                ret.add(builder);
                builder = "";
                open = false;
            }
            // middle characters make the words
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
        
        // get first set
        String ingredient = includes.get(0);
        // for all recipes in the set..
L1:     for (RecipeADT r : Main.recipesByIngredient.find(ingredient)) {
    
            // check if they contain includes
L2:         for (String s : includes) {
                // if not, check next recipe
                for (String t : r.getMeasurements()) {
                    // if one is found, check next ingredient
                    if (t.toLowerCase().contains(s)) continue L2;
                }
                continue L1;
            }
            
            // check if contain any excludes
            for (String s : excludes) {
                // if so, check next recipe
                for (String t : r.getMeasurements()) {
                    if (t.toLowerCase().contains(s)) continue L1;
                }
            }
            
            // if checks pass, add to tree
            inc.insert(r, r);
        }
        // returns the recipes sorted by rating
        return inc.keys();
    }
    
    // lists the results of the search
    private static void output(ArrayList<RecipeADT> results) {
        final int RESULTS_PER_PAGE = 20;
        int page = 0;
        int onPage;
        int j;
        
        // continue loop until returned
loop:   while (true) {
            // calculate how many results are on the page
            onPage = ((page+1)*RESULTS_PER_PAGE > results.size() ? results.size()%RESULTS_PER_PAGE - 1 : RESULTS_PER_PAGE-1);
            cls();
            // shows "x-y / z"
            System.out.println("Results " + 
                               Integer.toString(page*RESULTS_PER_PAGE+1) + 
                               "-" + Integer.toString(page*RESULTS_PER_PAGE + onPage + 1) + 
                               "/" + Integer.toString(results.size()));
            
            // lists results on page
            for (int i = 1; i <= RESULTS_PER_PAGE; i++) {
                j = i - 1 + page*RESULTS_PER_PAGE;
                // only print up to range
                if (j >= results.size()) break;
                
                System.out.println(Integer.toString(i) + ": " + results.get(j).getRecipeName());
            }
            System.out.println("0: Previous \nEnter: next");
            
            
            // to get user recipe choice
            int choice = -1;
            String input;
            while (choice < 1 || onPage < choice) {
                try {
                    System.out.print("Choice: ");
                    input = in.nextLine();
                    
                    // go to next page
                    if (input.equals("") && page*RESULTS_PER_PAGE + onPage + 1 < results.size()) {
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
            print(results.get(page*RESULTS_PER_PAGE+choice-1));
            
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
        System.out.println("When viewing search results, type 'back' to return to searching.");
    }
    
    // print lines to clear the console
    private static void cls() {
        System.out.println("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
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
