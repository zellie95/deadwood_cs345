import java.util.ArrayList;

public abstract class Room {

    private ArrayList<String> adjacentRooms;
    private String title;

    public ArrayList<String> getAdjacentRooms() {
        return adjacentRooms;
    }

    public void setAdjacentRooms(ArrayList<String> adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public Room(String title, ArrayList<String> adjacentRooms) {
      this.adjacentRooms = adjacentRooms;
      this.title = title;
   }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//   public void moveTo(){
//
//   }


}
