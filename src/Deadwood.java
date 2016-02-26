import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Deadwood {
   private static List<SceneCard> cardCollection;
   private static List<BoardSection> boardSections;
   private static Queue<Actor> playerQ;
   private static Random randomGenerator;
   private static int days = 0;
   private static int moviesLeft = 0;


   /*
   *
   * The game of Deadwood!
   * By Ted Weber, Michael Zazula, and Zellie Macabata
   *
   * Last Edited: 2/22/16
   *
   * CS345 - Western Washington University
   *
   */

   public static void main(String[] args) {
      if (args.length != 3) {
         System.err.println("USAGE: <rooms.txt> <RoleSceneCards.txt> <adjacentRoomsList.txt>");
         System.exit(1);
      }
      ArrayList<SceneCard> cardCollection = parseSceneCards(args[1]);
      setCardCollection(cardCollection);
      ArrayList<BoardSection> boardSections = parseRooms(args[0], args[2]);
      setBoardSections(boardSections);

      UI.start();
      while (getDays() != 0) {
         playerTurn();
         if (moviesLeft == 1) {
            days--;
            refreshBoard();
         }
      }
      endGame();
   }

   public static int getMoviesLeft() {
      return moviesLeft;
   }

   public static void setMoviesLeft(int moviesLeft) {
      Deadwood.moviesLeft = moviesLeft;
   }

   public static void setActorCredits(Actor actor, int credits) {
      actor.getWallet().incCredits(credits);
   }

   public static void setActorDollars(Actor actor, int dollars) {
      actor.getWallet().incDollars(dollars);
   }

   public static Queue<Actor> getPlayerQ() {
      return playerQ;
   }

   public static void setPlayerQ(LinkedList<Actor> playerQ) {
      Deadwood.playerQ = playerQ;
   }

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

   public static void playerTurn() {

      Actor curr = playerQ.remove();
      String position = curr.getBoardPosition();
      Boolean roleStatus = curr.getRoleStatus();
      int caseArg;
      Room currRoom = null;

   	  /* Player X's turn. */
      System.out.println("Player " + curr.getPlayerID() + "'s turn.\n");
      currRoom = getCurrRoom(position);
   
   	  /* Not in a role or not in CastingOffice */
      if ((roleStatus == false) && !position.equals("CastingOffice")) {
         caseArg = 1;
      }

      /* Player is currently in a role */
      else if (roleStatus == true && curr.getRoleStatus() == true) {
         caseArg = 2;
      }

      /* In the casting office currently */
      else {
         caseArg = 3;
      }

      switch (caseArg) {
         /* Not in a role or not in CastingOffice */
         case 1:
            // If already in a Scene Room, ask if they want to take a role.
            boolean turnOver = false;
            boolean b = false;
            if ((currRoom instanceof SceneRoom) && (((SceneRoom) currRoom).getMovieStatus()) == true) {
               String roleChoice = UI.roleCheck();
               ArrayList<Role> starringRoles;
               ArrayList<Role> extraRoles;
               if (roleChoice.equals("y")) {
                  Room r = getCurrRoom(position);
                  SceneRoom sr = (SceneRoom) r;
                  starringRoles = sr.getValidStars(curr);
                  extraRoles = sr.getValidExtras(curr);
                  SceneCard sceneCard = sr.getSceneCard();
                  int budget = sceneCard.getBudget();
                  Role newRole = UI.roleChoose(extraRoles, starringRoles, budget);
                  if (newRole != null) {
                     curr.setRole(newRole);
                     newRole.setOccupied(true);
                     newRole.setOccupant("Player " + curr.getPlayerID());
                     curr.setRoleStatus(true);
                  }
               }
            }

            // Prompt the player to move.
            if (!curr.getRoleStatus()) {
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
                     endPlayerTurn(curr);
                     turnOver = true;
                     b = true;
                  }
               }
            }

            if (!turnOver) {
               // if they move into a scene room after they choose to move, we ask if they want to take a role.
               if ((currRoom instanceof SceneRoom) && (((SceneRoom) currRoom).getMovieStatus()) == true) {

                  // retrieving all the data from the askRole function call. Requires array splitting b/c we return multiple types.
                  String roleChoice = UI.roleCheck();
                  ArrayList<Role> starringRoles;
                  ArrayList<Role> extraRoles;
                  if (roleChoice.equals("y")) {
                     Room r = getCurrRoom(position);
                     SceneRoom sr = (SceneRoom) r;
                     starringRoles = sr.getValidStars(curr);
                     extraRoles = sr.getValidExtras(curr);
                     SceneCard sceneCard = sr.getSceneCard();
                     int budget = sceneCard.getBudget();
                     Role newRole = UI.roleChoose(extraRoles, starringRoles, budget);
                     if (newRole != null) {
                        curr.setRole(newRole);
                        newRole.setOccupied(true);
                        newRole.setOccupant("Player " + curr.getPlayerID());
                        curr.setRoleStatus(true);
                     }
                  }
               }
               // If they move into the casting office after their move.
               if ((currRoom instanceof CastingOffice)) {
                  String upgradeChoice = UI.upgradeCheck();
                  // Check upgrade validity, then upgrade.
                  boolean validUpgrade = false;
                  if (upgradeChoice.equals("y")) {
                     UI.printPlayerStatus(curr);
                     while (!validUpgrade) {
                        CastingOffice castingOffice = (CastingOffice) currRoom;
                        String upgradeRank = UI.upgradeWithDollarsOrCredits();
                        if (upgradeRank.equals("exit")) {
                           break;
                        } else {
                           Actor upgradedPlayer = castingOffice.upgradeCheck(upgradeRank, curr);
                           if (upgradedPlayer != null) {
                              curr = upgradedPlayer;
                              validUpgrade = true;
                           }
                        }
                     }
                  }
                  b = false;
               }
            }
                  // Final check for player's turn.
                  while (!b) {
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
            b = false;
            SceneRoom insideRoom = (SceneRoom) currRoom;
            int budget = insideRoom.getSceneCard().getBudget();

            // Prompt role options.
            String workChoice = UI.workCheck();
            while (!workChoice.equals("a") && !workChoice.equals("b")) {
               if (workChoice.equals("c")) {
                  UI.printPlayerStatus(curr);
                  workChoice = UI.workCheck();
               }
               if (workChoice.equals("d")) {
                  UI.printMovieStatus(insideRoom);
                  workChoice = UI.workCheck();
               }
            }
            // Act calculations
            if (workChoice.equals("a")) {
               Dice roll = new Dice();
               int diceRoll = roll.getValue();
               diceRoll += curr.getShotBonus();
               UI.printDiceRoll(diceRoll);

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
                     curr = closeRoom(insideRoom, curr);
                  }
               } else {
                  if (curr.getRole() instanceof ExtraRole) {
                     curr.getWallet().incDollars(1);
                  }
                  System.out.println("\nFailed roll. Extras get a dollar for showing up.\n");
               }
            }

            // Rehearse
            if (workChoice.equals("b")) {
               curr.setShotBonus(curr.getShotBonus() + 1);
               System.out.println("\nShot Bonus has increased by 1!\n");
            }

            while (!b) {
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

            // Upgrade prompt "Would you like to upgrade?
            String upgradeChoice = UI.upgradeCheck();
            // Check upgrade validity, then upgrade.
            boolean validUpgrade = false;
            if (upgradeChoice.equals("y")) {
               UI.printPlayerStatus(curr);
               while (!validUpgrade) {
                  CastingOffice castingOffice = (CastingOffice) currRoom;
                  String upgradeRank = UI.upgradeWithDollarsOrCredits();
                  if (upgradeRank.equals("exit")) {
                     break;
                  } else {
                     Actor upgradedPlayer = castingOffice.upgradeCheck(upgradeRank, curr);
                     if (upgradedPlayer != null) {
                        curr = upgradedPlayer;
                        validUpgrade = true;
                     }
                  }
               }
//                  HashMap<Integer, Integer[]> hash = ((CastingOffice) castingOffice).getUpgradeHash();

//                  choice_split = upgradeRank.split(" ");
//                  desiredRank = Integer.parseInt(choice_split[0]);
//                  payment = choice_split[1];
//
//
//                  Integer[] dollarArray = hash.get(desiredRank);
//                  Integer[] creditArray = hash.get(desiredRank);
//                  int d = dollarArray[0];
//                  int cr = creditArray[1];
//
//                  if (!(curr.getRank() >= desiredRank)) {
//                     if (payment.equals("dollars") && currDollars >= d) {
//                        curr.getWallet().decDollars(d);
//                        curr.setRank(desiredRank);
//                        System.out.println("Player " + curr.getPlayerID() + " spent $" + d + " and upgraded to " + curr.getRank() + "\n");
//                        validUpgrade = true;
//                     } else if (payment.equals("credits") && currCredits >= cr) {
//                        curr.getWallet().decCredits(cr);
//                        curr.setRank(desiredRank);
//                        System.out.println("Player " + curr.getPlayerID() + " spent " + cr + " credits and upgraded to " + curr.getRank() + "\n");
//                        validUpgrade = true;
//                     } else {
//                        System.out.println("You have insufficient funds for this upgrade.\n");
//                        UI.printPlayerStatus(curr);
//                     }
//                  } else {
//                     System.out.println("You can't downgrade your rank");
//                     UI.printPlayerStatus(curr);
//                  }
            }

                  // Prompt move if they chose to not upgrade.
                  boolean exitBreakLoop = false;
                  while (!exitBreakLoop) {
                     String moveChoice = UI.moveCheck();
                     if (moveChoice.equals("a")) {
                        ArrayList<String> adjRooms = getAdjRooms(position);
                        String choice = UI.moveTo(adjRooms);
                        curr.setBoardPosition(choice);
                        currRoom = getCurrRoom(choice);
                        position = choice;
                        exitBreakLoop = true;
                     } else if (moveChoice.equals("b")) {
                        UI.printPlayerStatus(curr);
                     } else {
                        exitBreakLoop = true;
                        endPlayerTurn(curr);
                     }
                  }

                  // Checking if they want to take a role in scene room after moving.
                  if ((currRoom instanceof SceneRoom) && (((SceneRoom) currRoom).getMovieStatus()) == true) {
                     String roleChoice = UI.roleCheck();
                     ArrayList<Role> starringRoles;
                     ArrayList<Role> extraRoles;
                     if (roleChoice.equals("y")) {
                        Room r = getCurrRoom(position);
                        SceneRoom sr = (SceneRoom) r;
                        starringRoles = sr.getValidStars(curr);
                        extraRoles = sr.getValidExtras(curr);
                        SceneCard sceneCard = sr.getSceneCard();
                        budget = sceneCard.getBudget();
                        Role newRole = UI.roleChoose(extraRoles, starringRoles, budget);
                        if (newRole != null) {
                           curr.setRole(newRole);
                           newRole.setOccupied(true);
                           newRole.setOccupant("Player " + curr.getPlayerID());
                           curr.setRoleStatus(true);
                        }
                     }
                  }
                  boolean br = false;

                  while (!br) {
                     String choice = UI.finalCheck();
                     if (choice.equals("a")) {
                        UI.printPlayerStatus(curr);
                     } else if (choice.equals("b") && curr.getBoardPosition().equals("CastingOffice")) {
                        System.out.println("No movie in the Casting Office!");
                     } else if (choice.equals("b") && !curr.getBoardPosition().equals("CastingOffice")) {
                        UI.printMovieStatus((SceneRoom) currRoom);
                     } else {
                        endPlayerTurn(curr);
                        br = true;
                     }
                  }
            break;
            }
         }


   public static void upgrade() {
   }


   /* This function 'closes the room' by reseting necessary fields, marking the film as over,
   *  and handling the bonus payouts.
   */
   public static Actor closeRoom(SceneRoom currRoom, Actor curr) {
      ArrayList<Actor> insideActors = new ArrayList<>();
      ArrayList<Actor> stars = new ArrayList<>();
      String roomTitle = currRoom.getTitle();
      SceneCard card = currRoom.getSceneCard();
      int budget = card.getBudget();

      // For each actor in this room on a role, add them to insideActors ArrayList and setRoleStatus to false.
      insideActors.add(curr);
      curr.setRoleStatus(false);
      for (Actor a : playerQ) {
         if (a.getBoardPosition().equals(roomTitle) && a.getRoleStatus()) {
            insideActors.add(a);
            a.setRoleStatus(false);
         }
      }

      // Check if any players are on the scene card, ensuring that a bonus is necessary.
      boolean bonusValidation = validBonus(card);
      if (bonusValidation) {
         System.out.println("Film is complete. Here are the results for the bonus payments! Bonuses are\n" +
                              " only awarded to the starring actors.");
         for (Actor act : insideActors) {
            act.setShotBonus(0);
            if (act.getRole() instanceof ExtraRole) {
               int payment = act.getRole().bonusPayOut();
               act.getWallet().incDollars(payment);
               act.setRole(null);
            } else {
               stars.add(act);

               /* This check has been created to make sure that bonus payout is distributed to other roles on the scene
                * card even when no one is occupying them. Otherwise this will allow the only player on the scene card
                * to take all of the bonus earnings, contrary to the rules of the game.*/
               for (StarringRole starRole : card.getStarringRoles()) {
                  if (starRole.getOccupant() == null) {
                     Actor dummyStar = new Actor(0);
                     dummyStar.setRole(starRole);
                     int rank = starRole.getRank();
                     dummyStar.setRank(rank);
                     stars.add(dummyStar);
                  }
               }
            }
         }
         int currEarnings = starBonus(stars, budget, curr);
         curr.getWallet().incDollars(currEarnings);
         for (Actor star: stars) {
            star.setRole(null);
         }
      } else {
         System.out.println("\nFilm is complete. If there were no starring actors, there will be no bonus payments.");
         // resets all actors in roles in the scene room to null.
         for (Actor a : insideActors) {
               a.setRole(null);
               a.setShotBonus(0);
         }
      }
      resetRoles(currRoom);
      moviesLeft--;
      return curr;
   }

   /*
   * This handles sorting and distributing bonus rolls for starring actors.
   * */
   public static int starBonus(ArrayList<Actor> stars, int budget, Actor curr) {
      PriorityQueue<Dice> diceValues = new PriorityQueue<>();
      PriorityQueue<Actor> starQ = new PriorityQueue<>();
      Actor a = null;
      int currEarnings = 0;

      //Throw all stars into a priority queue, sorts based on role rank.
      starQ.addAll(stars);
      for (int i = 0; i < budget; i++) {
         Dice die = new Dice();
         diceValues.add(die);
      }

      while (!diceValues.isEmpty()) {
            // to ensure max value is on top, we have to call add and remove once to re-max-heapify the priority queue.
         Dice d = diceValues.remove();
         diceValues.add(d);
         d = diceValues.remove();
            // Get the int dollar value.
         int dollars = d.getValue();
         if (starQ.isEmpty()) {
            starQ.addAll(stars);
         } else {
               // Force max-heapify.
            a = starQ.remove();
            starQ.add(a);
            a = starQ.remove();
         }

         if (a.getPlayerID() == curr.getPlayerID()) {
            System.out.println("Player " + curr.getPlayerID() + " receives $" +dollars);
            currEarnings += dollars;
         } else {
            for (Actor actor : playerQ) {
               if (a.getPlayerID() == actor.getPlayerID()) {
                  System.out.println("Player " + actor.getPlayerID() + " receives $" + dollars);
                  actor.getWallet().incDollars(dollars);
               }
            }
         }
      }
      return currEarnings;
   }

   public static void resetRoles(SceneRoom currRoom) {
      boolean breakLoop = false;
      while (!breakLoop) {
         for (BoardSection b : boardSections) {
            for (Room r : b.getRoomObjects()) {
               if (r.getTitle().equals(currRoom.getTitle())) {
                  breakLoop = true;
                  SceneRoom sr = (SceneRoom) r;
                  sr.setMovieStatus(false);
                  SceneCard card = sr.getSceneCard();

                  // Adding all roles to arraylist, to then reset their role fields.
                  ArrayList<Role> roles = new ArrayList<Role>();
                  roles.addAll(card.getStarringRoles());
                  roles.addAll(sr.getExtraRoles());

                  for (Role role : roles) {
                     role.setOccupant(null);
                     role.setOccupied(false);
                  }
               }
            }
         }
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
      System.out.println("End of Player " + curr.getPlayerID() + "'s turn.\n\n");
      playerQ.add(curr);
   }

   private static ArrayList<SceneCard> parseSceneCards(String f1) {
      ArrayList<StarringRole> roles = new ArrayList<>();
      ArrayList<SceneCard> sceneCards = new ArrayList<>();
      Scanner fileS = null;
      try {
         fileS = new Scanner(new File(f1));
      } catch (FileNotFoundException e1) {
         System.err.println("FILE NOT FOUND: " + f1);
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
      ArrayList<ExtraRole> roles = new ArrayList<>(); //Role
      ArrayList<Room> rooms = new ArrayList<>(); //Room
      ArrayList<String> adjRooms = new ArrayList<>();

   	/* <rooms.txt> scanner */
      Scanner fileS = null;
      try {
         fileS = new Scanner(new File(f2));
      } catch (FileNotFoundException e1) {
         System.err.println("FILE NOT FOUND: " + f2);
         System.exit(2);
      }
   	/* <adjacentRoomsList.txt> scanner */
      Scanner adjS = null;
      try {
         adjS = new Scanner(new File(f3));
      } catch (FileNotFoundException e1) {
         System.err.println("FILE NOT FOUND: " + f3);
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
            rooms.add(new Trailer(title, adjRooms));
            adjRooms = new ArrayList<>();
         } else if (title.equals("CastingOffice")) {
            while (adjData.hasNext()) {
               String room = adjData.next();
               adjRooms.add(room);
            }
            rooms.add(new CastingOffice(title, adjRooms));
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
      ArrayList<Room> section2 = new ArrayList<>(rooms.subList(3, 6));
      sections.add(new BoardSection(section2, "3"));
      ArrayList<Room> section3 = new ArrayList<>(rooms.subList(6, 9));
      sections.add(new BoardSection(section3, "4"));
      ArrayList<Room> section4 = new ArrayList<>(rooms.subList(9, 12));
      sections.add(new BoardSection(section4, "1"));

      return sections;
   }


   public static void refreshBoard() {
      for (BoardSection b : boardSections) {
         for (Room r : b.getRoomObjects()) {
            if (r instanceof SceneRoom) {
               SceneRoom sr = (SceneRoom) r;
               int savedShots = sr.getSavedShotCounters();
               sr.setShotCounter(savedShots);
               sr.setSceneCard(pullCard());
               sr.setMovieStatus(true);
               setMoviesLeft(10);

            }
         }
      }
      setMoviesLeft(10);
      for (Actor p : playerQ) {
         p.setBoardPosition("Trailers");
         p.setRole(null);
         p.setShotBonus(0);
         p.setRoleStatus(false);

      }
   }


   public static void endGame() {
      int score = 0;
      Actor winner = null;
      for (Actor p : playerQ) {
         int dollars = p.getWallet().getDollars();
         int credits = p.getWallet().getCredits();
         int rankScore = p.getRank() * 5;
         int currScore = dollars + credits + rankScore;
         if (currScore > score) {
            score = currScore;
            winner = p;
         }
      }
      System.out.println(winner.getPlayerID() + " is the winner! With a score of " + score + "!\n" + "Thanks for playing!\n\n Goodbye.");
      System.exit(0);
   }

   private static SceneCard pullCard() {
      randomGenerator = new Random();
      int x = randomGenerator.nextInt(cardCollection.size());
      SceneCard card = cardCollection.get(x);
      cardCollection.remove(x);
      return card;
   }

   public static int getDays() {
      return days;
   }

   public static void setDays(int players) {

      if ((players == 2) || (players == 3)) {
         Deadwood.days = 3;
      } else if (players == 4) {
         Deadwood.days = 4;

      } else if (players == 5) {
         Deadwood.days = 4;
         for (Actor a : playerQ) {
            setActorCredits(a, 2);
         }
      } else if (players == 6) {
         Deadwood.days = 4;
         for (Actor a : playerQ) {
            setActorCredits(a, 4);
         }
      } else {
         Deadwood.days = 4;
         for (Actor a : playerQ) {
            a.setRank(2);
         }
      }
      Deadwood.setMoviesLeft(10);

   }

}
