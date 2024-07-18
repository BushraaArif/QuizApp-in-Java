import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultService {
    public boolean saveResult(Result result) {
        String query = "INSERT INTO Result (UserID, CategoryID, Score) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, result.getUserId());
            stmt.setInt(2, result.getCategoryId());
            stmt.setInt(3, result.getScore());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
