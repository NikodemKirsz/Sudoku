package pl.comp.model;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.comp.exceptions.DatabaseException;
import pl.comp.exceptions.NotInitializedException;
import pl.comp.exceptions.OutOfDatabaseException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    private static final int savedBoardsCount = 5;
    private static final int sudokuSize = 9;
    private static final String dbPath = FilesManager.DATABASE_PATH;
    private static final String connectionUrl = "jdbc:sqlite:" + dbPath;
    private static final Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("bundle");
    private static boolean initialized = false;

    private final String boardType;

    public JdbcSudokuBoardDao(final BoardType boardType) {
        this.boardType = boardType.getBoardType();
    }

    @Override
    public SudokuBoard read(int index) {
        if (indexOutOfRange(index)) {
            throw new OutOfDatabaseException();
        }
        if (!initialized) {
            throw new NotInitializedException();
        }

        var sudokuBoard = new SudokuBoard();

        String query = "SELECT board_values FROM SudokuValues WHERE id_board = ? AND board_type = ?;";

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            var preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, index);
            preparedStatement.setString(2, this.boardType);

            ResultSet rs = preparedStatement.executeQuery();
            String boardFields = rs.getString("board_values");

            for (int i = 0; i < 81; i++) {
                sudokuBoard.set(i / 9, i % 9,
                        Character.getNumericValue(boardFields.charAt(i)));
            }

            logger.info("SudokuBoard read.");

        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
        }

        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj, int index) {
        if (indexOutOfRange(index)) {
            throw new OutOfDatabaseException();
        }
        if (!initialized) {
            throw new NotInitializedException();
        }

        String sudokuBoards = "UPDATE SudokuBoards SET name = ? WHERE id_board = ?;";
        String values = "UPDATE SudokuValues SET board_values = ? WHERE id_board = ? AND board_type = ?;";

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            var pstmtSudokuBoards = conn.prepareStatement(sudokuBoards);
            pstmtSudokuBoards.setString(1, "SB(" + LocalDateTime.now() + ")");
            pstmtSudokuBoards.setInt(2, index);
            pstmtSudokuBoards.executeUpdate();

            var boardFields = new StringBuilder(81);
            for (int i = 0; i < 81; i++) {
                boardFields.append(obj.getField(i / 9, i % 9).getFieldValue());
            }

            var pstmtSudokuValues = conn.prepareStatement(values);
            pstmtSudokuValues.setString(1, boardFields.toString());
            pstmtSudokuValues.setInt(2, index);
            pstmtSudokuValues.setString(3, this.boardType);
            pstmtSudokuValues.executeUpdate();

            logger.info("Record modified.");

        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
        }

    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        ensureSudokuBoardsTableValidity();
        ensureSudokuValuesTableValidity();
        initialized = true;
    }

    public boolean isRecordEmpty(int index) {
        try {
            return getName(index).equals("empty");
        } catch (DatabaseException exception) {
            logger.error(exception.getLocalizedMessage());
        }
        return false;
    }

    private String getName(int index) {
        if (indexOutOfRange(index)) {
            throw new OutOfDatabaseException();
        }

        String queues = """ 
                            SELECT *
                            FROM SudokuBoards
                            WHERE id_board = ?;
                        """;

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement pstmt = conn.prepareStatement(queues);
            pstmt.setInt(1, index);
            ResultSet rs = pstmt.executeQuery();

            return rs.getString("name");
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
        }

        return null;
    }

    private static void ensureSudokuValuesTableValidity() {

        if (!checkSudokuValuesTableExists()) {
            logger.info("Table deos not exist!\n"
                    + "Creating new table and filling with defaults");
            createSudokuValuesTable();
            fillSudokuValuesTableWithDefaults();
        }
    }

    private static void ensureSudokuBoardsTableValidity() {

        if (!checkSudokuBoardsTableExists()) {
            logger.info("Table deos not exist!\n"
                    + "Creating new table and filling with defaults");
            createSudokuBoardsTable();
            fillSudokuBoardsTableWithDefaults();
        } else {
            if (checkSudokuBoardsTableValidity()) {
                logger.info("Table exists and is valid");
            } else {
                logger.info("Table exists but is not valid!\n"
                        + "Creating new table and filling with defaults");
                dropSudokuBoardsTable();
                createSudokuBoardsTable();
            }
            fillSudokuBoardsTableWithDefaults();
        }
    }

    private static boolean checkSudokuBoardsTableValidity() {
        String sudokuBoards = "SELECT * FROM SudokuBoards";

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sudokuBoards);

            int count = 0;
            while (rs.next()) {
                int index = rs.getInt("id_board");
                if (indexOutOfRange(index)) {
                    return false;
                }
                count++;
            }
            if (count != 5) {
                return false;
            }
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
            return false;
        }
        return true;
    }

    private static boolean checkSudokuBoardsTableExists() {
        String sudokuBoards = "SELECT * FROM SudokuBoards;";

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            Statement statement = conn.createStatement();
            statement.executeQuery(sudokuBoards);
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
            return false;
        }
        return true;
    }

    private static boolean checkSudokuValuesTableExists() {
        String values = "SELECT * FROM SudokuValues;";

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            Statement statement = conn.createStatement();
            statement.executeQuery(values);
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
            return false;
        }
        return true;
    }

    private static void fillSudokuBoardsTableWithDefaults() {
        String sudokuBoards = """
                INSERT or REPLACE INTO SudokuBoards VALUES
                    (0, "empty"),
                    (1, "empty"),
                    (2, "empty"),
                    (3, "empty"),
                    (4, "empty");
                """;

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            Statement sbStatement = conn.createStatement();
            sbStatement.execute(sudokuBoards);
            logger.info("Filled SudokuBoards Table");
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
        }
    }

    private static void fillSudokuValuesTableWithDefaults() {
        String currentBoardType = BoardType.CURRENT.getBoardType();
        String originalBoardType = BoardType.ORIGINAL.getBoardType();

        var sb = new StringBuilder("INSERT INTO SudokuValues VALUES\n");

        for (int i = 0; i < savedBoardsCount; i++) {
            sb.append("(").append(i).append(", '").append(originalBoardType).append("', 0),\n");
            sb.append("(").append(i).append(", '").append(currentBoardType).append("', 0)");
            if (i < savedBoardsCount - 1) {
                sb.append(",\n");
            }
            else {
                sb.append(";");
            }
        }

        var queues = sb.toString();
        System.out.println(queues);

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            var valueStatement = conn.createStatement();
            valueStatement.execute(queues);
            logger.info("Filled SudokuValues Table");

        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
        }
    }

    private static void createSudokuBoardsTable() {
        String sudokuBoards = """
                CREATE TABLE SudokuBoards (
                     id_board integer PRIMARY KEY,
                     name text NOT NULL
                );
                """;

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            Statement statement = conn.createStatement();
            statement.execute(sudokuBoards);
            logger.info("SudokuBoards table created");
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
        }
    }

    private static void createSudokuValuesTable() {
        String values = """
                CREATE TABLE SudokuValues (
                     id_board integer NOT NULL,
                     board_type text NOT NULL,
                     board_values text NOT NULL,
                     
                     CONSTRAINT board_FK FOREIGN KEY (id_board) REFERENCES SudokuBoards(id_board)
                );
                """;

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            Statement statement = conn.createStatement();
            statement.execute(values);
            logger.info("SudokuValues table created");
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
        }
    }

    private static void dropSudokuValuesTable() {
        String values = """
                DROP TABLE SudokuValues
                """;

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            Statement statement = conn.createStatement();
            statement.execute(values);
            logger.info("SudokuValues table Dropped");
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
        }
    }

    private static void dropSudokuBoardsTable() {
        String sudokuBoards =
                """
                DROP TABLE SudokuBoards
                """;

        try (var conn = DriverManager.getConnection(connectionUrl)) {
            Statement statement = conn.createStatement();
            statement.execute(sudokuBoards);
            logger.info("SudokuBoard table Dropped");
        } catch (SQLException exception) {
            var databaseException = new DatabaseException(
                    resourceBundle.getString("DatabaseFail"), exception);
            logger.error(databaseException + resourceBundle.getString(
                    "cause"), databaseException.getCause()
            );
        }
    }

    private static boolean indexOutOfRange(int index) {
        return (index < 0 || index >= savedBoardsCount);
    }

    private static String concat(int... ints) {
        StringBuilder sb = new StringBuilder();

        for (int val : ints) {
            sb.append(val);
        }

        return sb.toString();
    }

    public enum BoardType {
        ORIGINAL("original"),
        CURRENT("current");

        private final String boardType;

        BoardType(String boardType) {
            this.boardType = boardType;
        }

        public String getBoardType() {
            return this.boardType;
        }
    }
}
