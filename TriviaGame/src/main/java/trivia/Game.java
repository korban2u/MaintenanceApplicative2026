package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game implements IGame {
   List<Player> players = new ArrayList<>();

   LinkedList<String> popQuestions = new LinkedList<>();
   LinkedList<String> scienceQuestions = new LinkedList<>();
   LinkedList<String> sportsQuestions = new LinkedList<>();
   LinkedList<String> rockQuestions = new LinkedList<>();

   int currentPlayer = 0;

   public Game() {
      for (int i = 0; i < 50; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast("Science Question " + i);
         sportsQuestions.addLast("Sports Question " + i);
         rockQuestions.addLast("Rock Question " + i);
      }
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      if (howManyPlayers() >= 6) {
         System.out.println("Impossible d'ajouter " + playerName + " : la partie est pleine.");
         return false;
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
      System.out.println("The category is " + currentCategory(player));
      askQuestion(player);
   }

   private void askQuestion(Player player) {
      String category = currentCategory(player);
      String question;

      switch (category) {
         case "Pop":
            question = popQuestions.removeFirst();
            popQuestions.addLast(question);
            break;
         case "Science":
            question = scienceQuestions.removeFirst();
            scienceQuestions.addLast(question);
            break;
         case "Sports":
            question = sportsQuestions.removeFirst();
            sportsQuestions.addLast(question);
            break;
         case "Rock":
            question = rockQuestions.removeFirst();
            rockQuestions.addLast(question);
            break;
         default:
            throw new IllegalStateException("Catégorie inconnue : " + category);
      }
      System.out.println(question);
   }

   private String currentCategory(Player player) {
      if (player.getPlace() % 4 == 1) return "Pop";
      if (player.getPlace() % 4 == 2) return "Science";
      if (player.getPlace() % 4 == 3) return "Sports";
      return "Rock";
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
}