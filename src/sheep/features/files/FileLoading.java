package sheep.features.files;

import sheep.features.Feature;
import sheep.sheets.Sheet;
import sheep.ui.Perform;
import sheep.ui.UI;

import java.io.File;

/**
 * The FileLoading class provides functionality for loading an existing file.
 * It allows users to open a file and load its contents into a sheet.
 */
public class FileLoading implements Feature {

    /** The sheet where the contents of the file will be loaded */
    private final Sheet sheet;

    /**
     * Constructs a new FileLoading object with the specified sheet.
     *
     * @param sheet The sheet where the contents of the file will be loaded.
     */
    public FileLoading(Sheet sheet) {
        this.sheet = sheet;
    }

    /**
     * Registers the file loading feature with the user interface.
     * This adds a callback function for opening a file.
     *
     * @param ui The user interface where the feature will be registered.
     */
    @Override
    public void register(UI ui) {
        // Register a callback function for the "Open file" feature
        ui.addFeature("load-file", "Open file", new Load(this.sheet));
    }
}

