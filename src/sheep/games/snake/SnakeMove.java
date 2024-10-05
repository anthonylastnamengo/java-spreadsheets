package sheep.games.snake;

import sheep.games.snake.moves.*;
import sheep.ui.Perform;
import sheep.ui.Prompt;

/**
 * The SnakeMove class represents the movement action performed by the snake in the game.
 * It implements the Perform interface to define the action's behavior.
 */
public class SnakeMove implements Perform {

    /** The current game state. */
    private final GameState state;

    /** The direction of the movement. */
    private final String direction;

    /**
     * Constructs a new SnakeMove object with the specified direction and game state.
     *
     * @param direction The direction of the movement ("UP", "DOWN", "LEFT", or "RIGHT").
     * @param state The current game state.
     */
    public SnakeMove(String direction, GameState state) {
        this.state = state;
        this.direction = direction;
    }

    /**
     * Performs the movement action based on the specified direction.
     *
     * @param row The row index of the cell.
     * @param column The column index of the cell.
     * @param prompt The Prompt object for displaying messages.
     */
    @Override
    public void perform(int row, int column, Prompt prompt) {
        // Check if the game has started
        if (!this.state.isStarted()) {
            return;
        }

        // Initialise a new move based on the input String
        SnakeMovement newMove = null;
        switch (this.direction) {
            case "UP" -> {
                newMove = new MoveUp();
            }
            case "DOWN" -> {
                newMove = new MoveDown();
            }
            case "LEFT" -> {
                newMove = new MoveLeft();
            }
            case "RIGHT" -> {
                newMove = new MoveRight();
            }
        }

        // Update the current row and column shifts based on the new move
        if (newMove != null) {
            this.state.setCurrentRowShift(newMove.getRowShift());
            this.state.setCurrentColShift(newMove.getColShift());
        }
    }
}

