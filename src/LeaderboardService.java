import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardService {
	public List<LeaderboardEntry> getLeaderboardData() {
        List<LeaderboardEntry> leaderboardData = new ArrayList<>();
        String query = "SELECT u.UserName, SUM(r.Score) AS TotalScore " +
                       "FROM Result r JOIN User u ON r.UserID = u.UserID " +
                       "GROUP BY u.UserID " +
                       "ORDER BY TotalScore DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("UserName");
                int score = rs.getInt("TotalScore");
                LeaderboardEntry entry = new LeaderboardEntry(username, score);
                leaderboardData.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboardData;   
    }
}
