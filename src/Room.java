import java.util.ArrayList;

public abstract class Room {
    private ArrayList<String> adjacentRooms;
    private String title;

    public Room(String title, ArrayList<String> adjacentRooms){
      this.adjacentRooms = adjacentRooms;
      this.title = title;
   }

   public void moveTo(){

   }


}
