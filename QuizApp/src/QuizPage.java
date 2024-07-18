import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuizPage extends JFrame {
    private User user;
    private Category category;
    private QuestionService questionService;
    private ResultService resultService;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private JLabel timerLabel;
    private Timer timer;
    private int timeRemaining;

    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JButton nextButton;
    private JButton prevButton;

    public QuizPage(User user, Category category) {
        this.user = user;
        this.category = category;
        this.questionService = new QuestionService();
        this.resultService = new ResultService();
        this.questions = questionService.getQuestionsByCategory(category.getCategoryId());

        setTitle("Quiz Application - Quiz");
        setSize(1400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel quizPanel = new JPanel(new BorderLayout());
        quizPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel for question label and timer
        JPanel topPanel = new JPanel(new BorderLayout());

        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(questionLabel, BorderLayout.WEST);

        timerLabel = new JLabel();
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(timerLabel, BorderLayout.EAST);

        quizPanel.add(topPanel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        quizPanel.add(optionsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prevButton = new JButton("Previous");
        prevButton.setBackground(new Color(135, 206, 250)); // Blue background
        prevButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and size
        prevButton.setForeground(Color.WHITE); // White text color
        prevButton.addActionListener(e -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                displayQuestion();
            }
        });
        // Add hover effect
        prevButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                prevButton.setBackground(new Color(70, 130, 180)); // Darker blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                prevButton.setBackground(new Color(135, 206, 250)); // Blue background
            }
        });
        buttonPanel.add(prevButton);

        nextButton = new JButton("Next");
        nextButton.setEnabled(false);
        nextButton.setBackground(new Color(135, 206, 250)); // Blue background
        nextButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and size
        nextButton.setForeground(Color.WHITE); // White text color
        nextButton.addActionListener(e -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayQuestion();
            } else {
                showResults();
            }
        });
        // Add hover effect
        nextButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(70, 130, 180)); // Darker blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                nextButton.setBackground(new Color(135, 206, 250)); // Blue background
            }
        });
        buttonPanel.add(nextButton);

        quizPanel.add(buttonPanel, BorderLayout.SOUTH);

        startTimer();

        ImagePanel imagePanel = new ImagePanel("background.jpg");
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(quizPanel, BorderLayout.CENTER);

        add(imagePanel, BorderLayout.CENTER);

        displayQuestion();

        // Add ActionListener for option buttons using lambda expressions
        for (JRadioButton optionButton : optionButtons) {
            optionButton.addActionListener(e -> {
                JRadioButton selectedButton = (JRadioButton) e.getSource();
                checkAnswer(selectedButton);
            });
        }
    }

    private void displayQuestion() {
        if (currentQuestionIndex < 0 || currentQuestionIndex >= questions.size()) {
            return;
        }

        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText(String.format("Question: %02d", currentQuestionIndex + 1) + " - " + question.getQuestionText());

        List<Option> options = questionService.getOptionsByQuestion(question.getQuestionId());
        for (int i = 0; i < options.size(); i++) {
            optionButtons[i].setText(options.get(i).getOptionText());
            optionButtons[i].setActionCommand(Boolean.toString(options.get(i).isCorrect()));
            optionButtons[i].setIcon(UIManager.getIcon("RadioButton.icon"));
            optionButtons[i].setEnabled(true);
        }
        optionsGroup.clearSelection();
        nextButton.setEnabled(false);
        resetTimer();
    }

    private void checkAnswer(JRadioButton selectedButton) {
        if (selectedButton != null) {
            boolean isCorrect = Boolean.parseBoolean(selectedButton.getActionCommand());
            if (isCorrect) {
                selectedButton.setIcon(new ColoredRadioButtonIcon(Color.GREEN));
                score++;
            } else {
                selectedButton.setIcon(new ColoredRadioButtonIcon(Color.RED));
            }

            // Disable all option buttons after selection
            for (JRadioButton button : optionButtons) {
                button.setEnabled(false);
            }

            // Enable the next button
            nextButton.setEnabled(true);
        }
    }

    private void showResults() {
        // Stop the timer when showing results
        stopTimer();
        new ResultPage(user, category, score, questions.size()).setVisible(true);
        dispose();
    }

    private void startTimer() {
        timeRemaining = 10; // Set initial time for each question (in seconds)
        timer = new Timer(1000, e -> {
            timeRemaining--;
            if (timeRemaining >= 0) {
                updateTimerLabel();
            } else {
                // If time runs out, automatically check answer and move to next question
                JRadioButton selectedButton = getSelectedButton();
                checkAnswer(selectedButton);
            }
        });
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void resetTimer() {
        timeRemaining = 10; // Reset the time for each question
        updateTimerLabel();
        stopTimer();
        startTimer(); // Restart the timer for the next question
    }

    private void updateTimerLabel() {
        int minutes        = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));
    }

    private JRadioButton getSelectedButton() {
        for (JRadioButton button : optionButtons) {
            if (button.isSelected()) {
                return button;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the look and feel to Nimbus
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
            new QuizPage(new User(0, "test", "test", "test@test.com", "user"), new Category(1, "Sample Category")).setVisible(true);
        });
    }
}

