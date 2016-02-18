import java.util.ArrayList;

public class SceneRoom extends Room {
	private int shotCounter;
	private ArrayList<Role> extraRoles;
	private boolean movieStatus;
	private SceneCard sceneCard;

	public SceneRoom(String title, int shotCounters, ArrayList<Role> roles, SceneCard card) {
		super(title);
		this.shotCounter = shotCounters;
		this.sceneCard = card;
		this.extraRoles = roles;
	}

	public ArrayList<Role> getExtraRoles() {
		return extraRoles;
	}
}
