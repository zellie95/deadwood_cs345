import java.util.ArrayList;

public class SceneRoom extends Room {
	private int shotCounter;
	private ArrayList<Role> extraRoles;
	private boolean movieStatus;
	private SceneCard sceneCard;

	public SceneRoom(String title, int shotCounters, ArrayList<Role> roles, SceneCard card, ArrayList<String> adjRooms) {
		super(title, adjRooms);
		this.shotCounter = shotCounters;
		this.sceneCard = card;
		this.extraRoles = roles;
	}

	public ArrayList<Role> getExtraRoles() {
		return extraRoles;
	}

//	public int removeShotCounters(){
//		if (shotCounters > 0){
//			shotCounters = shotCounters - 1;
//		}
//		else{
//
//		}
//
//	}
}
