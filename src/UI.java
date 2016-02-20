
import java.util.*;

public class UI {
	private static Scanner console = new Scanner(System.in);

	 public static void start() {
		 LinkedList<Actor> playerQ = new LinkedList();
		 int input = 0;
		 boolean b = false;
		 while (!b) {
			 System.out.println("Welcome to Deadwood!\n" +
					 "Select the number of players (2 - 8)\n");
			 try {
				 input = console.nextInt();
				 if (!((2 <= input) && (8 >= input))) {
					 System.out.println("Please enter an integer within the range 2 - 8\n");
					 console = new Scanner(System.in);
				 } else {
					 b = true;
				 }
			 } catch (InputMismatchException e) {
				 System.out.println("Please enter an integer within the range 2 - 8\n");
				 console = new Scanner(System.in);
			 }
		 }

		 for (int i=1;i <= input;i++) {
			playerQ.add(new Actor(i));
		 }
		 System.out.println("Randomizing player order...\n");
		 Collections.shuffle(playerQ);
		 BoardController.setPlayerQ(playerQ);
		 BoardController.setDays(input);

	 }

	// Checks if a player wants to move.
	public static String moveCheck() {
		System.out.println("a) Move \n" +
				"b) Check Status\n" +
				"c) End turn\n");
		console = new Scanner(System.in);
		String choice = console.next();
		choice.toLowerCase();
		boolean b = false;
		// Checks for valid input.
		while (!b) {
			if (!(choice.equals("a")) && (!(choice.equals("b"))) && (!(choice.equals("c")))) {
				System.out.println("Please enter 'a' or 'b' or 'c'\n");
				console = new Scanner(System.in);
			} else {
				b = true;
			}
		}
		return choice;
	}

	public static String roleCheck() {
		System.out.println("Would you like to take a role? Type y or n");
		console = new Scanner(System.in);
		String choice = console.next();
		choice.toLowerCase();
		boolean b = false;

		while (!b){
			if(!choice.equals("y") && !choice.equals("n")){
				System.out.println("Please enter 'y' or 'n'");
				console = new Scanner(System.in);
			}
			else {
				b = true;
			}
		}
		return choice;
	}

	public static Role roleChoose(ArrayList<ExtraRole> extras, ArrayList<StarringRole> stars, int budget) {
		int numChoice = 0;
		Role returnRole = null;
		boolean b = false;
		String choice = null;
		String[] choice_split = null;
		while (!b) {
			System.out.println("What role would you like to take? (Enter starring <number> or extra <number>): \n");
			System.out.println("Budget of film = $"+budget+" million.");
			System.out.println("Extra Roles: \n");
			for (int i=0; i < extras.size(); i++) {
				System.out.println((i+1)+") "+extras.get(i));
			}

			System.out.println("Starring Roles: \n");
			for (int j=0; j < stars.size(); j++) {
				System.out.println((j+1)+") "+extras.get(j));
			}
			console = new Scanner(System.in);
			choice = console.next();
			choice.toLowerCase();

			// Checks for valid input.
			if (!choice.equals("extra 1") && (!choice.equals("extra 2") && (!choice.equals("extra 3") && (!choice.equals("extra 4")) && (!choice.equals("starring 1")) &&
					(!choice.equals("starring 2")) && (!choice.equals("starring 3"))))) {
				System.out.println("Please enter a valid input.\n");
				console = new Scanner(System.in);
			} else {
				b = true;
				choice_split = choice.split(" ");
				numChoice = Integer.parseInt(choice_split[1]);
			}
			/*
			*
			* TEST and add try-catch to check proper input bounds.
			*
			* */
			if (choice_split[0].equals("extra")) {
				 returnRole = extras.get(numChoice-1);
			} else {
				returnRole = stars.get(numChoice-1);
			}
		}
		return returnRole;
	}
//	public void chooseAMoveInARole(){
//
//		System.out.println("Choose a move: \n
//							1) Rehearse \n
//							2) Act \n");
//
//		Scanner inputMove = new Scanner(System.in);
//		int choice = inputMove.nextInt();
//		// make a playerChoice function (switch cases) in boardcontroller
//		BoardController.playerChoice(choice);
//
//	}
//

	public static String moveTo(ArrayList<String> adjRooms) {
		int numChoice = 0;
		System.out.println("Where would you like to move?: \n");
		for (int i=0; i < adjRooms.size(); i++) {
			System.out.println((i+1)+") "+adjRooms.get(i));
		}
		console = new Scanner(System.in);
		String choice = console.next();
		choice.toLowerCase();
		boolean b = false;
		// Checks for valid input.
		while (!b) {
			if (!choice.equals("1") && (!choice.equals("2") && (!choice.equals("3") && (!choice.equals("4"))))) {
				System.out.println("Please enter a valid input.\n");
				console = new Scanner(System.in);
			} else {
				b = true;
				numChoice = Integer.parseInt(choice);
			}
		}
		return adjRooms.get(numChoice-1);
	}

	public static void printPlayerStatus(Actor a) {
		int playerID = a.getPlayerID();
		int rank = a.getRank();
		String position = a.getBoardPosition();
		boolean roleStatus = a.getRoleStatus();
		int dollars = a.getWallet().getDollars();
		int credit = a.getWallet().getCredits();
		int shotbonus = a.getShotBonus();
		String currRole = null;
		if(roleStatus){
			currRole = a.getRole().toString();
		} else{
			currRole = "No current role.";
		}
		System.out.println("Player " + playerID+":\n\n" +
				"Player rank: " + rank+"\n"+
				"Player position: "+ position+"\n"+
				"Player role status: "+ roleStatus+ "\n"+
				"Player total dollars: "+ dollars+"\n"+
				"Player total credits: "+ credit+"\n"+
				"Player shot bonus: " + shotbonus+ "\n"+
				"Player role status: "+currRole+"\n");
		}


//
//	public static void chooseAMoveInCastingOffice(){
//		System.out.println("Choose a move: \n
//							1) Move to a different room \n
//							2) Upgrade rank");
//		Scanner inputInCastingOffice = new Scanner(System.in);
//		int choice = inputInCastingOffice.nextInt();
//		BoardController.playerChoice(choice);
//	}

//	private void welcomeToDeadwood(){
//		message("Welcome tp Deadwood, Bitches");
//	}

//	private void message(String format, Object... args){
//		output.println(String.format(format, args));
//	}
}
