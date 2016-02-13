
public abstract class Role {
	private final int rank;
//	private final String title;			/* Implement later*/
//	private final String catch_phrase;
	private int rehearsal_tokens;

	public Role(int rank) {
		this.rank = rank;
		this.rehearsal_tokens = 0;
	}
}
