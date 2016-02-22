import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BoardController {
   private static List<SceneCard> cardCollection;
   private static List<BoardSection> boardSections;
   private static Queue<Actor> playerQ;
   private static Random randomGenerator;
   private static int days = 0;
   private static int moviesLeft = 0;


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
      while(getDays() != 0) {
         playerTurn();
         if (moviesLeft == 1) {
            days--;
            // C A L L refreshBoard()
         }
      }
   }

   public static void playerTurn() {
      Actor curr = playerQ.remove();
      String position = curr.getBoardPosition();
      Boolean roleStatus = curr.getRoleStatus();
      int caseArg;
      Room currRoom = null;
   
   	// Boolean to break for each loops
      boolean breakLoop = false;
   
   	//Player X's turn.
      System.out.println("Player "+curr.getPlayerID()+"'s turn.\n");
      currRoom = getCurrRoom(position);
   
   	/* Not in a role or not in CastingOffice */
      if ((roleStatus == false) && !position.equals("CastingOffice")) {
         caseArg = 1;
      /* Player is currently in a role */
      } 
      else if (roleStatus == true) {
         caseArg = 2;
      /* In the casting office currently */
      } 
      else {
         caseArg = 3;
      }
   
      switch(caseArg) {
         case 1:
            boolean b = false;
         	// If checks if player is in a scene room and if a movie is still in production.
            if ((currRoom instanceof SceneRoom) && (((SceneRoom) currRoom).getMovieStatus()) == true) {
               ArrayList[] roleArrays = askRole(position, curr);
               if (roleArrays != null) {
				   ArrayList extraRoles = roleArrays[0];
				   ArrayList starringRoles = roleArrays[1];
				   ArrayList budget = roleArrays[2];
				   int realBudget = (int) budget.get(0);
				   Role newRole = UI.roleChoose(extraRoles, starringRoles, realBudget);
                  if (newRole != null) {
                     curr.setRole(newRole);
                     newRole.setOccupied(true);
                     newRole.setOccupant("Player " + curr.getPlayerID());
                     curr.setRoleStatus(true);
                  }
			   }
            }
            /*If they decided to take the role in the previous if clause, then forbid them from moving or taking
             *another role.
             * */
            if (!curr.getRoleStatus()) {
         	/*	User has chosen to not take a role, or they are in a board position that doesn't have any roles.*/
               // Loop to allow player to check status as many times as they want.
               while (!b) {
                  String moveChoice = UI.moveCheck();
                  if (moveChoice.equals("a")) {
                     ArrayList<String> adjRooms = getAdjRooms(position);
                     String choice = UI.moveTo(adjRooms);
                     curr.setBoardPosition(choice);
                     currRoom = getCurrRoom(choice);
                     position = choice;

                     b = true;
                  } else if (moveChoice.equals("b")) {
                     UI.printPlayerStatus(curr);
                  } else {
                     b = true;
                     endPlayerTurn(curr);
                  }
               }
         	/* Checks if player is in a scene room, and asks if they want to take a role. */
               if ((currRoom instanceof SceneRoom) && (((SceneRoom) currRoom).getMovieStatus()) == true) {
                  // retrieving all the data from the askRole function call.
                  ArrayList[] roleArrays = askRole(position, curr);
                  if (roleArrays != null) {
                     ArrayList extraRoles = roleArrays[0];
                     ArrayList starringRoles = roleArrays[1];
                     ArrayList budget = roleArrays[2];
                     int realBudget = (int) budget.get(0);
                     // Ask user what role they would like.
                     Role newRole = UI.roleChoose(extraRoles, starringRoles, realBudget);
                     if (newRole != null) {
                        curr.setRole(newRole);
                        newRole.setOccupied(true);
                        newRole.setOccupant("Player " + curr.getPlayerID());
                        curr.setRoleStatus(true);
                     }
                  }
//               endPlayerTurn(curr);
               }
            }
            b = false;
            while(!b) {
               String choice = UI.finalCheck();
               if (choice.equals("a")) {
                  UI.printPlayerStatus(curr);
               } else if (choice.equals("b")) {
                  try {
                     SceneRoom sceneRoom = (SceneRoom) currRoom;
                     UI.printMovieStatus(sceneRoom);
                  } catch (Exception e) {
                     System.out.println("You are not currently in a Scene Room!");
                  }
               } else {
                  endPlayerTurn(curr);
                  b = true;
               }
            }
            break;
         case 2:
            // Local fields
            b = false;
            SceneRoom insideRoom = (SceneRoom)currRoom;
            int budget = insideRoom.getSceneCard().getBudget();

            String workChoice = UI.workCheck();
            if (workChoice.equals("a")) {
               //Dice roll
               Dice roll = new Dice();
               int diceRoll = roll.getValue();
               diceRoll += curr.getShotBonus();
               UI.printDiceRoll(diceRoll);

               // Checking for payout
               if (diceRoll >= budget) {
                  System.out.println("\nSuccess!\n");
                  int[] payout = curr.getRole().payout();
                  if (curr.getRole() instanceof ExtraRole) {
                     int dollars = payout[0];
                     int credits = payout[1];
                     curr.getWallet().incDollars(dollars);
                     curr.getWallet().incCredits(credits);
                  } else {
                     int credits = payout[1];
                     curr.getWallet().incCredits(credits);
                  }
                  insideRoom.decShotCounter();
                  if (insideRoom.getShotCounter() == 0) {
                     closeRoom(insideRoom);
                  }
               } else {
                  if (curr.getRole() instanceof ExtraRole) {
                     curr.getWallet().incDollars(1);
                  }
                  System.out.println("\nFailed roll. Extras get a dollar for showing up.\n");
               }

            // Actor decides to rehearse
            } else if (workChoice.equals("b")) {
               curr.setShotBonus(1);
            // Check player status
            } else if (workChoice.equals("c")) {
               UI.printPlayerStatus(curr);
            // Check movie status.
            } else {
               UI.printMovieStatus(insideRoom);
            }

            // Final player options.
            while(!b) {
               String choice = UI.finalCheck();
               if (choice.equals("a")) {
                  UI.printPlayerStatus(curr);
               } else if (choice.equals("b")) {
                  UI.printMovieStatus(insideRoom);
               } else {
                  endPlayerTurn(curr);
                  b = true;
               }
            }
            break;
         case 3:
            break;
         }

   }

//   public static void refreshBoard() {
//
//   }

   public static void closeRoom(SceneRoom currRoom) {
      ArrayList<Actor> insideActors = new ArrayList<>();
      ArrayList<Actor> stars = new ArrayList<>();
      String roomTitle = currRoom.getTitle();
      SceneCard card = currRoom.getSceneCard();
      int budget = card.getBudget();

      // Check if any players are on the scene card, ensuring that a bonus is necessary.
      boolean bonusValidation = validBonus(card);
      if (bonusValidation) {
         for (Actor a : playerQ) {
            if (a.getBoardPosition().equals(roomTitle) && a.getRoleStatus()) {
               insideActors.add(a);
            }
         }
         for (Actor act : insideActors) {
            if (act.getRole() instanceof ExtraRole) {
               int payment = act.getRole().bonusPayOut();
               act.getWallet().incDollars(payment);
            } else {
               stars.add(act);
            }
         }
         starBonus(stars, budget);
      } else {
         System.out.println("No bonus payout.");
      }
      moviesLeft--;
   }

   public static void starBonus(ArrayList<Actor> stars, int budget) {
      Queue<Integer> diceValues = new PriorityQueue<Integer>();
      Queue<Actor> starQ;
      Actor a;
      /* Sort the stars ArrayList and then cast it to a Queue, b/c we want the functionality of a queue when
       * |diceValues| > |stars|
      */
      Collections.sort(stars);
      starQ = (Queue<Actor>) stars;

      for (int i=0;i<budget;i++) {
         Dice die = new Dice();
         int value = die.getValue();
         diceValues.add(value);
      }

      while (!diceValues.isEmpty()) {
         int dollars = diceValues.remove();
         a = starQ.remove();
         for (Actor actor : playerQ) {
            if (a.getPlayerID() == actor.getPlayerID()) {
               a.getWallet().incDollars(dollars);
            }
         }
         starQ.add(a);
      }
   }

   public static boolean validBonus(SceneCard card) {
      boolean b = false;

      for (StarringRole s : card.getStarringRoles()) {
         if (s.isOccupied() == true) {
            b = true;
            break;
         }
      }
      return b;
   }

   public static Room getCurrRoom(String position) {
      Room currRoom = null;
      boolean breakLoop = false;
      for (BoardSection b : boardSections) {
         for (Room r : b.getRoomObjects()) {
            if ((r.getTitle()).equals(position)) {
               currRoom = r;
               breakLoop = true;
               break;
            }
         }
         if (breakLoop) {
            break;
         }
      }
      breakLoop = false;
      return currRoom;
   }

   public static ArrayList<String> getAdjRooms(String position) {
      ArrayList<String> adjRooms = new ArrayList<>();
      boolean breakLoop = false;
      for (BoardSection bs : boardSections) {
         for (Room r : bs.getRoomObjects()) {
            if ((r.getTitle()).equals(position)) {
               adjRooms = r.getAdjacentRooms();
               breakLoop = true;
               break;
            }
         }
         if (breakLoop) {
            break;
         }
      }
      return adjRooms;
   }

   public static void endPlayerTurn(Actor curr) {
      System.out.println("End of Player "+curr.getPlayerID()+"'s turn.\n\n");
      playerQ.add(curr);
   }

   public static int getDays() {
      return days;
   }

   public static void setDays(int days) {
   
      if ((days == 2) || (days == 3)){
         BoardController.days = 3;
      } 
      else if (days == 4){
         BoardController.days = 4;
      
      } 
      else if (days == 5){
         BoardController.days = 4;
         for (Actor a: playerQ){
            setActorCredits(a, 2);
         }
      } 
      else if (days == 6){
         BoardController.days = 4;
         for (Actor a: playerQ){
            setActorCredits(a, 4);
         }
      } 
      else {
         BoardController.days = 4;
         for (Actor a: playerQ){
            a.setRank(2);
         }
      }
      BoardController.setMoviesLeft(10);
   
   }

   public static ArrayList[] askRole(String position, Actor curr) {
      ArrayList[] roles = null;
	   boolean breakLoop = false;
      String roleChoice = UI.roleCheck();
      ArrayList<Role> starringRoles = new ArrayList<>();
      ArrayList<Role> extraRoles = new ArrayList<>();
      int budget = 0;
   
      if (roleChoice.equals("y")) {
         Room r = getCurrRoom(position);
         SceneRoom sr = (SceneRoom) r;
         for (ExtraRole e : sr.getExtraRoles()) {
            if (e.getRank() <= curr.getRank() && !e.isOccupied()) {
               extraRoles.add(e);
            }
         }
         SceneCard sceneCard = sr.getSceneCard();
         budget = sceneCard.getBudget();
         for (StarringRole s : sceneCard.getStarringRoles()) {
            if (s.getRank() <= curr.getRank() && !s.isOccupied()) {
               starringRoles.add(s);
            }
         }
		  // Work around for returning multiple different types (Java needs structs).
		  ArrayList<Integer> tempBudget = new ArrayList<>();
		  tempBudget.add(budget);
		  roles = new ArrayList[] {extraRoles, starringRoles, tempBudget};
	  }
	   return roles;
   }


   private static ArrayList<SceneCard> parseSceneCards(String f1) {
      ArrayList<StarringRole> roles = new ArrayList<>();
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
            } 
            else {
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
      ArrayList<ExtraRole> roles = new ArrayList<>(); //Role
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
         } 
         else if (title.equals("CastingOffice")) {
            while (adjData.hasNext()) {
               String room = adjData.next();
               adjRooms.add(room);
            }
            rooms.add(new CastingOffice(title,adjRooms));
            adjRooms = new ArrayList<>();
         } 
         else {
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

	public static int getMoviesLeft() {
		return moviesLeft;
	}

	public static void setMoviesLeft(int moviesLeft) {
		BoardController.moviesLeft = moviesLeft;
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

   private static SceneCard pullCard() {
      randomGenerator = new Random();
      int x = randomGenerator.nextInt(cardCollection.size());
      SceneCard card = cardCollection.get(x);
      cardCollection.remove(x);
      return card;
   }

	/* L O G I C    G R A V E Y A R D*/


	//		/*Pause for 1 second*/
	//		try {
	//			Thread.sleep(200);
	//		} catch (InterruptedException e) {
	//			e.printStackTrace();
	//		}



	/* and if there are roles available to actor's rank /*
      /*			for (BoardSection b : boardSections) {
      		for (Room r : b.getRoomObjects()) {
      			if ((r.getTitle()).equals(position)) {*/

//				Old get rooms implementation
	//						/*for (BoardSection bs : boardSections) {
	//							for (Room r : bs.getRoomObjects()) {
	//								if ((r.getTitle()).equals(position)) {
	//									ArrayList<String> adjRooms = r.getAdjacentRooms();*/
	//									String choice = UI.moveTo(adjRooms);
	//									curr.setBoardPosition(choice);
	//									breakLoop = true;
	//									break;
	//								}
	//							}
	//							if (breakLoop = true) {
	//								break;
	//							}
	//						breakLoop = false;


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


	//		/*Pause for 1 second*/
	//		try {
	//			Thread.sleep(200);
	//		} catch (InterruptedException e) {
	//			e.printStackTrace();
	//		}

}
