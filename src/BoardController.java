import java.util.ArrayList;
import java.util.Random;

public class BoardController {
	private Collection<SceneCard> cardCollection;
	private Collection<BoardSection> boardSections;
	private static Random randomGenerator;

	public static void main(String[] args) {
		ArrayList<SceneCard> cardCollection = new ArrayList<SceneCard>();
		/* Bad idea, but leave for now. */
		if(args.length != 2) {
	         System.err.println("USAGE: <rooms.txt> <RoleSceneCards.txt>");
	         System.exit(1);
	    }
		// add second text file to this method when completed.
		// parseData(args[0], args[1]);
		this.cardCollection = parseSceneCards(args[0]);
		parseRooms(args[1]);

	}

	private Collection<SceneCard> parseData(String f1) {
		ArrayList<Role> roles = new ArrayList<Role>();
		ArrayList<SceneCard> sceneCards = new ArrayList<SceneCard>();
		// ArrayList<Room> rooms = new ArrayList<Room>();
		Scanner fileS = null;
		// Scanner fileS2 = null;
		try {
	         fileS = new Scanner(new File(f1));
			//  fileS2 = new Scanner(new File(f2));
	    }
	    catch(FileNotFoundException e1) {
	         System.err.println("FILE NOT FOUND: "+f1);
	         System.exit(2);
	    }
		/* Parses Scenecards */
		while (fileS.hasNextLine()) {
			String line = fileS.nextLine();
			Scanner lineData = new Scanner(line);
			int id = lineData.nextInt();
			int budget = lineData.nextInt();
			while (lineData.hasNext()) {
				int rank = lineData.nextInt();
				roles.add(new StarringRole(rank));
			}
			// SceneCard sc1 = new SceneCard(roles, budget, id);
			sceneCards.add(new SceneCard(roles, budget, id));
			roles = new ArrayList<Role>();
		}
	}

	private Collection<BoardSection> parseRooms(String f2) {
		ArrayList<BoardSection> = new ArrayList<BoardSection>();
		ArrayList<Role> roles = new ArrayList<Role>();
		ArrayList<Room> rooms = new ArrayList<Room>();

		Scanner fileS = null;
		try {
	         fileS = new Scanner(new File(f2));
	    }
	    catch(FileNotFoundException e1) {
	         System.err.println("FILE NOT FOUND: "+f2);
	         System.exit(2);
	    }
		/* Parses Rooms */
		while (fileS2.hasNextLine()) {
			String line = fileS2.nextLine();
			lineData = new Scanner(line);
			String title = lineData.next();
			if (title.equals("Trailers")) {
				// Room trailer = new TrailerRoom(title);
				rooms.add(new TrailerRoom(title));
			} else if (title.equals("CastingOffice")) {
				// Room castingOffice = new CastingOffice(title);
				rooms.add(new CastingOffice(title));
			} else {
				int shotCounters = lineData.nextInt();
				while (lineData.hasNext()) {
					int rank = lineData.nextInt();
					roles.add(new ExtraRole(rank));
				}
				/* Grab random scene card */
				Scenecard card = pullCard();
				rooms.add(new SceneRoom(title, shotCounters, roles, card));
			}

		}

	}

	private SceneCard pullCard() {
		randomGenerator = new Random();
		int x = randomGenerator.nextInt(cardCollection.size());
		SceneCard card = cardCollection.get(x);
		cardCollection.remove(x);
		return card;
	}

}
