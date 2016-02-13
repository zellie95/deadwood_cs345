/**
 * Created by zazulam on 2/9/16.
 */
public class wallet {
    int dollars;
    int credits;

    private wallet(int dollars, int credits) {
        this.dollars = dollars;
        this.credits = credits;
    }


    private void incWalletD(int dollar) {
        dollars += dollar;

    }
    private void incWalletC(int credit) {
        credits += credit;

    }

    private void decWalletD(int credit) {
        credits -= credit;

    }
    private void decWalletC(int credit) {
        credits -= credit;

    }
}