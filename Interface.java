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
        
        System.out.print("include: ");
        for (String s : includes) System.out.print(s + ',');
        System.out.println();
        System.out.print("exclude: ");
        for (String s : excludes) System.out.print(s + ',');
        System.out.println();
        
        
        //TODO need the hash table complete...
        
        // get recipes by ingredient for each search query
        // intersect results
        
        return null;
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
                search(search);
            }
            
            
            
            // for next loop
            search = getInput();
        }
        
        /* program ending stuff */
        
        // close scanner
        in.close();
    }
}
