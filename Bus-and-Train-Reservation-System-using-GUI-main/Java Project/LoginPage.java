import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginPage extends JFrame {

    // Declare Swing controls
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    // Database connection properties
    private static final String DB_URL = "jdbc:mysql://localhost:3306/arun";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "*********";

    // Constructor
    public LoginPage() {
        // Set the title of the JFrame
        super("Reservation System");

        // Create and set the layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
       
        // Create and add the controls for login
        usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(usernameField, gbc);

        passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        // Add an action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check if the username and password are valid
                if (validateUser(username, password)) {
                    performLoginActions();
                } else {
                    // Show an error message if the login is invalid
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });

        // Add an action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check if the username and password are empty
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both username and password");
                } else {
                    // Attempt to register the user
                    if (registerUser(username, password)) {
                        JOptionPane.showMessageDialog(null, "Registration successful. You can now login.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error registering user");
                    }
                }
            }
        });

        // Set the size, location, and close operation of the JFrame
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method to perform login actions
    private void performLoginActions() {
        // Remove the login controls
        remove(usernameLabel);
        remove(usernameField);
        remove(passwordLabel);
        remove(passwordField);
        remove(loginButton);
        remove(registerButton);

        // Add the reservation buttons
        JButton busButton = new JButton("Bus");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(busButton, gbc);
        busButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform bus reservation logic
                File file = new File("BusReservationSystemGUI.java");
                // Create a process to execute the file
                ProcessBuilder pb = new ProcessBuilder("javac", file.getName());
                pb.directory(file.getParentFile());
                try {
                    Process p = pb.start();
                    p.waitFor();
                    pb = new ProcessBuilder("java", file.getName().replace(".java", ""));
                    pb.directory(file.getParentFile());
                    pb.redirectErrorStream(true);
                    p = pb.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error executing file: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        JButton trainButton = new JButton("Train");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(trainButton, gbc);
        trainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform train reservation logic
                File file = new File("TrainReservationSystem.java");
                // Create a process to execute the file
                ProcessBuilder pb = new ProcessBuilder("javac", file.getName());
                pb.directory(file.getParentFile());
                try {
                    Process p = pb.start();
                    p.waitFor();
                    pb = new ProcessBuilder("java", file.getName().replace(".java", ""));
                    pb.directory(file.getParentFile());
                    pb.redirectErrorStream(true);
                    p = pb.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error executing file: " + ex.getMessage());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Repaint the JFrame to reflect the changes
        revalidate();
        repaint();
    }

    // Method to validate user credentials against the database
    private boolean validateUser(String username, String password) {
        try {
            // Create a database connection
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Create a prepared statement to check if the user exists
            String query = "SELECT * FROM Project WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if the user exists
            if (resultSet.next()) {
                // User exists
                return true;
            } else {
                // User does not exist
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to register a new user in the database
    private boolean registerUser(String username, String password) {
        try {
            // Create a database connection
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Create a prepared statement to insert the user
            String query = "INSERT INTO Project (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            int rowsAffected = statement.executeUpdate();

            // Check if the registration was successful
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Main method to create and show the JFrame
    public static void main(String[] args) {
        LoginPage gui = new LoginPage();
        gui.setVisible(true);
    }
}
