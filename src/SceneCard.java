import java.util.ArrayList;

public class SceneCard {
	private int budget;
	//public String text;
	private ArrayList<StarringRole> starringRoles;
	private String id;

	public SceneCard(ArrayList<StarringRole> starringRoles, int budget, String id) {
		this.starringRoles = starringRoles;
		this.budget = budget;
		this.id = id;
	}

	public ArrayList<StarringRole> getStarringRoles() {
		return starringRoles;
	}

	public int getBudget() {
		return budget;
	}

	//public void flipSceneCard(){

	//}
}
