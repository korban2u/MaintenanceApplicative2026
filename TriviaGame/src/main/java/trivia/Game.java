package trivia;

import java.util.*;


public class Game implements IGame {
   List<Player> players = new ArrayList<>();

   int currentPlayer = 0;
   private boolean hasStarted = false;

   private final Map<Category, LinkedList<String>> questionsByCategory = new HashMap<>();

   public Game() {
      for (Category category : Category.values()) {
         questionsByCategory.put(category, new LinkedList<>());
      }
      for (int i = 0; i < 50; i++) {
         for (Category category : Category.values()) {
            questionsByCategory.get(category).addLast(category.getLabel() + " Question " + i);
         }
      }
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      if (hasStarted) {
         System.out.println("Cannot add " + playerName + ". The game has already started.");
         return false;
      }

      if (players.size() >= 6) {
         System.out.println("Cannot add " + playerName + ". Maximum 6 players allowed.");
         return false;
      }

      for (Player p : players) {
         if (p.getName().equals(playerName)) {
            System.out.println("Cannot add " + playerName + ". Name already taken.");
            return false;
         }
      }

      players.add(new Player(playerName));

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      if (players.size() < 2) {
         throw new IllegalStateException("The game cannot start with less than 2 players.");
      }

      hasStarted = true;

      Player player = players.get(currentPlayer);
      System.out.println(player.getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (player.isInPenaltyBox()) {
         if (roll % 2 != 0) {
            player.setGettingOutOfPenaltyBox(true);
            System.out.println(player.getName() + " is getting out of the penalty box");
            movePlayerAndAskQuestion(player, roll);
         } else {
            System.out.println(player.getName() + " is not getting out of the penalty box");
            player.setGettingOutOfPenaltyBox(false);
         }
      } else {
         movePlayerAndAskQuestion(player, roll);
      }
   }
   private void movePlayerAndAskQuestion(Player player, int roll) {
      player.setPlace(player.getPlace() + roll);
      if (player.getPlace() > 12) {
         player.setPlace(player.getPlace() - 12);
      }

      System.out.println(player.getName() + "'s new location is " + player.getPlace());
      System.out.println("The category is " + currentCategory(player).getLabel());
      askQuestion(player);
   }

   private void askQuestion(Player player) {
      Category category = currentCategory(player);

      System.out.println(questionsByCategory.get(category).removeFirst());
   }

   Category currentCategory(Player player) {
      Category[] categories = Category.values();

      int index = (player.getPlace() - 1) % categories.length;
      return categories[index];
   }

   public boolean handleCorrectAnswer() {
      Player player = players.get(currentPlayer);

      if (player.isInPenaltyBox() && !player.isGettingOutOfPenaltyBox()) {
         nextPlayer();
         return true;
      }

      System.out.println("Answer was correct!!!!");
      player.addCoin();
      System.out.println(player.getName() + " now has " + player.getPurse() + " Gold Coins.");

      player.setInPenaltyBox(false);

      boolean inProgress = isGameInProgress();
      nextPlayer();

      return inProgress;
   }

   public boolean wrongAnswer() {
      Player player = players.get(currentPlayer);
      System.out.println("Question was incorrectly answered");
      System.out.println(player.getName() + " was sent to the penalty box");
      player.setInPenaltyBox(true);

      nextPlayer();
      return true;
   }

   private void nextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) {
         currentPlayer = 0;
      }
   }

   private boolean isGameInProgress() {
      return players.get(currentPlayer).getPurse() != 6;
   }

   public List<Player> getPlayers() {
      return players;
   }
}