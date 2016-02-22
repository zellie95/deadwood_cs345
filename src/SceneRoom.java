import java.util.ArrayList;

public class SceneRoom extends Room {
	private int shotCounter;
	private ArrayList<ExtraRole> extraRoles;
	private boolean movieStatus;
	private SceneCard sceneCard;
	private int savedShotCounters;

	public SceneRoom(String title, int shotCounters,  ArrayList<ExtraRole> roles, SceneCard card, ArrayList<String> adjRooms) {
		super(title, adjRooms);
		this.shotCounter = shotCounters;
		this.sceneCard = card;
		this.extraRoles = roles;
		this.movieStatus = true;
		this.savedShotCounters = shotCounters;
	}

	public ArrayList<ExtraRole> getExtraRoles() {
		return extraRoles;
	}

	public void setShotCounter(int shotCounter) {
		this.shotCounter = shotCounter;
	}

	public int getSavedShotCounters() {
		return savedShotCounters;
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

	public void decShotCounter() {
		this.shotCounter = shotCounter - 1;
	}

	public SceneCard getSceneCard() {
		return sceneCard;
	}

	public void setSceneCard(SceneCard sceneCard) {
		this.sceneCard = sceneCard;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (ExtraRole e : extraRoles) {
			builder.append(e.getTitle());
			builder.append(" - ");
			if (e.getOccupant() == null) {
				builder.append("Open Role! Must be rank: " +e.getRank());
			} else {
				builder.append(e.getOccupant());
			}
			builder.append(", ");
		}
		String extras = builder.toString();

		builder = new StringBuilder();
		for (StarringRole s : getSceneCard().getStarringRoles()) {
			builder.append(s.getTitle());
			builder.append(" - ");
			if (s.getOccupant() == null) {
				builder.append("Open Role! Must be rank: " + s.getRank());
			} else {
				builder.append(s.getOccupant());
			}
			builder.append(", ");
		}
		String stars = builder.toString();
		String productionStatus = null;
		if (this.movieStatus == true) {
			productionStatus = "Movie is still in production!";
		} else {
			productionStatus = "Movie in no longer in production...";
		}
		return "\nMovie Status:\n" +
				"\n   Production Status: "+productionStatus+
				"\n   Budget: " + getSceneCard().getBudget() +
				"\n   Shot Counters Remaining: " + shotCounter +
				"\n   Extra Roles: " + extras +
				"\n   Starring Roles: " + stars  +
				'\n';
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
