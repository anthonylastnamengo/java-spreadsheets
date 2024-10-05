package sheep.games.snake;

/**
 * The class represents the current state of the Snake game.
 * It includes information such as the current direction of movement and whether the game has started.
 */
public class GameState {

    /** The current shift in the column direction. Defaults to 0 */
    private int currentColShift = 0;

    /** The current shift in the row direction. Defaults to 1, in the down direction*/
    private int currentRowShift = 1;

    /** Indicates whether the game has started. */
    private boolean started = false;

    /**
     * Gets the current shift in the column direction.
     *
     * @return The current shift in the column direction.
     */
    public int getCurrentColShift() {
        return currentColShift;
    }

    /**
     * Sets the current shift in the column direction.
     *
     * @param currentColShift The current shift in the column direction to set.
     */
    public void setCurrentColShift(int currentColShift) {
        this.currentColShift = currentColShift;
    }

    /**
     * Gets the current shift in the row direction.
     *
     * @return The current shift in the row direction.
     */
    public int getCurrentRowShift() {
        return currentRowShift;
    }

    /**
     * Sets the current shift in the row direction.
     *
     * @param currentRowShift The current shift in the row direction to set.
     */
    public void setCurrentRowShift(int currentRowShift) {
        this.currentRowShift = currentRowShift;
    }

    /**
     * Checks whether the game has started.
     *
     * @return true if the game has started, false otherwise.
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Sets whether the game has started.
     *
     * @param started true if the game has started, false otherwise.
     */
    public void setStarted(boolean started) {
        this.started = started;
    }
}
