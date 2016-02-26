/**
 * Created by zazulam on 2/13/16.
 */

import java.util.*;
import java.lang.Object;

public class CastingOffice extends Room {
    private HashMap<Integer, Integer[]> upgradeHash;


    public CastingOffice(String title, ArrayList<String> adjRooms) {
        super(title, adjRooms);
        initializeHash();
    }

    public HashMap<Integer, Integer[]> getUpgradeHash() {
        return this.upgradeHash;
    }

    public void initializeHash() {
        upgradeHash.put(2, new Integer[]{4, 5});
        upgradeHash.put(3, new Integer[]{10, 10});
        upgradeHash.put(4, new Integer[]{18, 15});
        upgradeHash.put(5, new Integer[]{28, 20});
        upgradeHash.put(6, new Integer[]{40, 25});
    }

    public Actor upgradeCheck(String upgradeRank, Actor curr) {
        int currDollars = curr.getWallet().getDollars();
        int currCredits = curr.getWallet().getCredits();
        String[] choice_split = null;
        int desiredRank = 0;
        String payment = null;

        choice_split = upgradeRank.split(" ");
        desiredRank = Integer.parseInt(choice_split[0]);
        payment = choice_split[1];

        Integer[] dollarArray = this.upgradeHash.get(desiredRank);
        Integer[] creditArray = this.upgradeHash.get(desiredRank);
        int d = dollarArray[0];
        int cr = creditArray[1];

        if (!(curr.getRank() >= desiredRank)) {
            if (payment.equals("dollars") && currDollars >= d) {
                curr.getWallet().decDollars(d);
                curr.setRank(desiredRank);
                System.out.println("Player " + curr.getPlayerID() + " spent $" + d + " and upgraded to " + curr.getRank() + "\n");
            } else if (payment.equals("credits") && currCredits >= cr) {
                curr.getWallet().decCredits(cr);
                curr.setRank(desiredRank);
                System.out.println("Player " + curr.getPlayerID() + " spent " + cr + " credits and upgraded to " + curr.getRank() + "\n");
            } else {
                System.out.println("You have insufficient funds for this upgrade.\n");
                UI.printPlayerStatus(curr);
                curr = null;
            }
        } else {
            System.out.println("You can't downgrade your rank");
            UI.printPlayerStatus(curr);
            curr = null;
        }

        return curr;
    }


}













