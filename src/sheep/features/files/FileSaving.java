package sheep.features.files;

import sheep.features.Feature;
import sheep.sheets.Sheet;
import sheep.ui.Perform;
import sheep.ui.Prompt;
import sheep.ui.Tick;
import sheep.ui.UI;

public class FileSaving implements Feature {

    private final Sheet sheet;

    public FileSaving(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public void register(UI ui) {
        // Register a callback function for the "Save" feature
        ui.addFeature("save-file", "Save As", new Save(this.sheet));
    }

}