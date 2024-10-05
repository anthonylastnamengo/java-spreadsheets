package sheep.games.tetros;

import sheep.games.random.RandomCell;
import sheep.games.random.RandomTile;

public class NewPiece {

    private RandomTile randomTile;
    private TetrosGameState gameState;
    private TetrosCellState cellState;

    public NewPiece(RandomTile randomTile, TetrosGameState gameState, TetrosCellState cellState) {
        this.cellState = cellState;
        this.gameState = gameState;
        this.randomTile = randomTile;
    }

    public void newPiece() {
        // Generate a new random piece value
        int tilePicker = randomTile.pick();

        // Generate a new piece
        TetrosPieceFactory newFallingPiece = new TetrosPieceFactory(tilePicker,
                this.cellState,
                this.gameState);
        newFallingPiece.generatePiece();
    }
}
