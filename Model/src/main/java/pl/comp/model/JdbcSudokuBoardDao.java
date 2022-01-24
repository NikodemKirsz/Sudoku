package pl.comp.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import org.javatuples.Pair;

import java.sql.*;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>{

    final String DB_NAME = "sudoku_boards.db";
    final String DB_PATH = FilesManager.getPath(DB_NAME);
    final String CONNECTION_URL = "jdbc:sqlite:" + DB_PATH;

    private static final Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);

    private boolean initialized = false;

    @Override
    public SudokuBoard read() {
        Initialize();
        this.selectAll();
        return null;
    }

    public Pair<SudokuBoard, SudokuBoard> readBoth(int index) {
        var board = new SudokuBoard();
        var originalBoard = new SudokuBoard();

        String queues = "SELECT board, originalBoard FROM Boards WHERE id = "
                + index + ";";

        try (Connection conn = DriverManager.getConnection(CONNECTION_URL)) {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(queues);

            String boardFields = rs.getString("board");
            String originalBoardFields = rs.getString("originalBoard");
            for (int i = 0; i < 81; i++) {
                board.set(i / 9, i % 9,
                        Character.getNumericValue(boardFields.charAt(i)));
                originalBoard.set(i / 9, i % 9,
                        Character.getNumericValue(originalBoardFields.charAt(i)));
            }

            logger.info("SudokuBoards loaded.");

        } catch (SQLException exception) {
            logger.error(exception.getLocalizedMessage());
        }

        return Pair.with(board, originalBoard);
    }

    @Override
    public void write(SudokuBoard obj) {
        String queues = "INSERT INTO Boards(name, board) VALUES(?,?)";

        var boardFields = new StringBuilder(81);

        for (int i = 0; i < 81; i++) {
            boardFields.append(obj.getField(i / 9, i % 9).getFieldValue());
        }

        try (var conn = DriverManager.getConnection(CONNECTION_URL)) {
            PreparedStatement pstmt = conn.prepareStatement(queues);
            pstmt.setString(1, "SudokuBoard(" + LocalDateTime.now() + ")");
            pstmt.setString(2, boardFields.toString());
            pstmt.executeUpdate();
            logger.info("Record added.");

        } catch (SQLException exception) {
            logger.error(exception.getLocalizedMessage());
        }
    }

    public void write(SudokuBoard modified, SudokuBoard original) {
        String queues = "INSERT INTO Boards(name, board, originalBoard) VALUES(?,?,?)";

        var boardFields = new StringBuilder(81);
        var originalBoardFields = new StringBuilder(81);

        for (int i = 0; i < 81; i++) {
            boardFields.append(modified.getField(i / 9, i % 9).getFieldValue());
            originalBoardFields.append(original.getField(i / 9, i % 9).getFieldValue());
        }

        try (var conn = DriverManager.getConnection(CONNECTION_URL)) {
            PreparedStatement pstmt = conn.prepareStatement(queues);
            pstmt.setString(1, "SudokuBoard(" + LocalDateTime.now() + ")");
            pstmt.setString(2, boardFields.toString());
            pstmt.setString(3, originalBoardFields.toString());
            pstmt.executeUpdate();
            logger.info("Record added.");

        } catch (SQLException exception) {
            logger.error(exception.getLocalizedMessage());
        }
    }

    public void updateBoard(int index, SudokuBoard modified, SudokuBoard original) {
        String queues = "UPDATE Boards SET board = ?, originalBoard = ? WHERE id = ?";

        var boardFields = new StringBuilder(81);
        var originalBoardFields = new StringBuilder(81);

        for (int i = 0; i < 81; i++) {
            boardFields.append(modified.getField(i / 9, i % 9).getFieldValue());
            originalBoardFields.append(original.getField(i / 9, i % 9).getFieldValue());
        }

        try (var conn = DriverManager.getConnection(CONNECTION_URL)) {
            PreparedStatement pstmt = conn.prepareStatement(queues);
            pstmt.setString(1, boardFields.toString());
            pstmt.setString(2, originalBoardFields.toString());
            pstmt.setInt(3, index);
            pstmt.executeUpdate();
            logger.info("Record modified.");

        } catch (SQLException exception) {
            logger.error(exception.getLocalizedMessage());
        }
    }

    public void selectAll(){
        String queues = "SELECT * FROM Boards";

        try (var conn = DriverManager.getConnection(CONNECTION_URL)){
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(queues);

            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("board"));
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    private void Initialize() {
        if (initialized) return;
        this.ensureTableExists();
        initialized = true;
    }

    private void ensureTableExists() {
        String queues = """
                CREATE TABLE IF NOT EXISTS Boards (
                     id integer PRIMARY KEY,
                     name text NOT NULL,
                     board text NOT NULL,
                     originalBoard text NOT NULL
                );""";

        try (var conn = DriverManager.getConnection(CONNECTION_URL)) {
            Statement statement = conn.createStatement();
            statement.execute(queues);
            logger.info("Table ensured.");

        } catch (SQLException exception) {
            logger.error(exception.getLocalizedMessage());
        }
    }
}





