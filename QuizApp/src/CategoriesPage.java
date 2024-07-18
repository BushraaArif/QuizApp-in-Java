import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoriesPage extends JFrame {
    private User user;
    private CategoryService categoryService;

    public CategoriesPage(User user) {
        this.user = user;
        this.categoryService = new CategoryService();

        setTitle("Select Category");
        setSize(1400, 700);
        setLocationRelativeTo(null); // Set location to center of screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a label for the heading
        JLabel headingLabel = new JLabel("Select category for Quiz");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headingLabel, BorderLayout.NORTH); // Add the label to the top of the frame

        JPanel categoryPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Category> categories = categoryService.getAllCategories();
        for (Category category : categories) {
            JButton categoryButton = new JButton(category.getCategoryName());
            categoryButton.setPreferredSize(new Dimension(150, 30)); // Set preferred button size
            categoryButton.setBackground(new Color(70, 130, 180)); // Set background color to blue
            categoryButton.setForeground(Color.BLACK); // Set text color to white
            categoryButton.setFont(new Font("Arial", Font.BOLD, 24)); // Set font and size
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new QuizPage(user, category).setVisible(true);
                    dispose();
                }
            });
            categoryPanel.add(categoryButton);
        }

      

        add(categoryPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CategoriesPage(new User(0, "test", "test", "test@test.com", "user")).setVisible(true);
        });
    }
}
