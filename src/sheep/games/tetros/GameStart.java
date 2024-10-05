package sheep.games.tetros;

import sheep.games.snake.GameState;
import sheep.ui.Perform;
import sheep.ui.Prompt;

/**
 * Represents the action of starting a game of Tetros.
 * Implements the Perform interface to perform the action when triggered.
 */
public class GameStart implements Perform {

    /** The game state of Tetros */
    private final TetrosGameState gameState;

    /** The dropper responsible for dropping tetrominoes */
    private final TetrosDropper dropper;

    /**
     * Initializes a new instance of the GameStart class with the specified game state and dropper.
     *
     * @param gameState The game state of Tetros
     * @param dropper The dropper responsible for dropping tetrominoes
     */
    public GameStart(TetrosGameState gameState, TetrosDropper dropper) {
        this.gameState = gameState;
        this.dropper = dropper;
    }

    /**
     * Performs the action of starting a game of Tetros.
     * Sets the game state to started and initiates the first drop.
     *
     * @param row The row where the action is performed (not used in this context)
     * @param column The column where the action is performed (not used in this context)
     * @param prompt The prompt to interact with the user interface
     */
    @Override
    public void perform(int row, int column, Prompt prompt) {
        gameState.setStarted(true);
        dropper.drop();
    }
}
