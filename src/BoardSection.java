import java.util.ArrayList;

public class BoardSection {
	private ArrayList<Room> RoomObjects;
	private String roomID;

	public BoardSection(ArrayList<Room> RoomObjects, String roomID) {
		this.RoomObjects = RoomObjects;
		this.roomID = roomID;
	}

	public ArrayList<Room> getRoomObjects() {
		return RoomObjects;
	}

	public void setRoomObjects(ArrayList<Room> roomObjects) {
		RoomObjects = roomObjects;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
}
