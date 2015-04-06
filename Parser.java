package recipebook;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Parser {
	
	public static void parseHTML () {
		RecipeADT recipe;
		String directory, name, description, prepTime, totalTime, servings;
		ArrayList<String> ingredients;
		ArrayList<String> directions;
		String[] ingredientsArray;
		Document doc;
		Elements meta, dirs, li;
		
		String[] dir = new String[]{"http:", "http: (2)", "http: (3)", "http: (4)", "http: (5)"};
				
		System.out.println("Progress:");
		for (int a = 0; a < 5; a++) {
			System.out.print(20*a);
			for (int b = 1; b <= 1000; b++) {
//			for (int b = 1; b <= 100000; b++) {
				// draw a progress bar
				// save every 5000 recipes
				if (b%5000==0) {
					System.out.print(".");
					try {
						Main.save();
						Main.recipes = new RBT<String, RecipeADT>();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {

					directory = "/home/ben/Desktop/Workspace/HTML/" + dir[a] 
								+ "/www.food.com/recipeprint.do?rid=" 
								+ Integer.toString(a*100000+b);
					
					doc = Jsoup.parse(new File(directory), "UTF-8", "");
					
					// pull out name of recipe
					name = doc.title().split(" Recipe")[0];
					
					// find 'meta' tags
					meta = doc.select("meta");
					// pull out ingredients
					ingredients = new ArrayList<String>();
					ingredientsArray = meta.get(2).attr("content").split(" recipe")[0].split("\\,");
					for (String s : ingredientsArray) ingredients.add(s);
					
					// pull out description text
					description = meta.get(3).attr("content");
					// remove newlines from text
					description = description.replaceAll("\n", "");
					description = description.replaceAll("\r", " ");
					// pull out directions
					directions = new ArrayList<String>();
					dirs = doc.getElementsByTag("ol").get(0).getElementsByTag("span");
					for (Element d : dirs) directions.add(d.text());
					
					li = doc.getElementsByTag("li");
					prepTime = li.get(0).text().split(": ")[1];
					totalTime = li.get(1).text().split(": ")[1];
					servings = li.get(2).text().split(": ")[1];
					
					recipe = new RecipeADT(name, prepTime, totalTime, servings, description, ingredients, directions);
					Main.recipes.insert(name, recipe);
					
				} catch (Exception e) {} // continue on next loop if a recipe isn't valid
			}
		}
	}
}
