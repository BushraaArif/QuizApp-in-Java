import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultPage extends JFrame {
    private User user;
    private Category category;
    private int score;
    private int totalQuestions;
    private ResultService resultService;

    public ResultPage(User user, Category category, int score, int totalQuestions) {
        this.user = user;
        this.category = category;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.resultService = new ResultService();

        setTitle("Quiz Application - Results");
        setSize(1400, 700); // Set the size to match the Login page
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel resultPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Heading for quiz results
        JLabel resultLabel = new JLabel("Quiz Results");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultLabel.setForeground(Color.BLUE); // Set heading color
        resultPanel.add(resultLabel);

        // Display score
        JLabel scoreLabel = new JLabel("Score: " + score + "/" + totalQuestions);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        resultPanel.add(scoreLabel);

        // Emoji based on score
        JLabel emojiLabel = new JLabel();
        emojiLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80)); // Set font and size for emoji
        if (score > 3) {
            emojiLabel.setText("\uD83D\uDE00"); // Grinning face emoji
        } else {
            emojiLabel.setText("\uD83D\uDE41"); // Confused face emoji
        }
        resultPanel.add(emojiLabel);

        // Back to Categories button
        JButton backButton = new JButton("Back to Categories");
        backButton.setBackground(Color.MAGENTA); // Set button background color
        backButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and size
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CategoriesPage(user).setVisible(true);
                dispose();
            }
        });
        resultPanel.add(backButton);

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.pink); // Set button background color
        exitButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and size
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        resultPanel.add(exitButton);

        add(resultPanel, BorderLayout.CENTER);

        // Save the result to the database
        Result result = new Result(user.getUserId(), category.getCategoryId(), score);
        resultService.saveResult(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ResultPage(new User(0, "test", "test", "test@test.com", "user"), new Category(1, "Sample Category"), 5, 10).setVisible(true);
        });
    }
}
