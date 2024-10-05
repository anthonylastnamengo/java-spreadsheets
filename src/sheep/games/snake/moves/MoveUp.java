package sheep.games.snake.moves;

import sheep.games.snake.SnakeMovement;

/**
 * The MoveUp class represents the movement of the snake upwards.
 * It implements the SnakeMovement interface to provide the row and column shifts for the movement.
 */
public class MoveUp implements SnakeMovement {

    /** The shift in the row direction for moving up. */
    private int rowShift = -1;

    /** The shift in the column direction for moving up. */
    private int colShift = 0;

    /**
     * Returns the shift in the row direction for moving up.
     *
     * @return The shift in the row direction for moving up.
     */
    @Override
    public int getRowShift() {
        return this.rowShift;
    }

    /**
     * Returns the shift in the column direction for moving up.
     *
     * @return The shift in the column direction for moving up.
     */
    @Override
    public int getColShift() {
        return this.colShift;
    }

}

