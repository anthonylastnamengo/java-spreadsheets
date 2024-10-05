package sheep.games.snake.moves;

import sheep.games.snake.SnakeMovement;

/**
 * The MoveDown class represents the movement of the snake downwards.
 * It implements the SnakeMovement interface to provide the row and column shifts for the movement.
 */
public class MoveDown implements SnakeMovement {

    /** The shift in the row direction for moving down. */
    private int rowShift = 1;

    /** The shift in the column direction for moving down. */
    private int colShift = 0;

    /**
     * Returns the shift in the row direction for moving down.
     *
     * @return The shift in the row direction for moving down.
     */
    @Override
    public int getRowShift() {
        return this.rowShift;
    }

    /**
     * Returns the shift in the column direction for moving down.
     *
     * @return The shift in the column direction for moving down.
     */
    @Override
    public int getColShift() {
        return this.colShift;
    }
}
