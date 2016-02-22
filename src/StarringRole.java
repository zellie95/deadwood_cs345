
public class StarringRole extends Role implements Comparable<StarringRole> {

	public StarringRole(int rank, String title, String catch_phrase) {
		super(rank, title, catch_phrase);
	}

	@Override
	public int[] payout() {
		int[] payout = {0,2};
		return payout;
	}

	@Override
	public int bonusPayOut() {
		return 0;
	}


	// Probably a pointless compareTo. Delete later.
	@Override
	public int compareTo(StarringRole other) {
		return Integer.compare(this.getRank(), other.getRank());
	}
}


