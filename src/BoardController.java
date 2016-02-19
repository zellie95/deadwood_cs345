import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BoardController {
	private static List<SceneCard> cardCollection;
	private static List<BoardSection> boardSections;
	private static Queue<Actor> playerQ;
	private static Random randomGenerator;
	private static int days = 0;


	public static void main(String[] args) {
		/* Bad idea, but leave for now. */
		if(args.length != 3) {
	         System.err.println("USAGE: <rooms.txt> <RoleSceneCards.txt> <adjacentRoomsList.txt");
	         System.exit(1);
	    }
		ArrayList<SceneCard> cardCollection = parseSceneCards(args[1]);
		setCardCollection(cardCollection);
		ArrayList<BoardSection> boardSections = parseRooms(args[0], args[2]);
		setBoardSections(boardSections);

		UI.start();
		while(getDays() != 0){

		}

	}

	public static int getDays() {
		return days;
	}

	public static void setDays(int days) {

		if ((days == 2) || (days == 3)){
			BoardController.days = 3;
		} else if (days == 4){
			BoardController.days = 4;

		} else if (days == 5){
			BoardController.days = 4;
			for (Actor a: playerQ){
				setActorCredits(a, 2);
			}
		} else if (days == 6){
			BoardController.days = 4;
			for (Actor a: playerQ){
				setActorCredits(a, 4);
			}
		} else {
			BoardController.days = 4;
			for (Actor a: playerQ){
				a.setRank(2);
			}
		}

	}

	public static void setActorCredits(Actor actor, int credits){
		 actor.getWallet().incCredits(credits);
	}

	public static void setActorDollars(Actor actor, int dollars){
		actor.getWallet().incDollars(dollars);
	}

	public static Queue<Actor> getPlayerQ() {
		return playerQ;
	}

	public static void setPlayerQ(LinkedList<Actor> playerQ) { BoardController.playerQ = playerQ; }

	private static void setBoardSections(ArrayList<BoardSection> bs) {
		boardSections = bs;
	}

	public static void setCardCollection(ArrayList<SceneCard> cc) {
		cardCollection = cc;
	}

	public Collection<BoardSection> getBoardSections() {
		return boardSections;
	}

	public Collection<SceneCard> getCardCollection() {
		return cardCollection;
	}

	private static ArrayList<SceneCard> parseSceneCards(String f1) {
		ArrayList<Role> roles = new ArrayList<>();
		ArrayList<SceneCard> sceneCards = new ArrayList<>();
		Scanner fileS = null;
		try {
			fileS = new Scanner(new File(f1));
		}
	    catch(FileNotFoundException e1) {
	         System.err.println("FILE NOT FOUND: "+f1);
	         System.exit(2);
	    }
		/* Parses SceneCards */
		while (fileS.hasNextLine()) {
			String line = fileS.nextLine();
			Scanner lineData = new Scanner(line);
			String id = lineData.next();
			int budget = lineData.nextInt();
			while (lineData.hasNext()) {
				int rank = lineData.nextInt();
				String title = lineData.next();
		/*Splits strings at '+' to ensure they are formatted properly AND all the data is read as one string
		 * by the scanner. */
				if (title.contains("+")) {
					String[] split_title = title.split("\\+");
					StringBuilder builder = new StringBuilder();
					for (String s : split_title) {
						builder.append(s);
						builder.append(" ");
					}
					title = builder.toString();
				}

				String catch_phrase = lineData.next();

				if (catch_phrase.contains("+")) {
					String[] split_catch_phrase = catch_phrase.split("\\+");
					StringBuilder builder = new StringBuilder();
					builder.append('"');
					for (String s : split_catch_phrase) {
						builder.append(s);
						builder.append(" ");
					}
					builder.append('"');
					catch_phrase = builder.toString();
				} else {
					catch_phrase = '"' + catch_phrase + '"';
				}
				roles.add(new StarringRole(rank, title, catch_phrase));
			}
			sceneCards.add(new SceneCard(roles, budget, id));
			roles = new ArrayList<>(); //Role
		}
		return sceneCards;
	}

	private static ArrayList<BoardSection> parseRooms(String f2, String f3) {
		ArrayList<BoardSection> sections = new ArrayList<>(); //BoardSection
		ArrayList<Role> roles = new ArrayList<>(); //Role
		ArrayList<Room> rooms = new ArrayList<>(); //Room
		ArrayList<String> adjRooms = new ArrayList<>();

		/* <rooms.txt> scanner */
		Scanner fileS = null;
		try {
	         fileS = new Scanner(new File(f2));
	    }
	    catch(FileNotFoundException e1) {
	         System.err.println("FILE NOT FOUND: "+f2);
	         System.exit(2);
	    }
		/* <adjacentRoomsList.txt> scanner */
		Scanner adjS = null;
		try {
			adjS = new Scanner(new File(f3));
		}
		catch(FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: "+f3);
			System.exit(2);
		}
		/* Parses Rooms */
		while (fileS.hasNextLine() && adjS.hasNextLine()) {
			String adjLine = adjS.nextLine();
			String roomLine = fileS.nextLine();

			/* Two scanners to read room data and adjacent rooms data concurrently. */
			Scanner roomData = new Scanner(roomLine);
			Scanner adjData = new Scanner(adjLine);
			String adjTitle = adjData.next();
			String title = roomData.next();
			if (title.equals("Trailers")) {
				while (adjData.hasNext()) {
					String room = adjData.next();
					adjRooms.add(room);
				}
				rooms.add(new Trailer(title,adjRooms));
				adjRooms = new ArrayList<>();
			} else if (title.equals("CastingOffice")) {
				while (adjData.hasNext()) {
					String room = adjData.next();
					adjRooms.add(room);
				}
				rooms.add(new CastingOffice(title,adjRooms));
				adjRooms = new ArrayList<>();
			} else {
				int shotCounters = roomData.nextInt();
				while (roomData.hasNext()) {
					int rank = roomData.nextInt();
					String roleTitle = roomData.next();
			/*Splits strings at '+' to ensure they are formatted properly AND all the data is read as one string
			 * by the scanner. */
					if (roleTitle.contains("+")) {
						String[] split_title = roleTitle.split("\\+");
						StringBuilder builder = new StringBuilder();

						for (String s : split_title) {
							builder.append(s);
							builder.append(" ");
						}
						roleTitle = builder.toString();
						String catch_phrase = roomData.next();
						if (catch_phrase.contains("+")) {
							String[] split_phrase = catch_phrase.split("\\+");
							builder = new StringBuilder();

							for (String s : split_phrase) {
								builder.append(s);
								builder.append(" ");
							}
							catch_phrase = builder.toString();
							roles.add(new ExtraRole(rank, roleTitle, catch_phrase));
						}
						while (adjData.hasNext()) {
							String room = adjData.next();
							adjRooms.add(room);
						}
					}
				}
				/* Grab random scene card */
				SceneCard card = pullCard();
				rooms.add(new SceneRoom(title, shotCounters, roles, card, adjRooms));
				roles = new ArrayList<>(); //Role
				adjRooms = new ArrayList<>();
			}
		}
		ArrayList<Room> section1 = new ArrayList<>(rooms.subList(0, 3));
		sections.add(new BoardSection(section1, "2"));
		ArrayList<Room> section2 = new ArrayList<>(rooms.subList(3,6));
		sections.add(new BoardSection(section2, "3"));
		ArrayList<Room> section3 = new ArrayList<>(rooms.subList(6,9));
		sections.add(new BoardSection(section3, "4"));
		ArrayList<Room> section4 = new ArrayList<>(rooms.subList(9,12));
		sections.add(new BoardSection(section4, "1"));

		return sections;
	}

	private static SceneCard pullCard() {
		randomGenerator = new Random();
		int x = randomGenerator.nextInt(cardCollection.size());
		SceneCard card = cardCollection.get(x);
		cardCollection.remove(x);
		return card;
	}

//	private static void userChoosesMove(int moveChoice){
//		switch(moveChoice){
//			case 1:
//				Actor.move(Room room);
//				break;
//
//			case 2: case 3:
//				Actor.setRole();
//
//		}
//	}

}
