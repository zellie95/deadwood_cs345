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


    public void incDollars(int dollars) {
        dollars += dollars;

    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void incCredits(int credit) {
        credits += credit;

    }

    public void decDollars(int dollars) {
        dollars -= dollars;

    }
    public void decCredits(int credit) {
        credits -= credit;

    }

}