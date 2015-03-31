import java.util.ArrayList;

public class RecipeADT {
	
	private String recipeName;
	private double prepTime;
	private double totalTime;
	private int servingSize;
	private String aboutThisRecipe;
	private ArrayList<String> ingredients = null;
	private ArrayList<String> directions = null;
	
	//Create a blank Recipe Entry
	public RecipeADT(){
		recipeName = "";
		prepTime = 0;
		totalTime = 0;
		servingSize = 0;
		aboutThisRecipe = "";
		ingredients = new ArrayList<String>();
		directions = new ArrayList<String>();
	}
	
	//Create a valid recipe entry
	public RecipeADT(String name, double prepTime, double totalTime, int servingSize, String aboutThisRecipe, ArrayList<String> ingredients, ArrayList<String> directions){
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

	public double getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(double prepTime) {
		this.prepTime = prepTime;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(double totalTime) {
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
