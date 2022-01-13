package pl.comp.model;

import java.io.File;
import java.nio.file.Paths;

public class FilesManager {
    private static final String SUDOKU_BOARD_NAME= "saved_board";
    private static final String SUDOKU_BOARD_ORIGINAL_NAME = "saved_board_original";

    public static final String FILES_PATH =  Paths.get("files").toString();
    public static final String SUDOKU_BOARD_PATH = getPath(SUDOKU_BOARD_NAME);
    public static final String SUDOKU_BOARD_ORIGINAL_PATH = getPath(SUDOKU_BOARD_ORIGINAL_NAME);

    private FilesManager() {
    }

    public static String getPath(final String fileName) {
        return Paths.get(FILES_PATH, fileName).toString();
    }

    public static boolean ensureFilesDirExists() {
        var filesDirectory = new File(FILES_PATH);

        if (filesDirectory.exists()) {
            return true;
        }
        else {
            return filesDirectory.mkdir();
        }
    }
}
