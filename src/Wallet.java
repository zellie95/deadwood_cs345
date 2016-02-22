/**
 * Created by zazulam on 2/9/16.
 */
public class Wallet {
    int dollars;
    int credits;

    public Wallet(int dollars, int credits) {
        this.dollars = dollars;
        this.credits = credits;
    }

    public int getDollars() {
        return dollars;
    }

    public int getCredits() {
        return credits;
    }

    public void incDollars(int dollars) {
        this.dollars += dollars;

    }

    public void incCredits(int credit) {
        this.credits += credit;

    }
    public void decDollars(int dollars) {
        this.dollars -= dollars;

    }
    public void decCredits(int credit) {
        this.credits -= credit;
    }

}