import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ATM extends JFrame {

    private JTextField cardField;
    private JPasswordField pinField;
    private JButton loginButton;

    // Modern Colors
    private final Color BG = new Color(17, 32, 49);
    private final Color CARD_BG = new Color(35, 55, 71);
    private final Color BTN_BG = new Color(52, 91, 99);
    private final Color BTN_HOVER = new Color(64, 142, 145);
    private final Color TEXT = new Color(212, 236, 221);

    public ATM() {
        setTitle("ATM Login");
        setSize(420, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(CARD_BG);
        cardPanel.setBounds(50, 40, 300, 200);
        cardPanel.setLayout(null);
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        add(cardPanel);

        JLabel title = new JLabel("ATM Login");
        title.setForeground(TEXT);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(120, 5, 200, 30);
        add(title);

        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setForeground(TEXT);
        cardLabel.setBounds(20, 20, 120, 25);
        cardPanel.add(cardLabel);

        cardField = new JTextField();
        cardField.setBounds(140, 20, 130, 25);
        cardField.setBackground(Color.WHITE);
        cardField.setForeground(Color.BLACK);
        cardPanel.add(cardField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setForeground(TEXT);
        pinLabel.setBounds(20, 60, 120, 25);
        cardPanel.add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(140, 60, 130, 25);
        pinField.setBackground(Color.WHITE);
        cardPanel.add(pinField);

        loginButton = new JButton("Login");
        loginButton.setBounds(90, 110, 120, 35);
        loginButton.setBackground(BTN_BG);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));

        // Hover effect
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(BTN_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(BTN_BG);
            }
        });

        loginButton.addActionListener(e -> loginAction());
        cardPanel.add(loginButton);

        getContentPane().setBackground(BG);
        setVisible(true);
    }

    private void loginAction() {
        String cardNumber = cardField.getText();
        String pin = new String(pinField.getPassword());

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database not connected!");
                return;
            }

            String sql = "SELECT * FROM accounts WHERE card_number = ? AND pin = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, cardNumber);
            pst.setString(2, pin);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new Dashboard(cardNumber);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid card number or PIN!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ATM();
    }
}
