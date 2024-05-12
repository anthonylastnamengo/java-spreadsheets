package sheep.features.files;

import sheep.core.ViewElement;
import sheep.expression.Expression;
import sheep.sheets.CellLocation;
import sheep.sheets.Sheet;
import sheep.ui.Perform;
import sheep.ui.Prompt;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

public class Save implements Perform {
    // A class that contains the saving functionality
    // Prompts the user for a filePath
    // Holds the filePath input
    // Attempts to save the sheet to the specific filePath

    private String filePathInput;
    private Path trueFilePath;
    private Sheet sheet;
    public Save(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public void perform(int row, int column, Prompt prompt) {
        // When a Save() instance is created
        // perform() will prompt the user for a file name
        Optional<String> optionalPath = prompt.ask("Save file as:");


        // Obtain the string from the Optional<String>
        if (optionalPath.isPresent()) {
            this.filePathInput = optionalPath.get();
        } else {
            // Most times this is just the case where the user cancels
            return;
        }

        // First convert to a Path instance
        this.trueFilePath = Paths.get("./" + this.filePathInput);

        // Then obtain the File Name from that Path instance
        save(this.trueFilePath, prompt);
    }


    public void save(Path filePath, Prompt prompt){
        try{
            Path writtenFile = Files.createFile(filePath);
            Files.writeString(writtenFile, saveEncode(this.sheet));

        } catch (IOException e) {
            prompt.message("Invalid file path.");
        }
    }

    public String saveEncode(Sheet sheet) {
        // Modified encode function for the sheet.
        // Renders in a space instead of an empty string for Nothing instances.
        StringJoiner builder = new StringJoiner("\n");
        for (int row = 0; row < sheet.getRows(); row++) {
            StringJoiner rowEncoding = new StringJoiner("|");
            for (int column = 0; column < sheet.getColumns(); column++) {
                String formula = sheet.formulaAt(new CellLocation(row, column)).render();

                if (Objects.equals(formula, "")) {
                    rowEncoding.add(" ");
                } else {
                    rowEncoding.add(formula);
                }
            }
            builder.add(rowEncoding.toString());
        }
        return builder.toString();
    }
}