/**
 * Created by zazulam on 2/13/16.
 */

import java.util.*;
import java.lang.Object;

public class CastingOffice extends Room {
    public HashMap <Integer, Integer[]> upgradeHash = new HashMap<Integer, Integer[]>();{
        upgradeHash.put(2,new Integer[] {4,5});
        upgradeHash.put(3,new Integer[]{10,10});
        upgradeHash.put(4,new Integer[] {18, 15});
        upgradeHash.put(5,new Integer[]{28, 20});
        upgradeHash.put(6,new Integer[]{40,25});
    }



    public CastingOffice(){
        private int checkRank(Actor playerx){

            return getRank(playerx);
        }



        private boolean checkWallet(Actor playerx, Wallet wallet) {


            return true; // placeholder
        }

        private void upgrade(Actor playerx, int rank){
            playerx.setRank(rank);

        }
}











