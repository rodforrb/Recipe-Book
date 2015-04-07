package recipebook;

import java.util.ArrayList;
import java.util.Iterator;


public class RecipeOutputFormat {
	public static void output(RecipeADT r){
		if (r == null){
			System.out.printf("ERROR: Null Recipe.");
		}
		String format =  "%-50s\n %-50s\n %-50s\n %-50s\n %-50s\n";
		System.out.printf(format,
				" Recipe Name: " + r.getRecipeName() +"\n",
				"Rating: " +Double.toString(r.getrating()) + " / 100.0\n", 
				"Prep Time: " + r.getPrepTime() + " Minutes\n",
				"Total Time: " + r.getTotalTime() + " Minutes\n",
				"Serving Size: " + r.getServingSize());
		System.out.println("\n About The Recipe");
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
		System.out.println("\n\n Ingredients");
		for (String s : r.getIngredients()){
			System.out.println(" >>" + s);
		}
		System.out.println("\n Directions");
		int step = 0;
		for (String s: r.getDirections()){
			step++;
			System.out.println(" " + step+". "+s);
		}
	}
	
	//Call this method in the main method to see how the output looks.
	public static void test_output(){
		RecipeADT test = new RecipeADT();
		test.setRecipeName("Bobsicle");
		test.setRating(100);
		test.setPrepTime("50");
		test.setTotalTime("120");
		test.setServingSize("4-5 People");
		test.setAboutThisRecipe("OMG, This Recipe is super delicious i dont know what i would do without"
				+ " it in my life. I seriously cannot believe that I lived a life where I did not know that this dish really existed."
				+ " Sometimes I think to myself that after eating this dish, I finally know what the meaning of life is. This dish changed my life."
				+ " Now I hope that you will let it change yours.");
		ArrayList<String> ing = new ArrayList<String>();
		ing.add("Baking Powder");
		ing.add("Cheeeeeze");
		ing.add("Marshmellow");
		ing.add("Dynamite");
		ing.add("Dirt");
		ing.add("Motor Oil");
		ing.add("Pizza Sauce");
		ing.add("Holy Water");
		ing.add("Pig Intestine");
		test.setIngredients(ing);
		ArrayList<String> ins = new ArrayList<String>();
		ins.add("Drink Motor Oil");
		ins.add("Marinate Pig Intestine in Holy Water");
		ins.add("Add Baking Powder to the Dirt");
		ins.add("Take Marinated Pig Intesting and mix with Dirt mixture");
		ins.add("Stuff newly created concauction and stuff inside marshmellow");
		ins.add("Lather with cheeze, pizza sauce and dynamite for an explosive result");
		ins.add("Serve Hot");
		test.setDirections(ins);
		RecipeOutputFormat.output(test);
	}
}
