import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoriesPage extends JFrame {
   
    private User user; // Declare a variable to hold user information
    private CategoryService categoryService; // Declare a variable for category service to interact with categories

    public CategoriesPage(User user) 
    {
        this.user = user;
        this.categoryService = new CategoryService();

        setTitle("Category");
        setSize(1350, 700);
        
        setLocationRelativeTo(null); // Set location to center of screen
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        setLayout(new BorderLayout());

        // Create a background panel with a solid color
        JPanel backgroundPanel = new JPanel() ;
        backgroundPanel.setBackground(new Color(44, 62, 80)); // Set background color
        
        backgroundPanel.setLayout(new BorderLayout());
           
        add(backgroundPanel);

        // Create a label for the heading
        JLabel headingLabel = new JLabel("Select Category for Quiz");
        
        headingLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Increase font size for better visibility
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headingLabel.setForeground(Color.WHITE); // Set text color to white
        headingLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add padding to the label
        
        backgroundPanel.add(headingLabel, BorderLayout.NORTH); // Add the label to the top of the frame

        JPanel categoryPanel = new JPanel(new GridLayout(0, 2, 20, 20)); // Increase spacing between buttons
        
        categoryPanel.setOpaque(false); // Make the panel transparent
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Category> categories = categoryService.getAllCategories();// Get all categories from the service
        
        for (Category category : categories)
        {
            JButton categoryButton = createRoundedButton(category.getCategoryName());

            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new QuizPage(user, category).setVisible(true);
                    dispose();
                }
            });
            categoryPanel.add(categoryButton); // Add the button to the categoryPanel
        }

        backgroundPanel.add(categoryPanel, BorderLayout.CENTER);

        
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(100, 40));
 
        // Set background color
        button.setBackground(new Color(41, 128, 185));

        // Set rounded border
        int borderRadius = 20; 
        
        button.setBorder(BorderFactory.createEmptyBorder(10, borderRadius, 10, borderRadius));

        return button;
    }

    public static void main(String[] args) {
       {
            new CategoriesPage(new User(0, "test", "test")).setVisible(true);
        };
    }
}