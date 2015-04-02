package recipebook;

import java.util.ArrayList;

public class RecipeADT {
	
	private String recipeName;
	private String prepTime;
	private String totalTime;
	private int servingSize;
	private String aboutThisRecipe;
	private ArrayList<String> ingredients = null;
	private ArrayList<String> directions = null;
	
	//Create a blank Recipe Entry
	public RecipeADT(){
		recipeName = "";
		prepTime = "";
		totalTime = "";
		servingSize = 0;
		aboutThisRecipe = "";
		ingredients = new ArrayList<String>();
		directions = new ArrayList<String>();
	}
	
	//Create a valid recipe entry
	public RecipeADT(String name, String prepTime, String totalTime, int servingSize, String aboutThisRecipe, ArrayList<String> ingredients, ArrayList<String> directions){
		this.recipeName = name;
		this.prepTime = prepTime;
		this.totalTime = totalTime;
		this.servingSize = servingSize;
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

	public int getServingSize() {
		return servingSize;
	}

	public void setServingSize(int servingSize) {
		this.servingSize = servingSize;
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
	

}
