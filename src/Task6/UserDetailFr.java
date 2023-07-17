package Task6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserDetailFr extends JFrame {
    private Map<String, User> userMap;

    public UserDetailFr() {
        setTitle("User Details");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));

        userMap = new HashMap<>();

        // Create the main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Load user details from file and create user components
        loadUserDetails(mainPanel);

        // Load connection details from file and update user objects
        loadConnectionDetails();

        // Add the main panel to the frame
        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void loadUserDetails(JPanel mainPanel) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:/Users/krish/Downloads/_220179IndividualAssignmentDSA/src/Task7/user_details.txt"));


            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(":");
                if (userDetails.length == 6) {
                    int userId = Integer.parseInt(userDetails[0]);
                    String userName = userDetails[1];
                    String age = userDetails[2];
                    String location = userDetails[3];
                    String followers = userDetails[4];
                    String details = userDetails[5];

                    User user = new User(userId, userName, age, location, followers, details);
                    userMap.put(userName, user);
                    mainPanel.add(user.getUserButton());
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading user details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadConnectionDetails() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:/Users/krish/Downloads/_220179IndividualAssignmentDSA/src/Task7/user_details.txt"));


            String line;
            while ((line = reader.readLine()) != null) {
                String[] connectionDetails = line.split(":");
                if (connectionDetails.length == 5) {
                    String user1 = connectionDetails[0];
                    String user2 = connectionDetails[1];
                    int likes = Integer.parseInt(connectionDetails[2]);
                    int comments = Integer.parseInt(connectionDetails[3]);
                    int messages = Integer.parseInt(connectionDetails[4]);

                    User user = userMap.get(user1);
                    if (user != null) {
                        user.updateConnectionDetails(user2, likes, comments, messages);
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading connection details", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class User {
        private int id;
        private String userName;
        private String age;
        private String location;
        private String followers;
        private String details;
        private JButton userButton;
        private Map<String, ConnectionDetails> connectionMap;

        public User(int id, String userName, String age, String location, String followers, String details) {
            this.id = id;
            this.userName = userName;
            this.age = age;
            this.location = location;
            this.followers = followers;
            this.details = details;

            userButton = new JButton(userName);
            userButton.setPreferredSize(new Dimension(120, 50));
            userButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showUserDetails(User.this);
                }
            });

            connectionMap = new HashMap<>();
        }

        public int getId() {
            return id;
        }

        public JButton getUserButton() {
            return userButton;
        }

        public void updateConnectionDetails(String connectedUserName, int likes, int comments, int messages) {
            ConnectionDetails connection = connectionMap.getOrDefault(connectedUserName, new ConnectionDetails());
            connection.likes += likes;
            connection.comments += comments;
            connection.messages += messages;
            connectionMap.put(connectedUserName, connection);
        }

        public JPanel getUserPanel() {
            JPanel userPanel = new JPanel(new BorderLayout());
            userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            userPanel.add(userButton, BorderLayout.CENTER);
            return userPanel;
        }
    }

    private class ConnectionDetails {
        private int likes;
        private int comments;
        private int messages;
    }
    private void showUserDetails(User user) {
        JFrame detailsFrame = new JFrame();
        detailsFrame.setTitle(user.userName + " Details");
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.setPreferredSize(new Dimension(400, 300));

        JTextArea detailTextArea = new JTextArea();
        detailTextArea.setEditable(false);
        detailTextArea.setText(
                "Name: " + user.userName + "\n" +
                        "Age: " + user.age + "\n" +
                        "Location: " + user.location + "\n" +
                        "Followers: " + user.followers + "\n" +
                        "Details: " + user.details
        );

        JScrollPane scrollPane = new JScrollPane(detailTextArea);
        detailsFrame.getContentPane().add(scrollPane);

        detailsFrame.pack();
        detailsFrame.setLocationRelativeTo(null);
        detailsFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserDetailFr frame = new UserDetailFr();
            frame.setVisible(true);
        });
    }
}
