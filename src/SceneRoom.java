public class SceneRoom extends Room {
	public int shotCounter;
	public ArrayList<Role> extraRoles;
	public boolean movieStatus;

	public SceneRoom(String title, int shotCounters, ArrayList<Role> roles, SceneCard card) {
		super(title);
		this.shotCounters = shotCounters;
		this.sceneCard = card;
		this.extraRoles = roles;
	}
}
