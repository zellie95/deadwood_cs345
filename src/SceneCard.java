import java.util.ArrayList;

public class SceneCard {
	private int budget;
	//public String text;
	private ArrayList<Role> starringRoles;
	private String id;

	public SceneCard(ArrayList<Role> starringRoles, int budget, String id) {
		this.starringRoles = starringRoles;
		this.budget = budget;
		this.id = id;
	}

	//public void flipSceneCard(){

	//}
}
