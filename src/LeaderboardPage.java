import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LeaderboardPage extends JFrame {
    private LeaderboardService leaderboardService;

    public LeaderboardPage() {
        this.leaderboardService = new LeaderboardService();

        setTitle("Leaderboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1350, 700);
        setLocationRelativeTo(null);

        JPanel leaderboardPanel = new JPanel(new GridLayout(2, 1));
        leaderboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        leaderboardPanel.setBackground(new Color(207, 159, 255)); 

        // Image panel
        ImageIcon imageIcon = new ImageIcon("image/trophy.jpg");
        JLabel imageLabel = new JLabel(imageIcon);

        // Leaderboard panel
        JPanel leaderboardEntriesPanel = new JPanel(new BorderLayout());
        leaderboardEntriesPanel.setBackground(new Color(255, 255, 255));
        leaderboardEntriesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Heading for leaderboard
        JLabel leaderboardLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        leaderboardLabel.setFont(new Font("Arial", Font.BOLD, 24));
        leaderboardEntriesPanel.add(leaderboardLabel, BorderLayout.NORTH);

        // Display leaderboard data
        List<LeaderboardEntry> leaderboardEntries = leaderboardService.getLeaderboardData();

        JPanel entriesPanel = new JPanel(new GridLayout(leaderboardEntries.size() + 1, 1, 10, 10)); // +1 for heading
        entriesPanel.setBackground(new Color(255, 255, 255));

        // Create labels for the entry panel data
        JLabel positionHeadingLabel = new JLabel("Standings", SwingConstants.CENTER);
        JLabel usernameHeadingLabel = new JLabel("Player", SwingConstants.CENTER);
        JLabel scoreHeadingLabel = new JLabel("Points", SwingConstants.CENTER);

        JPanel headingPanel = new JPanel(new GridLayout(1, 3));
        headingPanel.setPreferredSize(new Dimension(300, 40)); // Set preferred size for the heading panel
        headingPanel.setBackground(new Color(195, 177, 225)); // Set background color for heading panel
        headingPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        headingPanel.add(positionHeadingLabel);
        headingPanel.add(usernameHeadingLabel);
        headingPanel.add(scoreHeadingLabel);

        entriesPanel.add(headingPanel);

        for (int i = 0; i < leaderboardEntries.size(); i++) {
            LeaderboardEntry entry = leaderboardEntries.get(i);
            JPanel entryPanel = new JPanel(new GridLayout(1, 3));
            entryPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)), // Set border color
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            entryPanel.setBackground(i % 2 == 0 ? new Color(245, 245, 245) : new Color(255, 255, 255)); // Alternate row colors

            JLabel positionLabel = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            JLabel usernameLabel = new JLabel(entry.getUsername(), SwingConstants.CENTER);
            JLabel scoreLabel = new JLabel(String.valueOf(entry.getScore()), SwingConstants.CENTER);

            entryPanel.add(positionLabel);
            entryPanel.add(usernameLabel);
            entryPanel.add(scoreLabel);

            entriesPanel.add(entryPanel);
        }

        JScrollPane scrollPane = new JScrollPane(entriesPanel);
        leaderboardEntriesPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(195, 177, 225));
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(191, 64, 191)); // Set button background color
        exitButton.setFocusPainted(false);
        exitButton.setForeground(Color.WHITE); // Set button background color
        exitButton.setFont(new Font("Arial", Font.BOLD, 24)); // Set font and size
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        buttonPanel.add(exitButton);
        exitButton.setPreferredSize(new Dimension(150, 40));
        add(buttonPanel, BorderLayout.SOUTH);

        // Add components to leaderboardPanel using GridLayout
        leaderboardPanel.add(imageLabel);
        leaderboardPanel.add(leaderboardEntriesPanel);

        add(leaderboardPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
       new LeaderboardPage();
    }
}