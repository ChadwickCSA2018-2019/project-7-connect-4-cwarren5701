import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyAgentTest {

	Connect4Game game;
	final int NUM_OF_TEST_GAMES = 50;

	@Before
	public void setUp() throws Exception {
		game = new Connect4Game(7, 6);
	}

	@Test
	public void testICanWinVerticallySimple() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		for (int i = 0; i < 3; i++) {
			redAgent.moveOnColumn(1);
			yellowAgent.moveOnColumn(2);
		}

		assertEquals(1, redAgent.iCanWin());

	}

	@Test
	public void testICanWinVerticallyTop4() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		for (int i = 0; i < 2; i++) {
			redAgent.moveOnColumn(1);
			yellowAgent.moveOnColumn(2);
		}

		for (int i = 0; i < 3; i++) {
			redAgent.moveOnColumn(2);
			yellowAgent.moveOnColumn(1);
		}

		assertEquals(2, redAgent.iCanWin());

	}

	// TODO: Write 2 test cases for testICanWinHorizontally
	@Test
	public void testICanWinHorizontallyBottom() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		for (int i = 0; i < 3; i++) {
			redAgent.moveOnColumn(i + 2);
			yellowAgent.moveOnColumn(1);
		}

		assertEquals(5, redAgent.iCanWin());
	}

	@Test
	public void testICanWinHorizontallyBottom2ndRow() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		for (int i = 0; i < 2; i++) {
			redAgent.moveOnColumn(i + 1);
			yellowAgent.moveOnColumn(i + 3);
		}
		for (int i = 0; i < 3; i++) {
			redAgent.moveOnColumn(i + 1);
			yellowAgent.moveOnColumn(0);
		}
		assertEquals(4, redAgent.iCanWin());
	}

	// TODO: Write 2 test cases for testICanWinDiagonally
	@Test
	public void testICanWinDiagonallySimple() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		for (int i = 0; i < 2; i++) {
			redAgent.moveOnColumn(i + 1);
			yellowAgent.moveOnColumn(i + 2);
		}
		redAgent.moveOnColumn(4);
		yellowAgent.moveOnColumn(3);
		redAgent.moveOnColumn(3);
		yellowAgent.moveOnColumn(4);
		redAgent.moveOnColumn(4);
		yellowAgent.moveOnColumn(5);
		assertEquals(4, redAgent.iCanWin());
	}

	@Test
	public void testICanWinDiagnallyAlternating() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		redAgent.moveOnColumn(6);
		for (int i = 0; i < 3; i += 2) {
			yellowAgent.moveOnColumn(i + 1);
			redAgent.moveOnColumn(i + 2);
		}
		yellowAgent.moveOnColumn(5);
		for (int i = 0; i < 3; i += 2) {
			redAgent.moveOnColumn(i + 1);
			yellowAgent.moveOnColumn(i + 2);
		}
		redAgent.moveOnColumn(5);
		for (int i = 0; i < 3; i += 2) {
			yellowAgent.moveOnColumn(i + 1);
			redAgent.moveOnColumn(i + 2);
		}
		yellowAgent.moveOnColumn(5);
		assertEquals(1, redAgent.iCanWin());
	}

	@Test
	public void testTheyCanWin() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		for (int i = 0; i < 3; i++) {
			redAgent.moveOnColumn(1);
			yellowAgent.moveOnColumn(2);
		}

		assertEquals(2, redAgent.theyCanWin());
	}

	// TODO: Write testTheyCanWinHorizontally
	@Test
	public void testTheyCanWinHorizontallyBottom() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		for (int i = 0; i < 2; i++) {
			redAgent.moveOnColumn(1);
			yellowAgent.moveOnColumn(i + 2);
		}
		redAgent.moveOnColumn(6);
		yellowAgent.moveOnColumn(4);
		assertEquals(5, redAgent.theyCanWin());
	}

	// TODO: Write testTheyCanWinDiagonally
	@Test
	public void testTheyCanWinDiagonally() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		redAgent.moveOnColumn(0);
		yellowAgent.moveOnColumn(1);
		redAgent.moveOnColumn(1);
		yellowAgent.moveOnColumn(2);
		redAgent.moveOnColumn(3);
		yellowAgent.moveOnColumn(2);
		redAgent.moveOnColumn(2);
		yellowAgent.moveOnColumn(3);
		redAgent.moveOnColumn(0);
		yellowAgent.moveOnColumn(3);
		assertEquals(3, redAgent.iCanWin());

	}

	// Tests you can win against a Beginner agent as Red
	@Test
	public void testRedWinningBeginnerAgent() {
		Agent redAgent = new MyAgent(game, true);
		Agent yellowAgent = new BeginnerAgent(game, false);
		int numberOfWins = 0;
		for (int i = 0; i < NUM_OF_TEST_GAMES; i++) {
			game.clearBoard();
			while (!game.boardFull() && game.gameWon() == 'N') {
				redAgent.move();
				if (game.gameWon() != 'R' && !game.boardFull()) {
					yellowAgent.move();
				}
			}

			if (game.gameWon() == 'R') {
				numberOfWins++;
			}
		}
		System.out.println("You won: " + numberOfWins + " games as Red against Beginner");
		// Test that you win over 90% of your games
		assertTrue(numberOfWins >= 45);
	}

	// Tests you can win against a Beginner agent as Yellow
	@Test
	public void testYellowWinningBeginnerAgent() {
		Agent redAgent = new BeginnerAgent(game, true);
		Agent yellowAgent = new MyAgent(game, false);
		int numberOfWins = 0;
		for (int i = 0; i < NUM_OF_TEST_GAMES; i++) {
			game.clearBoard();
			while (!game.boardFull() && game.gameWon() == 'N') {
				redAgent.move();
				if (game.gameWon() != 'R' && !game.boardFull()) {
					yellowAgent.move();
				}
			}

			if (game.gameWon() == 'Y') {
				numberOfWins++;
			}
		}
		System.out.println("You won: " + numberOfWins + " games as Yellow against Beginner");
		// Test that you win over 90% of your games
		assertTrue(numberOfWins >= 45);
	}

	// Tests you can win against a Random agent as Red
	@Test
	public void testRedWinningRandomAgent() {
		Agent redAgent = new MyAgent(game, true);
		Agent yellowAgent = new RandomAgent(game, false);
		int numberOfWins = 0;
		for (int i = 0; i < NUM_OF_TEST_GAMES; i++) {
			game.clearBoard();
			while (!game.boardFull() && game.gameWon() == 'N') {
				redAgent.move();
				if (game.gameWon() != 'R' && !game.boardFull()) {
					yellowAgent.move();
				}
			}

			if (game.gameWon() == 'R') {
				numberOfWins++;
			}
		}
		System.out.println("You won: " + numberOfWins + " games as Red against Random");
		// Test that you win over 90% of your games
		assertTrue(numberOfWins >= 45);
	}

	// Tests that the checkForTwo method works
	@Test
	public void testRedGetting3InARow() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		redAgent.moveOnColumn(1);
		yellowAgent.moveOnColumn(2);
		redAgent.moveOnColumn(1);
		yellowAgent.moveOnColumn(3);
		assertTrue(redAgent.checkForTwo() == 1);
	}
	
	// Tests that checkForTwo method works
	@Test
	public void testRedGetting3InARowFalse() {
		MyAgent redAgent = new MyAgent(game, true);
		MyAgent yellowAgent = new MyAgent(game, false);
		game.clearBoard();
		redAgent.moveOnColumn(1);
		yellowAgent.moveOnColumn(2);
		redAgent.moveOnColumn(1);
		yellowAgent.moveOnColumn(3);
		redAgent.moveOnColumn(4);
		yellowAgent.moveOnColumn(1);
		assertFalse(redAgent.checkForTwo() == 1);
	}

	// Tests you can win against a Random agent as Red
	@Test
	public void testYellowWinningRandomAgent() {
		Agent redAgent = new RandomAgent(game, true);
		Agent yellowAgent = new MyAgent(game, false);
		int numberOfWins = 0;
		for (int i = 0; i < NUM_OF_TEST_GAMES; i++) {
			game.clearBoard();
			while (!game.boardFull() && game.gameWon() == 'N') {
				redAgent.move();
				if (game.gameWon() != 'R' && !game.boardFull()) {
					yellowAgent.move();
				}
			}

			if (game.gameWon() == 'Y') {
				numberOfWins++;
			}
		}
		System.out.println("You won: " + numberOfWins + " games as Yellow against Random");
		// Test that you win over 90% of your games
		assertTrue(numberOfWins >= 45);
	}

	// BONUS TODO: Write testCases to play against IntermediateAgent

	// SUPER BONUS TODO: Write testCases to playAgainst AdvancedAgent

	// SUPER BONUS TODO: Write testCases to playAgainst BrilliantAgent

}
