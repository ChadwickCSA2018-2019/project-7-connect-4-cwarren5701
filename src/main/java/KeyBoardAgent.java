import java.util.Random;
import java.util.Scanner;

/**
 * Describe your basic strategy here.
 * 
 * @author <your Github username>
 *
 */
public class KeyBoardAgent extends Agent {
	/**
	 * A random number generator to randomly decide where to place a token.
	 */
	private Random random;
	private Scanner scanner;
	private int moveNumber;

	/**
	 * Constructs a new agent, giving it the game and telling it whether it is Red
	 * or Yellow.
	 *
	 * @param game   The game the agent will be playing.
	 * @param iAmRed True if the agent is Red, False if the agent is Yellow.
	 */
	public KeyBoardAgent(Connect4Game game, boolean iAmRed) {
		super(game, iAmRed);
		random = new Random();
		random = new Random();
		scanner = new Scanner(System.in);
		moveNumber = 0;
	}

	/**
	 * The move method is run every time it is this agent's turn in the game. You
	 * may assume that when move() is called, the game has at least one open slot
	 * for a token, and the game has not already been won.
	 *
	 * <p>
	 * By the end of the move method, the agent should have placed one token into
	 * the game at some point.
	 * </p>
	 *
	 * <p>
	 * After the move() method is called, the game engine will check to make sure
	 * the move was valid. A move might be invalid if: - No token was place into the
	 * game. - More than one token was placed into the game. - A previous token was
	 * removed from the game. - The color of a previous token was changed. - There
	 * are empty spaces below where the token was placed.
	 * </p>
	 *
	 * <p>
	 * If an invalid move is made, the game engine will announce it and the game
	 * will be ended.
	 * </p>
	 *
	 */
	public void move() {
		System.out.println("Enter a column to move at (0-6)");
		int currentMove = scanner.nextInt();
		moveOnColumn(currentMove);

	}
	public void printGameBoard() {
		Connect4Game copyGame = new Connect4Game(myGame);
		char[][] boardMatrix = copyGame.getBoardMatrix();
		for (int row = 0; row < copyGame.getRowCount(); row++) {
			for (int col = 0; col < copyGame.getColumnCount(); col++) {
				System.out.print(boardMatrix[row][col] + " ");
				if (boardMatrix[row][col] == 'R' && iAmRed) {
					// this is a piece you have placed
					if (col < copyGame.getColumnCount() - 1) {
						// I'm able to check to the right
						// boardMatrix[row][col+1]
					}
				}
			}
			System.out.println();
		}
		System.out.println("-------------");
	}

	private int findTwo() {
		for (int i = 0; i < myGame.getColumnCount(); i++) {
			if (checkForTwo(i)) {
				return i;
			}
		}
		return -1;

	}

	private boolean checkForTwo(int columnNum) {
		Connect4Column currColumn = myGame.getColumn(columnNum);
		int slotToBeFilledNum = getLowestEmptyIndex(currColumn);
		if (slotToBeFilledNum == -1) {
			return false;
		} else {
			boolean color = this.iAmRed;
			// check two to the left and see if they are the same color
			if (columnNum >= 2) {
				Connect4Column columnLeft = myGame.getColumn(columnNum - 1);
				Connect4Slot slotToLeft = columnLeft.getSlot(slotToBeFilledNum);
				Connect4Column columnLeft2 = myGame.getColumn(columnNum - 2);
				Connect4Slot slotToLeft2 = columnLeft2.getSlot(slotToBeFilledNum);
				// System.out.println("cl " + columnLeft2);
				// System.out.println("sl " + slotToLeft2);
				// System.out.println("stbf " + slotToBeFilledNum);
				if (!slotToLeft.getIsFilled() || !slotToLeft2.getIsFilled()) {
					return false;
				} else {
					if ((color != slotToLeft.getIsRed()) || (color != slotToLeft2.getIsRed())) {
						return false;
					} else {
						return true;
					}
				}
				// check two to the right and see if they are the same color
			} else if (columnNum <= 4) {
				Connect4Column columnRight = myGame.getColumn(columnNum + 1);
				Connect4Slot slotToRight = columnRight.getSlot(slotToBeFilledNum);
				Connect4Column columnRight2 = myGame.getColumn(columnNum + 2);
				Connect4Slot slotToRight2 = columnRight2.getSlot(slotToBeFilledNum);
				if (!slotToRight.getIsFilled() || !slotToRight2.getIsFilled()) {
					return false;
				} else {
					if ((color != slotToRight.getIsRed()) || (color != slotToRight2.getIsRed())) {
						return false;
					} else {
						return true;
					}
				}
			}
			return false;
		}
	}

	private int getHighestFilledRed(int columnNum) {
		Connect4Column column = myGame.getColumn(columnNum);
		for (int i = 0; i < column.getRowCount(); i++) {
			Connect4Slot currSlot = column.getSlot(i);
			if (currSlot.getIsFilled() && currSlot.getIsRed()) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Drops a token into a particular column so that it will fall to the bottom of
	 * the column. If the column is already full, nothing will change.
	 *
	 * @param columnNumber The column into which to drop the token.
	 */
	public void moveOnColumn(int columnNumber) {
		// Find the top empty slot in the column
		// If the column is full, lowestEmptySlot will be -1
		int lowestEmptySlotIndex = getLowestEmptyIndex(myGame.getColumn(columnNumber));
		// if the column is not full
		if (lowestEmptySlotIndex > -1) {
			// get the slot in this column at this index
			Connect4Slot lowestEmptySlot = myGame.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);
			// If the current agent is the Red player...
			if (iAmRed) {
				lowestEmptySlot.addRed(); // Place a red token into the empty slot
			} else {
				lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
			}
		}
	}

	/**
	 * Returns the index of the top empty slot in a particular column.
	 *
	 * @param column The column to check.
	 * @return the index of the top empty slot in a particular column; -1 if the
	 *         column is already full.
	 */
	public int getLowestEmptyIndex(Connect4Column column) {
		int lowestEmptySlot = -1;
		for (int i = 0; i < column.getRowCount(); i++) {
			if (!column.getSlot(i).getIsFilled()) {
				lowestEmptySlot = i;
			}
		}
		return lowestEmptySlot;
	}

	/**
	 * Returns a random valid move. If your agent doesn't know what to do, making a
	 * random move can allow the game to go on anyway.
	 *
	 * @return a random valid move.
	 */
	public int randomMove() {
		int i = random.nextInt(myGame.getColumnCount());
		while (getLowestEmptyIndex(myGame.getColumn(i)) == -1) {
			i = random.nextInt(myGame.getColumnCount());
		}
		return i;
	}

	/**
	 * Returns the column that would allow the agent to win.
	 *
	 * <p>
	 * You might want your agent to check to see if it has a winning move available
	 * to it so that it can go ahead and make that move. Implement this method to
	 * return what column would allow the agent to win.
	 * </p>
	 *
	 * @return the column that would allow the agent to win.
	 */
	public int iCanWin() {
		// check to see if I can win in the next move
		for (int i = 0; i < myGame.getColumnCount(); i++) {
			// create a copy of the game
			Connect4Game copyGame = new Connect4Game(myGame);
			KeyBoardAgent copyAgent = new KeyBoardAgent(copyGame, iAmRed);
			if (!copyGame.getColumn(i).getIsFull()) {
				copyAgent.moveOnColumn(i);
				if ((copyGame.gameWon() == 'R' && iAmRed) || (copyGame.gameWon() == 'Y' && !iAmRed)) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Returns the column that would allow the opponent to win.
	 *
	 * <p>
	 * You might want your agent to check to see if the opponent would have any
	 * winning moves available so your agent can block them. Implement this method
	 * to return what column should be blocked to prevent the opponent from winning.
	 * </p>
	 *
	 * @return the column that would allow the opponent to win.
	 */
	public int theyCanWin() {
		// check to see if they can win in the next move
		for (int i = 0; i < myGame.getColumnCount(); i++) {
			// create copy of the game
			Connect4Game copyGame = new Connect4Game(myGame);
			KeyBoardAgent copyAgent = new KeyBoardAgent(copyGame, !iAmRed);
			if (!copyGame.getColumn(i).getIsFull()) {
				copyAgent.moveOnColumn(i);
				if ((copyGame.gameWon() == 'R' && !iAmRed) || (copyGame.gameWon() == 'Y' && iAmRed)) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Returns the name of this agent.
	 *
	 * @return the agent's name
	 */
	public String getName() {
		return "My Agent";
	}
}
