package sheep.games.tetros;

public class TetrosGameState {

    /** "Switch" to indicate whether Tetros is currently running or not */
    private boolean started = false;

    /** A falling type that indicates which piece is currently falling */
    private int fallingType = 1;

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getFallingType() {
        return fallingType;
    }

    public void setFallingType(int fallingType) {
        this.fallingType = fallingType;
    }
}
