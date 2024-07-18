import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Registration extends JFrame {
    private UserService userService;

    public Registration() {
        userService = new UserService();

        setTitle("Register");
        setSize(1400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Image panel
        ImagePanel imagePanel = new ImagePanel("image/register.jpg");
        imagePanel.setPreferredSize(new Dimension(getWidth(), (int) (getHeight() * 0.6)));

        // Registration form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);

        // Username field
        JLabel usernameLabel = new JLabel(" Enter Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);
        
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        Dimension size = new Dimension(200, 30); // width: 200, height: 30
        usernameField.setPreferredSize(size);
        usernameField.setMinimumSize(size);
        usernameField.setMaximumSize(size);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Set Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridy = 1;
        gbc.gridx = 0;
        formPanel.add(passwordLabel, gbc);
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setBackground(new Color(34, 139, 34));
        registerButton.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(registerButton, gbc);

        // Adding action listener to register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (register(username, password)) {
                    JOptionPane.showMessageDialog(null, "Registration successful!");
                    new Login();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Registration failed. Username may already exist.");
                }
            }
        });

        // Place image panel at the top, form panel at the bottom
        add(imagePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private boolean register(String username, String password) {
        return userService.registerUser(new User(0, username, password));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Registration::new);
    }
}
