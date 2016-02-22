
public class ExtraRole extends Role{
	public ExtraRole(int rank, String title, String catch_phrase) {
		super(rank, title, catch_phrase);
	}

	@Override
	public int[] payout() {
		int[] payout = {1,1};
		return payout;
	}

	@Override
	public int bonusPayOut() {
		int payment = getRank();
		return payment;
	}
}
