package recipebook;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Parser {
	
	public static void parseHTML () {
		RecipeADT recipe;
		String name, description, prepTime, totalTime, servings;
		ArrayList<String> ingredients;
		ArrayList<String> directions;
		String[] ingredientsArray;
		
		String url;
		for (int i = 10; i < 520000; i++) {
			try {
				url = "http://www.food.com/recipeprint.do?rid="+Integer.toString(i);
				Document doc = Jsoup.connect(url).get();
				
				// pull out name of recipe
				name = doc.title().split(" Recipe")[0];
				
				// find 'meta' tags
				Elements meta = doc.select("meta");
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
				Elements dirs = doc.getElementsByTag("ol").get(0).getElementsByTag("span");
				for (Element d : dirs) directions.add(d.text());
				
				Elements li = doc.getElementsByTag("li");
				prepTime = li.get(0).text().split(": ")[1];
				totalTime = li.get(1).text().split(": ")[1];
				servings = li.get(2).text().split(": ")[1];
				
				recipe = new RecipeADT(name, prepTime, totalTime, servings, description, ingredients, directions);
				Main.recipes.insert(name, recipe);
				
			} catch (Exception e) {} // continue on next loop if a recipe isn't valid
		}
	}
}
