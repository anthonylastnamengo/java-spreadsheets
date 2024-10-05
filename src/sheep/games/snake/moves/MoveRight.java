package sheep.games.snake.moves;

import sheep.games.snake.SnakeMovement;

/**
 * The MoveRight class represents the movement of the snake to the right.
 * It implements the SnakeMovement interface to provide the row and column shifts for the movement.
 */
public class MoveRight implements SnakeMovement {

    /** The shift in the row direction for moving right. */
    private int rowShift = 0;

    /** The shift in the column direction for moving right. */
    private int colShift = 1;

    /**
     * Returns the shift in the row direction for moving right.
     *
     * @return The shift in the row direction for moving right.
     */
    @Override
    public int getRowShift() {
        return this.rowShift;
    }

    /**
     * Returns the shift in the column direction for moving right.
     *
     * @return The shift in the column direction for moving right.
     */
    @Override
    public int getColShift() {
        return this.colShift;
    }
}
