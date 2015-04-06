package recipebook;

import java.util.ArrayList;

public class RecipeADT {
	
	private String recipeName;
	private String prepTime;
	private String totalTime;
	private String servings;
	private String aboutThisRecipe;
	private ArrayList<String> ingredients = null;
	private ArrayList<String> directions = null;
	
	//Create a blank Recipe Entry
	public RecipeADT(){
		recipeName = "";
		prepTime = "";
		totalTime = "";
		aboutThisRecipe = "";
		ingredients = new ArrayList<String>();
		directions = new ArrayList<String>();
	}
	
	//Create a valid recipe entry
	public RecipeADT(String name, String prepTime, String totalTime, String servings, String aboutThisRecipe, ArrayList<String> ingredients, ArrayList<String> directions){
		this.recipeName = name;
		this.prepTime = prepTime;
		this.totalTime = totalTime;
		this.servings = servings;
		this.aboutThisRecipe = aboutThisRecipe;
		this.ingredients = ingredients;
		this.directions = directions;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(String prepTime) {
		this.prepTime = prepTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getServingSize() {
		return servings;
	}

	public void setServingSize(String servings) {
		this.servings = servings;
	}

	public String getAboutThisRecipe() {
		return aboutThisRecipe;
	}

	public void setAboutThisRecipe(String aboutThisRecipe) {
		this.aboutThisRecipe = aboutThisRecipe;
	}

	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}

	public ArrayList<String> getDirections() {
		return directions;
	}
	
	public void setDirections(ArrayList<String> directions) {
		this.directions = directions;
	}
	
	public String data() {
		String s = "";
		s += this.recipeName + "\n";
		s += this.prepTime + "\n";
		s += this.totalTime + "\n";
		s += this.servings + "\n";
		s += this.aboutThisRecipe + "\n";
		for (String str : ingredients) s += str + ",,";
		s += "\n";
		for (String dir : directions) s += dir + ",,";
		s += "\n";
		return s;
	}
	
	public String toString() {
		String s = "";
		s += this.recipeName + "\n";
		s += this.prepTime + "\n";
		s += this.totalTime + "\n";
		s += this.servings + "\n";
		s += this.aboutThisRecipe + "\n";
		s += "Ingredients:\n";
		for (String str : ingredients) s += str + "\n";
		s += "\nDirections:\n";
		for (String dir : directions) s += dir + "\n";
		return s;
	}

}
