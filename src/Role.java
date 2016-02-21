
public abstract class Role {
	private final int rank;
	private final String title;
	private final String catch_phrase;
	private int rehearsal_tokens;
	private boolean occupied;
	private String occupant;

	public Role(int rank, String title, String catch_phrase) {
		this.rank = rank;
		this.rehearsal_tokens = 0;
		this.title = title;
		this.catch_phrase = catch_phrase;
		this.occupied = false;
		this.occupant = null;
	}

	public String toString() {
		String roleString = title+", "+catch_phrase;
		return roleString;
	}

	public int getRank() {
		return rank;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public String getOccupant() {
		return occupant;
	}

	public void setOccupant(String occupant) {
		this.occupant = occupant;
	}
}
