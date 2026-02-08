import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CollegeStaffAttendanceSystem extends JFrame implements ActionListener {

    private JTextField nameField;
    private JLabel messageLabel;
    private JLabel timeLabel;
    private JButton checkInButton;
    private JButton checkOutButton;

    public CollegeStaffAttendanceSystem() {

        setTitle("College Staff Attendance System");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 248, 255));
        panel.setLayout(null);

        // Title
        JLabel titleLabel = new JLabel("College Staff Attendance");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(40, 40, 90));
        titleLabel.setBounds(110, 20, 300, 30);
        panel.add(titleLabel);

        // Name label
        JLabel nameLabel = new JLabel("Staff Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setBounds(60, 80, 100, 25);
        panel.add(nameLabel);

        // Name field
        nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBounds(160, 80, 220, 30);
        panel.add(nameField);

        // Check-in button
        checkInButton = new JButton("Attendance In");
        checkInButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        checkInButton.setBackground(new Color(60, 120, 240));
        checkInButton.setForeground(Color.WHITE);
        checkInButton.setBounds(80, 130, 150, 40);
        checkInButton.addActionListener(this);
        panel.add(checkInButton);

        // Check-out button
        checkOutButton = new JButton("Attendance Out");
        checkOutButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        checkOutButton.setBackground(new Color(220, 70, 70));
        checkOutButton.setForeground(Color.WHITE);
        checkOutButton.setBounds(260, 130, 150, 40);
        checkOutButton.addActionListener(this);
        panel.add(checkOutButton);

        // Message label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        messageLabel.setBounds(40, 190, 400, 30);
        panel.add(messageLabel);

        // Time label
        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        timeLabel.setBounds(40, 225, 400, 25);
        panel.add(timeLabel);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter staff name!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");
        String currentTime =
                LocalDateTime.now().format(formatter);

        if (e.getSource() == checkInButton) {
            messageLabel.setText("Hello " + name + " 👋");
            messageLabel.setForeground(new Color(20, 120, 20));
            timeLabel.setText("Checked in at: " + currentTime);
        }

        if (e.getSource() == checkOutButton) {
            messageLabel.setText("Goodbye, have a nice day 😊");
            messageLabel.setForeground(new Color(150, 50, 50));
            timeLabel.setText("Checked out at: " + currentTime);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new CollegeStaffAttendanceSystem());
    }
}
