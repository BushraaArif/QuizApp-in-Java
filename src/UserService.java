import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class UserService {
	
	
    public boolean registerUser(User user) {
    	
        String query = "INSERT INTO User (UserName, Password) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
        		
             PreparedStatement stmt = conn.prepareStatement(query)) 
        {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;                                        //succesfully insert
        }
        catch (SQLException e) {
            e.printStackTrace();                               //Trace error
            return false;
        }
    }

    public User loginUser(String username, String password)
    {
        String query = "SELECT * FROM User WHERE UserName = ? AND Password = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
        		
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
         
            if (rs.next())      // Checking if a user was found
            {
                
                return new User(    // Creating a new User object with the retrieved data
                		
                        rs.getInt("UserID"), // Get the user ID from the result set
                        rs.getString("UserName"),
                        rs.getString("Password")
                        
                );
            } 
            else {
                return null; // User not found or invalid credentials
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}