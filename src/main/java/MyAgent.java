import java.util.Random;

/**
 * My move method is structured to go through a series of checks and if all
 * those are false, and the move number greater than 1, the agent makes a random
 * move. First, the agent checks for any moves that it can make to win or any
 * moves than it can make to block the other player from winning. Then, my agent
 * looks for moves to make in order to get 3 in a row or block them from getting
 * three in a row. Next, my agent looks for moves to make in order to get 2 in a
 * row or block them from getting 2 in a row. Usually, because there are not
 * many pieces on the board, the agent places its first piece in the middle
 * column. Lastly, at each check, a piece is only placed there if the opponent
 * cannot win with one move following the move my agent is about to make.
 * 
 * @author <cwarren5701>
 *
 */
public class MyAgent extends Agent {
	/**
	 * A random number generator to randomly decide where to place a token.
	 */
	private Random random;

	private int moveNumber;

	/**
	 * Constructs a new agent, giving it the game and telling it whether it is Red
	 * or Yellow.
	 *
	 * @param game   The game the agent will be playing.
	 * @param iAmRed True if the agent is Red, False if the agent is Yellow.
	 */
	public MyAgent(Connect4Game game, boolean iAmRed) {
		super(game, iAmRed);
		random = new Random();
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
		// System.out.println(moveNumber);
		clearMoveCount();
		int iCanWinColumn = iCanWin();
		if (iCanWinColumn != -1) {
			moveOnColumn(iCanWinColumn);
		} else if (theyCanWin() != -1 && !theyCanWinIfIMoveHere(theyCanWin())) {
			moveOnColumn(theyCanWin());
		} else if (checkForTwo() != -1 && !theyCanWinIfIMoveHere(checkForTwo())) {
			moveOnColumn(checkForTwo());
		} else if (checkForOne() != -1 && !theyCanWinIfIMoveHere(checkForOne())) {
			moveOnColumn(checkForOne());
		} else if (checkThemForTwo() != -1 && !theyCanWinIfIMoveHere(checkThemForTwo())) {
			moveOnColumn(checkThemForTwo());
		} else if (checkThemForOne() != -1 && !theyCanWinIfIMoveHere(checkThemForOne())) {
			moveOnColumn(checkThemForOne());
		} else if (moveNumber < 1 && !theyCanWinIfIMoveHere(3)) {
			moveOnColumn(3);
		} else {
			moveOnColumn(randomMove());
		}
		// printGameBoard();
		moveNumber++;
	}

	/**
	 * Resets the int moveNumber by looping through the board and looking for any
	 * slots with my color.
	 */
	public void clearMoveCount() {
		// Get board matrix
		Connect4Game copyGame = new Connect4Game(myGame);
		char[][] boardMatrix = copyGame.getBoardMatrix();
		char color;
		if (iAmRed == true) {
			color = 'R';
		} else {
			color = 'Y';
		}

		// loop through all slots
		for (int row = 0; row < copyGame.getRowCount(); row++) {
			for (int col = 0; col < copyGame.getColumnCount(); col++) {
				// if any slots have my color, don't reset
				if (boardMatrix[row][col] == color) {
					return;
				}

			}
		}
		// otherwise reset
		moveNumber = 0;
	}

	/**
	 * Prints the game board as it appears in a 2D array to the console.
	 */
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

	/**
	 * Checks the board for two in a row of my color, and returns the column that I
	 * can place in to get three in a row.
	 * 
	 * @return a column number.
	 */
	public int checkForTwo() {
		Connect4Game copyGame = new Connect4Game(myGame);
		char[][] boardMatrix = copyGame.getBoardMatrix();
		char color = ' ';
		if (iAmRed == true) {
			color = 'R';
		} else {
			color = 'Y';
		}
		for (int row = 0; row < copyGame.getRowCount(); row++) {
			for (int col = 0; col < copyGame.getColumnCount(); col++) {
				if (boardMatrix[row][col] == 'B') {
					if (checkForTwoLeft(boardMatrix, row, col, color) == true
							|| checkForTwoRight(boardMatrix, row, col, color) == true
							|| checkForTwoBelow(boardMatrix, row, col, color) == true) {
						return col;
					}
				}
			}
		}

		return -1;
	}

	/**
	 * Returns <code>true</code> if both slots to the left are the same color.
	 * 
	 * @param thisBoardMatrix the board matrix.
	 * @param thisRow         the row number.
	 * @param thisCol         the column number.
	 * @param color           the color of the agent.
	 * @return <code>true</code> if both slots to the left are the same color;
	 *         <code>false</code> otherwise
	 */
	public boolean checkForTwoLeft(char[][] thisBoardMatrix, int thisRow, int thisCol, char color) {
		if (thisCol > 1) {
			if (color == thisBoardMatrix[thisRow][thisCol - 1] && color == thisBoardMatrix[thisRow][thisCol - 2]) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if both slots to the right are the same color.
	 * 
	 * @param thisBoardMatrix the board matrix.
	 * @param thisRow         the row number.
	 * @param thisCol         the column number.
	 * @param color           the color of the agent.
	 * @return <code>true</code> if both slots to the right are the same color;
	 *         <code>false</code> otherwise
	 */
	public boolean checkForTwoRight(char[][] thisBoardMatrix, int thisRow, int thisCol, char color) {
		if (thisCol < 5) {
			if (color == thisBoardMatrix[thisRow][thisCol + 1] && color == thisBoardMatrix[thisRow][thisCol + 2]) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if both slots below are the same color.
	 * 
	 * @param thisBoardMatrix the board matrix.
	 * @param thisRow         the row number.
	 * @param thisCol         the column number.
	 * @param color           the color of the agent.
	 * @return <code>true</code> if both slots below are the same color;
	 *         <code>false</code> otherwise
	 */
	public boolean checkForTwoBelow(char[][] thisBoardMatrix, int thisRow, int thisCol, char color) {
		if (thisRow < 4) {
			if (color == thisBoardMatrix[thisRow + 1][thisCol] && color == thisBoardMatrix[thisRow + 2][thisCol]) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks the board for one of my color, and returns the column that I can place
	 * in to get two in a row.
	 * 
	 * @return a column number.
	 */
	public int checkForOne() {
		Connect4Game copyGame = new Connect4Game(myGame);
		char[][] boardMatrix = copyGame.getBoardMatrix();
		char color = ' ';
		if (iAmRed == true) {
			color = 'R';
		} else {
			color = 'Y';
		}
		for (int row = 0; row < copyGame.getRowCount(); row++) {
			for (int col = 0; col < copyGame.getColumnCount(); col++) {
				if (boardMatrix[row][col] == 'B') {
					if (checkForOneLeft(boardMatrix, row, col, color) == true
							|| checkForOneRight(boardMatrix, row, col, color) == true
							|| checkForOneBelow(boardMatrix, row, col, color) == true) {
						return col;
					}
				}
			}
		}
		return -1;
	}

	/**
	 * Checks the board for one of their color, and returns the column that they can
	 * place in to get two in a row.
	 * 
	 * @return a column number.
	 */
	public int checkThemForOne() {
		Connect4Game copyGame = new Connect4Game(myGame);
		char[][] boardMatrix = copyGame.getBoardMatrix();
		char color = ' ';
		if (iAmRed == true) {
			color = 'Y';
		} else {
			color = 'R';
		}
		for (int row = 0; row < copyGame.getRowCount(); row++) {
			for (int col = 0; col < copyGame.getColumnCount(); col++) {
				if (boardMatrix[row][col] == 'B') {
					if (checkForOneLeft(boardMatrix, row, col, color) == true
							|| checkForOneRight(boardMatrix, row, col, color) == true
							|| checkForOneBelow(boardMatrix, row, col, color) == true) {
						return col;
					}
				}
			}
		}
		return -1;
	}

	/**
	 * Returns <code>true</code> if one slot to the left is the same color.
	 * 
	 * @param thisBoardMatrix the board matrix.
	 * @param thisRow         the row number.
	 * @param thisCol         the column number.
	 * @param color           the color of the agent.
	 * @return <code>true</code> if one slot to the left is the same color;
	 *         <code>false</code> otherwise
	 */
	public boolean checkForOneLeft(char[][] thisBoardMatrix, int thisRow, int thisCol, char color) {
		if (thisCol > 0) {
			if (color == thisBoardMatrix[thisRow][thisCol - 1]) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if one slot to the right is the same color.
	 * 
	 * @param thisBoardMatrix the board matrix.
	 * @param thisRow         the row number.
	 * @param thisCol         the column number.
	 * @param color           the color of the agent.
	 * @return <code>true</code> if one slot to the right is the same color;
	 *         <code>false</code> otherwise
	 */
	public boolean checkForOneRight(char[][] thisBoardMatrix, int thisRow, int thisCol, char color) {
		if (thisCol < 6) {
			if (color == thisBoardMatrix[thisRow][thisCol + 1]) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns <code>true</code> if one slot below is the same color.
	 * 
	 * @param thisBoardMatrix the board matrix.
	 * @param thisRow         the row number.
	 * @param thisCol         the column number.
	 * @param color           the color of the agent.
	 * @return <code>true</code> if one slot below is the same color;
	 *         <code>false</code> otherwise
	 */
	public boolean checkForOneBelow(char[][] thisBoardMatrix, int thisRow, int thisCol, char color) {
		if (thisRow < 5) {
			if (color == thisBoardMatrix[thisRow + 1][thisCol]) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks the board for two in a row of their color, and returns the column that
	 * they can place in to get three in a row.
	 * 
	 * @return a column number.
	 */
	public int checkThemForTwo() {
		Connect4Game copyGame = new Connect4Game(myGame);
		char[][] boardMatrix = copyGame.getBoardMatrix();
		char color = ' ';
		if (iAmRed == true) {
			color = 'Y';
		} else {
			color = 'R';
		}
		for (int row = 0; row < copyGame.getRowCount(); row++) {
			for (int col = 0; col < copyGame.getColumnCount(); col++) {
				if (boardMatrix[row][col] == 'B') {
					if (checkForTwoLeft(boardMatrix, row, col, color) == true
							|| checkForTwoRight(boardMatrix, row, col, color) == true
							|| checkForTwoBelow(boardMatrix, row, col, color) == true) {
						return col;
					}
				}
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
			MyAgent copyAgent = new MyAgent(copyGame, iAmRed);
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
	 * Returns <code>true</code> if they can win if I move in the given column.
	 * 
	 * @param col the column number.
	 * @return <code>true</code> if they can win if I move in the given column;
	 *         <code>false</code> otherwise
	 */
	public boolean theyCanWinIfIMoveHere(int col) {
		Connect4Game copyGame = new Connect4Game(myGame);
		MyAgent copyAgent = new MyAgent(copyGame, iAmRed);
		if (!copyGame.getColumn(col).getIsFull()) {
			copyAgent.moveOnColumn(col);
			if (copyAgent.theyCanWin() != -1) {
				return true;
			}
		}
		return false;
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
			MyAgent copyAgent = new MyAgent(copyGame, !iAmRed);
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
