import java.util.ArrayList;

public class BoardController {
	private Collection<SceneCard> cardCollection;
	public static void main(String[] args) {
		/* Bad idea, but leave for now. */
		if(args.length != 2) {
	         System.err.println("USAGE: <rooms.txt> <RoleSceneCards.txt>");
	         System.exit(1);
	    }
		// add second text file to this method when completed.
		parseData(args[0], args[1]);

	}

	private Collection<Role> parseData(String f1, String f2) {
		ArrayList<Role> roles = new ArrayList<Role>();
		ArrayList<Room> room = new ArrayList<Room>();
		Scanner fileS1 = null;
		Scanner fileS2 = null;
		try {
	         fileS1 = new Scanner(new File(f1));
			 fileS2 = new Scanner(new File(f2));
	    }
	    catch(FileNotFoundException e1) {
	         System.err.println("FILE NOT FOUND: "+f1+f2);
	         System.exit(2);
	    }
		/* Parses Scenecards */
		while (fileS1.hasNextLine()) {
			String line = fileS1.nextLine();
			Scanner lineData = new Scanner(line);
			int id = lineData.nextInt();
			int budget = lineData.nextInt();
			while (lineData.hasNext()) {
				int rank = lineData.nextInt();
				roles.add(new StarringRole(rank));
			}
			SceneCard sc1 = new SceneCard(roles, budget, id);
			cardCollection.add(sc1);
		}
		/* Parses Rooms */
		while (fileS2.hasNextLine()) {
			String line = fileS2.nextLine();
			lineData = new Scanner(line);
			String title = lineData.next();
			int shotCounters = lineData.nextInt();
			while (lineData.hasNext()) {
				int rank = lineData.nextInt();
				roles.add(new ExtraRole(rank));
			}
		Rooms
		}

	}

}
