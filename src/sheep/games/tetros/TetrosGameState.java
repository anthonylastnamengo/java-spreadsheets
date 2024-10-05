package sheep.games.tetros;

/**
 * Manages the game state of Tetros, including whether the game is currently running and the type of the falling piece.
 */
public class TetrosGameState {

    /** Indicates whether Tetros is currently running. */
    private boolean started = false;

    /** Represents the type of the falling piece. */
    private int fallingType = 1;

    /**
     * Checks if Tetros is currently running.
     *
     * @return True if Tetros is running, false otherwise
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Sets the running state of Tetros.
     *
     * @param started The running state to set
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * Gets the type of the falling piece.
     *
     * @return The type of the falling piece
     */
    public int getFallingType() {
        return fallingType;
    }

    /**
     * Sets the type of the falling piece.
     *
     * @param fallingType The type of the falling piece to set
     */
    public void setFallingType(int fallingType) {
        this.fallingType = fallingType;
    }
}

