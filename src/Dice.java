/**
 * Created by zazulam on 2/18/16.
 */
import java.util.Random;
import java.lang.*;

public class Dice implements Comparable<Dice> {
    private static Random rand;
    private int value;
    //0private int random;

    public Dice(){
      this.rand = new Random();  
      this.value = 0;
      value = rand.nextInt(6)+1;
    }


    public int compareTo(Dice other) {
        return Integer.compare(this.value, other.value);
    }

    public int getValue() {
        return value;
    }
}
