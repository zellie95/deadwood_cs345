import java.util.ArrayList;

public class SceneRoom extends Room {
	private int shotCounter;
	private ArrayList<Role> extraRoles;
	private boolean movieStatus;

	public SceneRoom(String title, int shotCounters, ArrayList<Role> roles, SceneCard card) {
		super(title);
		this.shotCounters = shotCounters;
		this.sceneCard = card;
		this.extraRoles = roles;
	}
}
