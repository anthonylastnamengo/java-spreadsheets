package sheep.games.snake;

/**
 * The SnakeMovement interface represents the movement behavior of the snake in the game.
 * It defines methods to retrieve the row and column shifts associated with a particular movement.
 */
public interface SnakeMovement {

    /**
     * Retrieves the row shift associated with the movement.
     *
     * @return The amount by which the snake's row position changes during this movement.
     */
    int getRowShift();

    /**
     * Retrieves the column shift associated with the movement.
     *
     * @return The amount by which the snake's column position changes during this movement.
     */
    int getColShift();
}

