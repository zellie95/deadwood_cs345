public class SceneCard {
	private int budget;
	//public String text;
	private ArrayList<Role> starringRoles;
	private String id;

	public SceneCard(ArrayList<Role> starringRoles, int budget, int id) {
		this.starringRoles = starringRoles;
		this.budget = budget;
		this.id = String.valueOf(id);
	}

	//public void flipSceneCard(){

	//}
}
