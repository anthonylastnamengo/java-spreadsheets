package sheep.games.tetros;

import sheep.games.tetros.pieces.*;
import sheep.sheets.CellLocation;

import java.util.List;

/**
 * Implementation for creating a new Tetros Piece
 */
public class TetrosPieceFactory {

    /** The value that determines which piece to generate */
    private final int value;

    private TetrosCellState cellState;
    private TetrosGameState gameState;

    public TetrosPieceFactory(int value, TetrosCellState cellState, TetrosGameState gameState) {
        this.value = value;

        this.cellState = cellState;

        this.gameState = gameState;
    }

    /**
     * Generates the new piece based on the value passed into the Piece instance
     */
    public void generatePiece() {
        // Initialise a new blank Piece
        TetrosPiece newPiece = null;
        switch (this.value) {

            // Initialise a new Piece type based on the input value
            case 1 -> {
                newPiece = new PieceSeven();
            }
            case 2 -> {
                newPiece = new PieceFive();
            }
            case 3 -> {
                newPiece = new PieceEight();
            }
            case 4 -> {
                newPiece = new PieceThree();
            }
            case 5 -> {
                newPiece = new PieceSix();
            }
            case 6 -> {
                newPiece = new PieceTwo();
            }
            case 0 -> {
                newPiece = new PieceFour();
            }
        }


        if (newPiece != null) {
            // Depending on the piece generated:

            // Update the contents of the sheet
            newPiece.updatePieceContents(this.cellState.getContents());

            // Update the falling type
            this.gameState.setFallingType(newPiece.getFallingType());
        }
    }


}