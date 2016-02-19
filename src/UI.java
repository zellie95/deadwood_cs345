
import java.util.*;

public class UI {
	private static Scanner console = new Scanner(System.in);

//	public UI(Scanner input) {
//		this.console
//	}
//
	//	private PrintStream output;

	//private int numberOfPlayers;

	 public static void start(){
//		 System.out.println("Welcome to Deadwood!\n" +
//				"Select the number of players (2 - 8)");
		 LinkedList<Actor> playerQ = new LinkedList();
		 int input = 0;
		 boolean b = false;
		 while (!b) {
			 System.out.println("Welcome to Deadwood!\n" +
					 "Select the number of players (2 - 8)");
			 try {
				 input = console.nextInt();
				 if (!((2 <= input) && (8 >= input))) {
					 System.out.println("Please enter an integer within the range 2 - 8");
					 console = new Scanner(System.in);
				 } else {
					 b = true;
				 }
			 } catch (InputMismatchException e) {
				 System.out.println("Please enter an integer within the range 2 - 8");
				 console = new Scanner(System.in);
			 }
		 }

		 for (int i=1;i <= input;i++) {
			playerQ.add(new Actor(i));
		 }
		 System.out.println("Randomizing player order...");
		 Collections.shuffle(playerQ);
		 BoardController.setPlayerQ(playerQ);
		 BoardController.setDays(input);

	 }

//	public void askPlayers(){
//
//		message("How many people are playing? Choose 2-8");
//		Scanner input = new Scanner(System.in);
//		int playersInput = input.nextInt();
//		//make player queue
//		BoardController.playerQueue(players);
//		//make a set days function in boardcontroller class
//		Controller.setDays(players);
//		int days = Controller.getDays();
//		message("There will be ", days, " days that you will be playing.");
//
//
//	}
//
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
//	public static void chooseAMoveNotInARole(){
//		System.out.println("Choose a move: \n
//							1) Move to a different room \n
//							2) Take a starring role \n
//							3) Take an extra role")
//
//		Scanner inputInARole = new Scanner(System.in);
//		int choice = inputInARole.nextInt();
//		BoardController.playerChoice(choice);
//	}
//
//	public static void chooseAMoveInTrailers(){
//		System.out.println("Choose a move: \n
//							1) Move to a different room");
//		Scanner inputInTrailers = new Scanner(System.in);
//		int choice = inputInTrailers.nextInt();
//		BoardController.playerChoice(choice);
//	}
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
