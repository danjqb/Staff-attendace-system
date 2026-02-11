import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class CollegeStaffAttendanceSystem extends JFrame implements ActionListener {

    private JTextField nameField;
    private JLabel messageLabel;
    private JLabel timeLabel;
    private JButton checkInButton;
    private JButton checkOutButton;

    private static final String DB_URL = "jdbc:sqlite:attendance.db";

    public CollegeStaffAttendanceSystem() {

        // Force load SQLite driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "SQLite JDBC Driver not found!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        createTable();
        setupUI();
    }

    private void setupUI() {
        setTitle("College Staff Attendance System");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 245, 255));

        JLabel title = new JLabel("College Staff Attendance");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(100, 20, 350, 30);
        panel.add(title);

        JLabel nameLabel = new JLabel("Staff Name:");
        nameLabel.setBounds(60, 80, 100, 25);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(160, 80, 220, 30);
        panel.add(nameField);

        checkInButton = new JButton("Attendance In");
        checkInButton.setBounds(80, 130, 150, 40);
        checkInButton.setBackground(new Color(60, 120, 240));
        checkInButton.setForeground(Color.WHITE);
        checkInButton.addActionListener(this);
        panel.add(checkInButton);

        checkOutButton = new JButton("Attendance Out");
        checkOutButton.setBounds(260, 130, 150, 40);
        checkOutButton.setBackground(new Color(200, 60, 60));
        checkOutButton.setForeground(Color.WHITE);
        checkOutButton.addActionListener(this);
        panel.add(checkOutButton);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        messageLabel.setBounds(40, 190, 400, 30);
        panel.add(messageLabel);

        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setBounds(40, 225, 400, 25);
        panel.add(timeLabel);

        add(panel);
        setVisible(true);
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS attendance (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "staff_name TEXT, " +
                "date TEXT, " +
                "check_in TEXT, " +
                "check_out TEXT" +
                ");";

        try (Connection con = DriverManager.getConnection(DB_URL);
             Statement stmt = con.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkIn(String name) {
        String sql = "INSERT INTO attendance (staff_name, date, check_in) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, LocalDate.now().toString());
            ps.setString(3, LocalTime.now().toString());
            ps.executeUpdate();

            messageLabel.setText("Hello " + name + " 👋");
            messageLabel.setForeground(new Color(0, 120, 0));
            timeLabel.setText("Checked in at: " + LocalTime.now());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkOut(String name) {
        String sql = "UPDATE attendance SET check_out = ? " +
                "WHERE staff_name = ? AND date = ?";

        try (Connection con = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, LocalTime.now().toString());
            ps.setString(2, name);
            ps.setString(3, LocalDate.now().toString());
            ps.executeUpdate();

            messageLabel.setText("Goodbye, have a nice day 😊");
            messageLabel.setForeground(new Color(150, 0, 0));
            timeLabel.setText("Checked out at: " + LocalTime.now());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter staff name!");
            return;
        }

        if (e.getSource() == checkInButton) {
            checkIn(name);
        } else if (e.getSource() == checkOutButton) {
            checkOut(name);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CollegeStaffAttendanceSystem();
            }
        });
    }
}
