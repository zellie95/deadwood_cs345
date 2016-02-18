import java.util.*;

public class UI{
	private PrintStream output;


	//private int numberOfPlayers;

	// public void start(){
	// 	welcomeToDeadwood();
	// 	while 
	// }

	public void askPlayers(){

		message("How many people are playing? Choose 2-8");
		Scanner input = new Scanner(System.in);
		int playersInput = input.nextInt();
		//make player queue
		BoardController.playerQueue(players);
		//make a set days function in boardcontroller class
		Controller.setDays(players);
		int days = Controller.getDays();
		message("There will be ", days, " days that you will be playing.");

	}

	public void chooseAMoveInARole(){

		System.out.println("Choose a move: \n
							1) Rehearse \n
							2) Act \n");

		Scanner inputMove = new Scanner(System.in);
		int choice = inputMove.nextInt();
		// make a playerChoice function (switch cases) in boardcontroller
		BoardController.playerChoice(choice);

	}

	public void chooseAMoveNotInARole(){
		System.out.println("Choose a move: \n
							1) Move to a different scene room \n
							2) Take a starring role \n
							3) Take an extra role")

		Scanner input
	}

	private void welcomeToDeadwood(){
		message("Welcome tp Deadwood, Bitches");
	}

	private void message(String format, Object... args){
		output.println(String.format(format, args));
	}
}