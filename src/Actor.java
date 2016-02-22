/*
 * Created by zazulam on 2/13/16.
 */
public class Actor implements Comparable<Actor>{


    private Wallet wallet;
    private int rank;
    private int playerID;
    private String boardPosition;
    private boolean roleStatus;
    private int shotBonus;
    private Role role;

    public Actor(int playerID){
        this.wallet = new Wallet(0, 0);
        this.rank = 1;
        this.playerID = playerID;
        this.boardPosition = "Trailers";
        this.roleStatus = false;
        this.shotBonus = 0;
        this.role = null;

    }

    @Override
    public int compareTo(Actor other) {
        return Integer.compare(this.getRole().getRank(), other.getRole().getRank());
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
    public int reherse(){
        shotBonus += 1;
        return shotBonus;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void movePlayer(String roomx){
        boardPosition = setBoardPosition(roomx);
    }

    public String setBoardPosition(String boardPosition) {
        return this.boardPosition = boardPosition;
    }

    public String getBoardPosition() {
        return boardPosition;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRoleStatus(boolean status) {
        this.roleStatus = status;
    }
    public boolean getRoleStatus(){
        return roleStatus;
    }

    public int getShotBonus() {
        return shotBonus;
    }

    public void setShotBonus(int shotBonus) {
        this.shotBonus = shotBonus;
    }


}
