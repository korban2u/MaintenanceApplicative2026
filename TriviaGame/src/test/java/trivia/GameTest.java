
package trivia;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest {
	@Test
	public void caracterizationTest() {
		// runs 10.000 "random" games to see the output of old and new code mathces
		for (int seed = 1; seed < 10_000; seed++) {
			testSeed(seed, false);
		}
	}

	private void testSeed(int seed, boolean printExpected) {
		String expectedOutput = extractOutput(new Random(seed), new GameOld());
		if (printExpected) {
			System.out.println(expectedOutput);
		}
		String actualOutput = extractOutput(new Random(seed), new Game());
		assertEquals(expectedOutput, actualOutput);
	}

	@Test
	@Disabled("enable back and set a particular seed to see the output")
	public void oneSeed() {
		testSeed(1, true);
	}

	private String extractOutput(Random rand, IGame aGame) {
		PrintStream old = System.out;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (PrintStream inmemory = new PrintStream(baos)) {
			// WARNING: System.out.println() doesn't work in this try {} as the sysout is captured and recorded in memory.
			System.setOut(inmemory);

			aGame.add("Chet");
			aGame.add("Pat");
			aGame.add("Sue");

			boolean notAWinner = false;
			do {
				aGame.roll(rand.nextInt(5) + 1);

				if (rand.nextInt(9) == 7) {
					notAWinner = aGame.wrongAnswer();
				} else {
					notAWinner = aGame.handleCorrectAnswer();
				}

			} while (notAWinner);
		} finally {
			System.setOut(old);
		}

		return baos.toString();
	}

	@Test
	public void playable_at_least_two_players() {
		Game game = new Game();

		assertFalse(game.isPlayable());

		game.add("Alice");
		assertFalse(game.isPlayable());

		game.add("Bob");
		assertTrue(game.isPlayable());
	}

	@Test
	public void adding_player_sets_correct_initial_state() {
		Game game = new Game();
		game.add("Cédric");

		assertEquals(1, game.getPlayers().size());

		Player player = game.getPlayers().get(0);
		assertEquals("Cédric", player.getName());
		assertEquals(1, player.getPlace(), "Un joueur devrait commencer sur la case 1");
		assertEquals(0, player.getPurse(), "Un joueur devrait commencer avec 0 pièce");
		assertFalse(player.isInPenaltyBox(), "Un joueur ne devrait pas commencer en prison");
	}

	@Test
	public void rolling_moves_player_forward() {
		Game game = new Game();
		game.add("David");

		game.roll(3);

		Player player = game.getPlayers().get(0);
		assertEquals(4, player.getPlace(), "Le joueur devrait être sur la case 4 (1 + 3)");
	}

	@Test
	public void rolling_past_12_wraps_around() {
		Game game = new Game();
		game.add("Emma");

		Player player = game.getPlayers().get(0);
		player.setPlace(11);

		game.roll(4);

		assertEquals(3, player.getPlace(), "Le joueur devrait atterrir sur la case 3 après avoir dépassé 12");
	}

	@Test
	public void correct_answer_checks_win_condition() {
		Game game = new Game();
		game.add("Fanny");
		game.add("Greg");

		Player player = game.getPlayers().get(0);

		for(int i=0; i<5; i++) {
			player.addCoin();
		}
		assertEquals(5, player.getPurse());

		boolean isGameStillInProgress = game.handleCorrectAnswer();

		assertEquals(6, player.getPurse(), "Le joueur devrait avoir 6 pièces");
		assertFalse(isGameStillInProgress, "Le jeu devrait être terminé (false) car le joueur a gagné");
	}

	@Test
	public void wrong_answer_sends_player_to_penalty_box() {
		Game game = new Game();
		game.add("Hugo");
		game.add("Isabelle");

		game.wrongAnswer();

		Player hugo = game.getPlayers().get(0);
		assertTrue(hugo.isInPenaltyBox(), "Le joueur devrait être envoyé en prison après une mauvaise réponse");
	}

	@Test
	public void player_in_penalty_box_rolling_even_stays_in_box() {
		Game game = new Game();
		game.add("Jack");
		game.add("Karen");

		Player jack = game.getPlayers().get(0);
		jack.setInPenaltyBox(true);
		int initialPlace = jack.getPlace();

		game.roll(4);
		game.handleCorrectAnswer();

		assertEquals(initialPlace, jack.getPlace(), "Le joueur ne doit pas bouger");
		assertEquals(0, jack.getPurse(), "Le joueur ne doit pas gagner de pièce");
		assertTrue(jack.isInPenaltyBox(), "Le joueur doit rester en prison");
	}

	@Test
	public void player_penalty_box_rolling_odd_gets_out_and_plays() {
		Game game = new Game();
		game.add("Leo");
		game.add("Mia");

		Player leo = game.getPlayers().get(0);
		leo.setInPenaltyBox(true);
		leo.setPlace(1);

		game.roll(3);
		game.handleCorrectAnswer();

		assertEquals(4, leo.getPlace(), "Le joueur a dû avancer (1 + 3)");
		assertEquals(1, leo.getPurse(), "Le joueur a dû gagner une pièce");
		assertFalse(leo.isInPenaltyBox(), "Le joueur doit être libéré de la prison");
	}

	@Test
	public void cannot_add_more_than_6_players() {
		Game game = new Game();

		assertTrue(game.add("P1"));
		assertTrue(game.add("P2"));
		assertTrue(game.add("P3"));
		assertTrue(game.add("P4"));
		assertTrue(game.add("P5"));
		assertTrue(game.add("P6"));

		assertFalse(game.add("P7"), "Le jeu ne devrait pas accepter un 7ème joueur");
		assertEquals(6, game.getPlayers().size(), "Il ne devrait y avoir que 6 joueurs dans la liste");
	}

	@Test
	public void cannot_add_two_players_with_the_same_name() {
		Game game = new Game();

		assertTrue(game.add("Alice"));

		assertFalse(game.add("Alice"), "Le jeu ne devrait pas accepter un deuxième joueur avec le même nom");

		assertEquals(1, game.getPlayers().size(), "Il ne devrait y avoir qu'un seul joueur dans la liste");
	}
}
