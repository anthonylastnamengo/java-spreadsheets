package sheep;

import sheep.expression.CoreFactory;
import sheep.expression.ExpressionFactory;
import sheep.features.Feature;
import sheep.features.files.FileLoading;
import sheep.features.files.FileSaving;
import sheep.fun.Fibonacci;
import sheep.fun.FibonacciConstants;
import sheep.fun.FunException;
import sheep.fun.Pascal;
import sheep.games.life.Life;
import sheep.games.random.RandomFreeCell;
import sheep.games.random.RandomTetrosTile;
import sheep.games.snake.Snake;
import sheep.games.tetros.Tetros;
import sheep.parsing.Parser;
import sheep.parsing.SimpleParser;
import sheep.sheets.GameSheet;
import sheep.sheets.Sheet;
import sheep.ui.UI;
import sheep.ui.graphical.GUI;

import java.util.HashMap;
import java.util.Random;

/**
 * Execute the SheeP spreadsheet program.
 * This file is for you to execute your program,
 * it will not be marked.
 */
public class Main {

    /**
     * Start the spreadsheet program.
     * @param args Parameters to the program, currently not supported.
     * @throws FunException If a pre-populator fails to insert an expression.
     */
    public static void main(String[] args) throws FunException {
        ExpressionFactory factory = new CoreFactory();
        Parser parser = new SimpleParser(factory);

        Sheet sheet = new GameSheet(parser, new HashMap<>(),
                factory.createEmpty(), 20, 20);

        // Stage 1a: Pre-populate sheets (just for fun).
//        new FibonacciConstants(20).draw(sheet);
//       new Fibonacci(20).draw(sheet);
//        new Pascal(4, 2).draw(sheet);

//       System.out.println(sheet.encode());

        Feature[] features = new Feature[]{
                new FileLoading(sheet),
                new FileSaving(sheet),
                new Life(sheet),
                new Snake(sheet, new RandomFreeCell(sheet, new Random())),
                new Tetros(sheet, new RandomTetrosTile(new Random()))
        };

        UI ui = new GUI(sheet, sheet);
        for (Feature feature : features) {
            feature.register(ui);
        }

        ui.render();
    }
}
