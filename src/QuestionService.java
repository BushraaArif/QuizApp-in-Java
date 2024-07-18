import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {
	
	public List<Question> getQuestionsByCategory(int categoryId) 
	{
	    List<Question> questions = new ArrayList<>();
	    
	    String query = "SELECT * FROM Questions WHERE CategoryID = ?";
	    
	    try (Connection conn = DatabaseConnection.getConnection();
	    		
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, categoryId);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            questions.add(new Question(
	                    rs.getInt("QuestionID"),
	                    rs.getInt("CategoryID"),
	                    rs.getString("QuestionText")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return questions;
	}

	public List<Option> getOptionsByQuestion(int questionId) {
	    List<Option> options = new ArrayList<>();
	    String query = "SELECT * FROM Option WHERE QuestionID = ?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setInt(1, questionId);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            options.add(new Option(
	                    rs.getInt("OptionID"),
	                    rs.getInt("QuestionID"),
	                    rs.getString("OptionText"),
	                    rs.getBoolean("IsCorrect")
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return options;
	}
}