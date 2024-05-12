package sheep.games.tetros;

import sheep.games.tetros.Pieces.*;
import sheep.sheets.CellLocation;

import java.util.List;

/**
 * Implementation for creating a new Tetros Piece
 */
public class TetrosPieceFactory {

    /** The contents of the sheet for rendering */
    private final List<CellLocation> contents;

    /** The value that determines which piece to generate */
    private final int value;

    /** The falling type to set in the main class */
    private int fallingType;

    /**
     * Constructs a new instance of the Piece class
     * A piece is determined by its random value and its falling type.
     *
     * @param value         The value which determines what piece is being generated
     * @param contents      The contents of the sheet that will render the piece generated
     *
     * @requires 0 <= values <= 6
     */
    public TetrosPieceFactory(int value, List<CellLocation> contents) {
        this.value = value;
        this.contents = contents;
        this.fallingType = 0;
    }

    /**
     * Generates the new piece based on the value passed into the Piece instance
     */
    public void generatePiece() {
        TetrosPiece newPiece;

        switch (this.value) {
            case 1 -> {
                PieceSeven sevenPiece = new PieceSeven();
                sevenPiece.getPieceContents(this.contents);
                fallingType = sevenPiece.getFallingType();
            }
            case 2 -> {
                PieceFive fivePiece = new PieceFive();
                fivePiece.getPieceContents(this.contents);
                fallingType = fivePiece.getFallingType();
            }
            case 3 -> {
                PieceEight eightPiece = new PieceEight();
                eightPiece.getPieceContents(this.contents);
                fallingType = eightPiece.getFallingType();
            }
            case 4 -> {
                PieceThree threePiece = new PieceThree();
                threePiece.getPieceContents(this.contents);
                fallingType = threePiece.getFallingType();
            }
            case 5 -> {
                PieceSix sixPiece = new PieceSix();
                sixPiece.getPieceContents(this.contents);
                fallingType = sixPiece.getFallingType();
            }
            case 6 -> {
                PieceTwo twoPiece = new PieceTwo();
                twoPiece.getPieceContents(this.contents);
                fallingType = twoPiece.getFallingType();
            }
            case 0 -> {
                PieceFour fourPiece = new PieceFour();
                fourPiece.getPieceContents(this.contents);
                fallingType = fourPiece.getFallingType();
            }

        }
    }

    /**
     * Gets the falling type denoted by the new Piece
     *
     * @return The falling type of the new Piece
     */
    public int getFallingType() {
        return this.fallingType;
    }
}