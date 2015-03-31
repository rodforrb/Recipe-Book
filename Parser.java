package recipebook;
import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Parser {
	
	public static void parse () {
		String name, description;
		String[] ingredients, directions;
		try {
			Document doc = Jsoup.connect("http://www.food.com/recipeprint.do?rid=485182").get();
			
			name = doc.title().split(" Recipe")[0];
			
			Elements meta = doc.select("meta");
			ingredients = meta.get(2).attr("content").split(" recipe")[0].split("\\,");
			description = meta.get(3).attr("content");
			Elements dirs = doc.getElementsByTag("ol").get(0).getElementsByTag("span");
			directions = new String[dirs.size()];
			for (int i = 0; i < dirs.size(); i++) directions[i] = dirs.get(i).text();
			
			
		} catch (IOException e) {
			//	continue;				for the loop later
		}
	}
	
	public static void main(String[] args) {
		parse();
	}
}
