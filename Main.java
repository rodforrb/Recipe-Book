package recipebook;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static ListRBT<RecipeADT> recipesByIngredient = new ListRBT<RecipeADT>();
	public static RBT<String, RecipeADT> recipes = new RBT<String, RecipeADT>();
	
	public static void save() throws IOException {
		// write to file
		File file = new File("data/recipes");
		BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
		ArrayList<RecipeADT> list = recipes.list();
		for (RecipeADT r : list) {
		    // data is stored in a retrievable format
			out.write(r.data());
		}
		out.close();
	}
	
	public static void main(String[] args) {
		// load everything necessary
		System.out.println("Trying to load file...");
		// try to find file
		try {
			// load in file
			Parser.loadFiles();
			System.out.println("Loaded successfully, " + Integer.toString(recipesByIngredient.size()) + " recipes.");
		
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
				return;
			} catch (IOException e) {
				System.out.println("Something went wrong with saving.");
				e.printStackTrace();
			}
			
		} catch (IOException b) {
			System.out.println("Something went wrong with loading HTML data.");
			b.printStackTrace();
		}
		
		// program sequence
		Interface.run();
	}
}
