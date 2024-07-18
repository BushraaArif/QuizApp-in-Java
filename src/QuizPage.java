import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//Class declaration
public class QuizPage extends JFrame 
{
 // Instance variables
 private User user;                                 // The current user
 private Category category;                        // The quiz category
 private QuestionService questionService;          // Service to handle questions
 private ResultService resultService;              // Service to handle results
 private List<Question> questions;                // List of questions
 private int currentQuestionIndex = 0;            // Index of the current question
 private int score = 0;                          // Score of the user  
 private JLabel timerLabel;                      // Label to display the timer
 private Timer timer;                            // Timer to track time
 private int timeRemaining;                     // Time remaining for each question

    private JLabel questionLabel;
    private JToggleButton[] optionButtons;
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
        setSize(1350, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(150, 222, 209));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();


        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new GridBagLayout());
        quizPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        quizPanel.setBackground(new Color(64, 224, 208));
        quizPanel.setOpaque(true);
        quizPanel.setPreferredSize(new Dimension(600, 400)); // Set the size of the quiz panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 20, 20, 20);
        mainPanel.add(quizPanel, gbc);
        
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        //gbc.gridx = 0;
        //gbc.gridy = 0;
        //gbc.gridwidth = 2;
        quizPanel.add(questionLabel, gbc);

     // Options panel setup
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Create options panel
        
        optionsPanel.setOpaque(false); // Set panel opaque
        
        optionButtons = new JToggleButton[4]; // Create array for option buttons
        optionsGroup = new ButtonGroup(); // Create button group 

        for (int i = 0; i < 4; i++) { // Loop through each option
        	
            optionButtons[i] = createOptionButton(); // Create option button
           optionsGroup.add(optionButtons[i]); // Add button to button group
            optionsPanel.add(optionButtons[i]); // Add button to options panel
        }
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        
        quizPanel.add(optionsPanel, gbc);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        timerLabel = new JLabel();
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(timerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        prevButton = createStyledButton("Previous");
        prevButton.setPreferredSize(new Dimension(150, 50)); // Set the size of the Previous button
        prevButton.setMinimumSize(new Dimension(150, 50)); // Set the minimum size of the Previous button
        prevButton.setMaximumSize(new Dimension(150, 50)); // Set the maximum size of the Previous button
        
            
        buttonPanel.add(prevButton);

        nextButton = createStyledButton("Next");
        nextButton.setPreferredSize(new Dimension(150, 50)); // Set the size of the Next button
        nextButton.setMinimumSize(new Dimension(150, 50)); // Set the minimum size of the Next button
        nextButton.setMaximumSize(new Dimension(150, 50)); // Set the maximum size of the Next button
        nextButton.setEnabled(false);
        
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                } else {
                    showResults();
            }
            } 
            });
        buttonPanel.add(nextButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(bottomPanel, gbc);

        startTimer();
        add(mainPanel, BorderLayout.CENTER);

        displayQuestion();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(240, 255, 255));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(64, 224, 208), 2, true)); // Rounded border
        return button;
    }

    private JToggleButton createOptionButton() {
        JToggleButton button = new JToggleButton();
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(240, 255, 255));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(new Color(64, 224, 208), 2, true)); // Rounded border
        button.setPreferredSize(new Dimension(500, 50)); // Set the size of the option buttons
        button.setMinimumSize(new Dimension(500, 50)); // Set the minimum size of the option buttons
        button.setMaximumSize(new Dimension(500, 50));    // Set the maximum size of the option buttons
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer((JToggleButton) e.getSource());
            }
        });

        return button;
    }

    private void displayQuestion() {
        if (currentQuestionIndex < 0 || currentQuestionIndex >= questions.size()) {
            return;
        }

        Question question = questions.get(currentQuestionIndex);
        
        questionLabel.setText("Question " + (currentQuestionIndex + 1) + ": " + question.getQuestionText());
        
        questionLabel.setForeground(Color.BLACK); // Set text color

        List<Option> options = questionService.getOptionsByQuestion(question.getQuestionId());
        
        for (int i = 0; i < options.size(); i++)
        {
            optionButtons[i].setText(options.get(i).getOptionText());
            optionButtons[i].setActionCommand(Boolean.toString(options.get(i).isCorrect()));
            optionButtons[i].setSelected(false); // Clear previous selection
            optionButtons[i].setBackground(new Color(240, 255, 255)); // Reset color
            optionButtons[i].setEnabled(true); // Enable button
            optionButtons[i].setForeground(Color.BLACK);
        }

        optionsGroup.clearSelection();
        nextButton.setEnabled(false);
        resetTimer();
    }

    private void checkAnswer(JToggleButton selectedButton) 
    {
        if (selectedButton != null) {
            boolean isCorrect = Boolean.parseBoolean(selectedButton.getActionCommand());
            if (isCorrect)
            {
                selectedButton.setBackground(Color.GREEN);
                score++;
            } else {
                selectedButton.setBackground(Color.RED);
            }

            for (JToggleButton button : optionButtons) {
                button.setEnabled(false);
            }
            nextButton.setEnabled(true);
        }
    }

    private void showResults() {
        timer.stop();
        new ResultPage(user, category, score, questions.size()).setVisible(true);
        dispose();
    }

    private void startTimer() {
        timeRemaining = 10;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                if (timeRemaining >= 0) {
                    updateTimerLabel();
                } else {
                    timer.stop();
                    checkAnswer(null);
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.size()) {
                        displayQuestion();
                    } else {
                        showResults();
                }
            }
            }});
        timer.start();
        timerLabel.setForeground(Color.BLACK); // Set timer text color
    }

    private void resetTimer() {
        timeRemaining = 10;
        timer.restart();
        updateTimerLabel();
    }

    private void updateTimerLabel() 
    {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));
    }

    public static void main(String[] args) {
        new QuizPage(new User(0, "test", "test"), new Category(1, "Sample Category")).setVisible(true);
    }
}