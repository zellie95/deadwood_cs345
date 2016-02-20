import java.util.ArrayList;

public class SceneRoom extends Room {
	private int shotCounter;
	private ArrayList<ExtraRole> extraRoles;
	private boolean movieStatus;
	private SceneCard sceneCard;

	public SceneRoom(String title, int shotCounters, ArrayList<ExtraRole> roles, SceneCard card, ArrayList<String> adjRooms) {
		super(title, adjRooms);
		this.shotCounter = shotCounters;
		this.sceneCard = card;
		this.extraRoles = roles;
		this.movieStatus = true;
	}

	public ArrayList<ExtraRole> getExtraRoles() {
		return extraRoles;
	}

	public boolean getMovieStatus() {
		return movieStatus;
	}

	public void setMovieStatus(boolean movieStatus) {
		this.movieStatus = movieStatus;
	}

	public int getShotCounter() {
		return shotCounter;
	}

	public void setShotCounter(int shotCounter) {
		this.shotCounter = shotCounter;
	}

	public SceneCard getSceneCard() {
		return sceneCard;
	}

	public void setSceneCard(SceneCard sceneCard) {
		this.sceneCard = sceneCard;
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
