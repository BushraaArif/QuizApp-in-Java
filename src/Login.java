import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
	
	private JTextField usernameField;
    private JPasswordField passwordField;
    private UserService userService;

    public Login()
    {
        userService = new UserService();
        
        setTitle("Login");
        setSize(1350, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
       
        // Panel for login components
        JPanel loginPanel = new JPanel(new GridBagLayout());
        
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);  // This ensures that there is a 10-pixel space around each component.

        // Username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        
        // These properties specify the grid position of the component. 
        
        gbc.gridx = 0;  //is the column
        
        gbc.gridy = 0;  //is the row
        
        loginPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        // Password label and text field
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180)); 
        loginButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and size
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;   //span across two columns.
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.GREEN);
        registerButton.setFont(new Font("Arial", Font.BOLD, 18)); // Set font and size
        gbc.gridy = 3;
        loginPanel.add(registerButton, gbc);
        

        // Action listeners for buttons
        
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                User user = userService.loginUser(username, password); // Attempt to log in the user using the UserService

                
                if (user != null)
                {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    
                    new CategoriesPage(user).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Please try again.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new Registration().setVisible(true);
                dispose();
            }
        });

        // Panel for image
        ImagePanel imagePanel = new ImagePanel("image/login.jpg");
        
     // Set the preferred size of the image panel to be half the height of the frame
        imagePanel.setPreferredSize(new Dimension(getWidth(), getHeight() / 2));

        // Add components to the frame
        add(imagePanel, BorderLayout.NORTH); // Add the image panel to the top (north) of the frame
        add(loginPanel, BorderLayout.CENTER); // Add the login panel to the center of the frame
    }

    public static void main(String[] args) {
       
            new Login().setVisible(true);
    }
}