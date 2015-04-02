package recipebook;

import java.util.ArrayList;
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
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		ArrayList<RecipeADT> list = recipes.list();
		for (RecipeADT r : list) {
			out.write(r.data());
		}
		out.close();
	}
	
	public static void load() throws IOException, FileNotFoundException {
		// read from file
		BufferedReader reader = new BufferedReader(new FileReader("data/recipes"));

		// read all lines
		String readLine;
		String[] data = new String[5];
		String[] list;
		ArrayList<String> ingredients, directions;
		RecipeADT r;
		while ( (readLine = reader.readLine()) != null) {
			// read name
			data[0] = readLine;
			// read next four elements
			for (int i = 1; i < 5; i++) data[i] = reader.readLine();
			// read ingredients list
			ingredients = new ArrayList<String>();
			list = reader.readLine().split("\\,\\,");
			for (String s : list) ingredients.add(s);
			// read directions list
			directions = new ArrayList<String>();
			list = reader.readLine().split("\\,\\,");
			for (String s : list) directions.add(s);
			
			r = new RecipeADT(data[0], data[1], data[2], data[3], data[4], ingredients, directions);
			recipes.insert(data[0], r);
		}
		reader.close();
	}
	
	public static void main(String[] args) {
		System.out.println("Trying to load file...");
		// try to find file
		try {
			// load in file
			load();
			System.out.println("Loaded successfully.");
		
		} catch (FileNotFoundException a) {
			// can't find file, parse from website
			System.out.println("File not found, downloading data...");
			Parser.parseHTML();
			System.out.println("Downloaded successfully.");
			
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
	}
}
