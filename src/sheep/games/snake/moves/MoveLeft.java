package sheep.games.snake.moves;

import sheep.games.snake.SnakeMovement;

/**
 * The class represents the movement of the snake to the left direction.
 * It implements the Snake Movement interface to provide the row and column shifts for the movement.
 */
public class MoveLeft implements SnakeMovement {

    /** The shift in the row direction for moving left (no change). */
    private int rowShift = 0;

    /** The shift in the column direction for moving left (to the left by one position). */
    private int colShift = -1;

    /**
     * Returns the shift in the row direction for moving left.
     *
     * @return The shift in the row direction for moving left.
     */
    @Override
    public int getRowShift() {
        return this.rowShift;
    }

    /**
     * Returns the shift in the column direction for moving left.
     *
     * @return The shift in the column direction for moving left.
     */
    @Override
    public int getColShift() {
        return this.colShift;
    }
}
