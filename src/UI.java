
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
		boolean b = false;
		String choice = null;
		while (!b) {
			/*Pause for 1 second*/
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("\nWhat would you like to do?\n" +
					"a) Move \n" +
					"b) Check Actor Status\n" +
					"c) End turn\n");
			console = new Scanner(System.in);
			choice = console.next();
			choice.toLowerCase();
			// Checks for valid input.
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
		String choice = null;
		boolean b = false;
		while (!b) {
			System.out.println("Would you like to take a role? Type y or n");
			console = new Scanner(System.in);
			choice = console.next();
			choice.toLowerCase();
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
			System.out.println("What role would you like to take?\n"+
								"(Enter <starring OR extra> <number>: 'starring 1') \n");
			System.out.println("Budget of film = $"+budget+" million.\n");

			/* Traverses extras and starring roles array lists to retrieve possible role options */
         if(!extras.isEmpty()){
            System.out.println("Extra Roles: \n");
   			for (int i=0; i < extras.size(); i++) {
   				System.out.println((i+1)+") "+extras.get(i)+" - Rank = "+ extras.get(i).getRank());
   			}
          }
         else{
	         System.out.println("\nNo extra roles!\n");
         }
         if(!stars.isEmpty()){
   			System.out.println("\nStarring Roles: \n");
   			for (int j=0; j < stars.size(); j++) {
   				System.out.println((j+1)+") "+stars.get(j)+" - Rank = "+ stars.get(j).getRank());
   			}
         }
         else{
         	System.out.println("\nNo starring roles!\n");
         }

		if (stars.isEmpty() && extras.isEmpty()) {
			System.out.println("...Looks like no roles fit your current rank.\n");
		}

		System.out.println("\nType 'exit' to return to menu.\n");

			console = new Scanner(System.in);
			choice = console.nextLine();
			choice.toLowerCase();

			// Checks for valid input.
			if (!choice.equals("extra 1") && (!choice.equals("extra 2") && (!choice.equals("extra 3") && (!choice.equals("extra 4")) && (!choice.equals("starring 1")) &&
					(!choice.equals("starring 2")) && (!choice.equals("starring 3")) && (!choice.equals("exit"))))) {
				System.out.println("\nPlease enter a valid input.\n\n\n");
				console = new Scanner(System.in);
			} else if (choice.equals("exit")) {
				returnRole = null;
				b = true;
			} else {
				choice_split = choice.split(" ");
				numChoice = Integer.parseInt(choice_split[1]);
				if (choice_split[0].equals("extra")) {
					try {
						returnRole = extras.get(numChoice-1);
						b = true;
					} catch (IndexOutOfBoundsException e) {
						System.out.println("\nPlease enter a valid input.\n\n\n");
					}
				} else {
					try {
						returnRole = stars.get(numChoice-1);
						b = true;
					} catch (IndexOutOfBoundsException e) {
						System.out.println("\nPlease enter a valid input.\n\n\n");
					}
				}
			}
		}
		return returnRole;
	}

	public static String moveTo(ArrayList<String> adjRooms) {
		int numChoice = 0;
		System.out.println("Where would you like to move?\n");
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

	public static String workCheck() {
		String choice = null;
		boolean b = false;
		while (!b) {
			System.out.println("What would you like to do on your role?\n" +
					"a) Act \n"+
					"b) Rehearse \n"+
					"c) Check Actor Status \n"+
					"d) Check Movie status");
			console = new Scanner(System.in);
			choice = console.next();
			choice.toLowerCase();
			if(!choice.equals("a") && !choice.equals("b") && !choice.equals("c") && !choice.equals("d")) {
				System.out.println("Please enter 'a', 'b', 'c', or 'd'");
				console = new Scanner(System.in);
			}
			else {
				b = true;
			}
		}
		return choice;
	}

	public static String finalCheck () {
		String choice = null;
		boolean b = false;
		while (!b) {
			System.out.println("What would you like to do?\n" +
					"a) Check Actor Status \n"+
					"b) Check Movie Status \n"+
					"c) End Turn \n");
			console = new Scanner(System.in);
			choice = console.next();
			choice.toLowerCase();
			if(!choice.equals("a") && !choice.equals("b") && !choice.equals("c")){
				System.out.println("Please enter 'a' or 'b' or 'c'");
				console = new Scanner(System.in);
			}
			else {
				b = true;
			}
		}
		return choice;
	}

	public static String upgradeCheck(){
		String choice = null;
		boolean b = false;
		while(!b){
			System.out.println("What would you like to do?\n" +
								"a) Move\n"+
								"b) Upgrade Actor Rank\n"+
								"c) Check Actor Status\n");
			console = new Scanner(System.in);
			choice = console.next();
			choice.toLowerCase();
			if(!choice.equals("a") && !choice.equals("b") && !choice.equals("c")){
				System.out.println("Please enter 'a' or 'b' or 'c'");
				console = new Scanner(System.in);
			}
			else {
				b = true;
			}
		}
		return choice;
	}

	public static String upgradeWithDollarsOrCredits() {
		String choice = null;
		boolean b = false;

		while (!b) {
			System.out.println("Welcome to the Casting Office!\n" +
					"Rank 2: 4 Dollars OR 5 Credits\n" +
					"Rank 3: 10 Dollars OR 10 Credits\n" +
					"Rank 4: 18 Dollars OR 15 Credits\n" +
					"Rank 5: 28 Dollars OR 20 Credits\n" +
					"Rank 6: 40 Dollars OR 25 Credits\n");
			System.out.println("\nEnter which rank you want to upgrade to and how you will pay: \n" +
					"(Enter <desired rank number> <dollars OR credits>: '2 dollars')");

			System.out.println("\nType 'exit' to return to menu.\n");

			console = new Scanner(System.in);
			choice = console.nextLine();
			choice.toLowerCase();
			if (!choice.equals("2 dollars") && !choice.equals("3 dollars") && !choice.equals("4 dollars") && !choice.equals("5 dollars")
					&& !choice.equals("6 dollars") && !choice.equals("2 credits") && !choice.equals("3 credits") && !choice.equals("4 credits")
					&& !choice.equals("5 credits") && !choice.equals("6 credits") && !choice.equals("exit")) {
				System.out.println("Please enter 'dollars' or 'credits' or 'exit'");
				console = new Scanner(System.in);
			} else {
				b = true;
			}
		}
		return choice;
	}


	/*
	* All print status methods
	*
	*
	*
	* */

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
				"Player total dollars: "+ dollars+"\n"+
				"Player total credits: "+ credit+"\n"+
				"Player shot bonus: " + shotbonus+ "\n"+
				"Player role status: "+currRole+"\n");
	}



	public static void printMovieStatus(SceneRoom sr){
		System.out.println(sr.toString());
	}

	public static void printDiceRoll ( int diceRoll){
		System.out.println("You rolled a "+ diceRoll);

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
