/*
 * Created by zazulam on 2/13/16.
 */
public class Actor {
    private Wallet wallet = new Wallet();
    private int rank;
    private String playerID;
    private Room boardPosition;
    private boolean roleStatus;
    private int shotBonus;

    public Actor{
        this.wallet = wallet;
        this.rank = rank;
        this.playerID = playerID;
        this.boardPosition = boardPosition;
        this.roleStatus = roleStatus;
        this.shotBonus = shotBonus;
    }

    public int act(){
        int rolledDie = rollDice();
        return rolledDie;
    }

    public int reherse(){
        shotBonus += 1;
        return shotBonus;
    }

    public void movePlayer(Room roomx){
        Room newRoomPosition = setBoardPosition(roomx);
    }

    public void setBoardPosition(Room boardPosition) {
        this.boardPosition = boardPosition;
    }

    public Room getBoardPosition() {
        return boardPosition;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRoleStatus(boolean roleStatus) {
        this.roleStatus = roleStatus;
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
