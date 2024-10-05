package sheep.games.snake;

import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;
import sheep.ui.Perform;
import sheep.ui.Prompt;

/**
 * Responsible for starting a new Snake game
 */
public class SnakeStart implements Perform {

    /** The game state to set when starting a new snake game */
    private final GameState gameState;

    /** The handler for apple logic */
    private final AppleFactory appleFactory;

    /** The handler for snake logic */
    private final SnakeFactory snakeFactory;

    /** The sheet to start a new Snake game */
    private final Sheet sheet;

    /**
     * Starts a new snake game
     *
     * @param gameState         The game's state to store information about the game
     * @param appleFactory      The handler for apple logic
     * @param snakeFactory      The handler for snake logic
     * @param sheet             The sheet to start a new snake game in
     */
    public SnakeStart(GameState gameState,
                      AppleFactory appleFactory,
                      SnakeFactory snakeFactory,
                      Sheet sheet) {
        this.appleFactory = appleFactory;
        this.gameState = gameState;
        this.snakeFactory = snakeFactory;
        this.sheet = sheet;

    }

    /**
     * Performs when a new Snake game is started
     *
     * @param row The currently selected row of the user, or -2 if none selected.
     * @param column The currently selected column of the user, or -2 if none selected.
     * @param prompt Provides a mechanism to interact with the user interface
     *               after an interaction, if required.
     */
    @Override
    public void perform(int row, int column, Prompt prompt) {
        // Update the started boolean in the game state to true
        this.gameState.setStarted(true);
        // Create a new snake head at the clicked cell
        newHead(row, column);

        // Search the sheet for apple representations and add them to the apple state
        appleFactory.addApples();
    }

    /**
     * Creates a new head for the snake when game is started
     * @param row       The row to start the snake's head in
     * @param column    The column to start the snake's head in
     */
    public void newHead(int row, int column) {
        // Create and set the newHead as specified by row and column
        this.sheet.update(row, column, "1");

        // Update the snake factory with the new snake head
        snakeFactory.setSnakeHead(new CellLocation(row, column));
    }

}


