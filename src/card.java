/**
 * Created by zazulam on 2/9/16.
 */
import java.lang.*;
import java.util.*;
import java.io.*;

public class card {
    int cardNum;
    int budget;
    ArrayList<Role> roles = new ArrayList<Role>();

    public card(int cardNum, int budget, Role roles){
        this.cardnum = cardNum;
        this.budget = budget;
        this.roles = roles;
    }
}