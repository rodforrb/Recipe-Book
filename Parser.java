package recipebook;
import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Parser {
	
	public static void parse () {
		String name, description;
		String[] ingredients;
		try {
			Document doc = Jsoup.connect("http://www.food.com/recipeprint.do?rid=485824").get();
			Elements meta = doc.select("meta");
			ingredients = meta.get(2).attr("content").split(" recipe")[0].split("\\,");
			description = meta.get(3).attr("content");
			System.out.println(description);
			for (String s : ingredients) System.out.println(s);
			
			
		} catch (IOException e) {
			//	break;				for exiting from the loop later
		}
	}
	
	public static void main(String[] args) {
		parse();
	}
}
