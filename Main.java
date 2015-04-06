package recipebook;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static HashTable<String, RecipeADT> recipesByIngredient = new HashTable<String, RecipeADT>();
	public static RBT<String, RecipeADT> recipes = new RBT<String, RecipeADT>();
	
	public static void save() throws IOException {
		// write to file
		File file = new File("data/recipes");
		BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
		ArrayList<RecipeADT> list = recipes.list();
		for (RecipeADT r : list) {
			out.write(r.data());
		}
		out.close();
	}
	
	public static void getInput() {
       Scanner in = new Scanner(System.in);
       String input = in.next();       
	}
	
	public static void main(String[] args) {
		// load everything necessary
		System.out.println("Trying to load file...");
		// try to find file
		try {
			// load in file
			Parser.loadFiles();
			System.out.println("Loaded successfully, " + Integer.toString(recipes.size()) + " recipes.");
		
		} catch (FileNotFoundException a) {
			// can't find file, parse from website
			System.out.println("File not found, parsing data...");
			Parser.parseHTML();
			System.out.println("Loaded successfully.");
			
			try {
				// save file for future use
				System.out.println("Saving data to file...");
				save();
				System.out.println("Saved successfully.");
			} catch (IOException e) {
				System.out.println("Something went wrong with saving.");
				e.printStackTrace();
			}
			
		} catch (IOException b) {
			System.out.println("Something went wrong with downloading data.");
			b.printStackTrace();
		}
		
		// program sequence
		
	}
}
