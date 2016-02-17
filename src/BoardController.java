import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

public class BoardController {
	private Collection<SceneCard> cardCollection;
	private Collection<BoardSection> boardSections;
	private static Random randomGenerator;

	public static void main(String[] args) {
//		ArrayList<SceneCard> cardCollection = new ArrayList<SceneCard>();
//		ArrayList<BoardSections> boardSections = new ArrayList<BoardSection>()
		/* Bad idea, but leave for now. */
		if(args.length != 2) {
	         System.err.println("USAGE: <rooms.txt> <RoleSceneCards.txt>");
	         System.exit(1);
	    }

		ArrayList<SceneCard> cardCollection = parseSceneCards(args[0]);
		setCardCollection(cardCollection);
		ArrayList<BoardSection> boardSections = parseRooms(args[1]);
		setBoardSections(boardSections);

	}

	private void setBoardSections(ArrayList<BoardSection> bs) {
		this.boardSections = bs;
	}

	public void setCardCollection(Collection<SceneCard> cardCollection) {
		this.cardCollection = cardCollection;
	}

	public Collection<BoardSection> getBoardSections() {
		return this.boardSections;
	}

	public Collection<SceneCard> getCardCollection() {
		return this.cardCollection;
	}

	private Collection<SceneCard> parseSceneCards(String f1) {
		ArrayList<Role> roles = new ArrayList<>();
		ArrayList<SceneCard> sceneCards = new ArrayList<>();
		// ArrayList<Room> rooms = new ArrayList<Room>();
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
			int id = lineData.nextInt();
			int budget = lineData.nextInt();
			while (lineData.hasNext()) {
				int rank = lineData.nextInt();
				roles.add(new StarringRole(rank));
			}
			// SceneCard sc1 = new SceneCard(roles, budget, id);
			sceneCards.add(new SceneCard(roles, budget, id));
			roles = new ArrayList<>(); //Role
		}
		return sceneCards;
	}

	private static Collection<BoardSection> parseRooms(String f2) {
		ArrayList<BoardSection> sections = new ArrayList<>(); //BoardSection
		ArrayList<Role> roles = new ArrayList<>(); //Role
		ArrayList<Room> rooms = new ArrayList<>(); //Room

		Scanner fileS = null;
		try {
	         fileS = new Scanner(new File(f2));
	    }
	    catch(FileNotFoundException e1) {
	         System.err.println("FILE NOT FOUND: "+f2);
	         System.exit(2);
	    }
		/* Parses Rooms */
		while (fileS.hasNextLine()) {
			String line = fileS.nextLine();
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
		ArrayList<Room> section1 = rooms.subList(0,2);
		sections.add(new BoardSection(section1, "1"));
		ArrayList<Room> section2 = rooms.subList(3,5);
		sections.add(new BoardSection(section2, "2"));
		ArrayList<Room> section3 = rooms.subList(6,8);
		sections.add(new BoardSection(section3, "3"));
		ArrayList<Room> section4 = rooms.subList(9,11);
		sections.add(new BoardSection(section4, "4"));

		return sections;
	}

	private SceneCard pullCard() {
		randomGenerator = new Random();
		int x = randomGenerator.nextInt(cardCollection.size());
		SceneCard card = cardCollection.get(x);
		cardCollection.remove(x);
		return card;
	}

}
