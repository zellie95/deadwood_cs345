
public abstract class Role {
	private final int rank;
	private final String title;			/* Implement later*/
	private final String catch_phrase;
	private int rehearsal_tokens;
	private boolean occupied;

	public Role(int rank, String title, String catch_phrase) {
		this.rank = rank;
		this.rehearsal_tokens = 0;
		this.title = title;
		this.catch_phrase = catch_phrase;
		this.occupied = false;
	}

	public String toString() {
		String roleString = title+", "+catch_phrase;
		return roleString;
	}

	public int getRank() {
		return rank;
	}
}
