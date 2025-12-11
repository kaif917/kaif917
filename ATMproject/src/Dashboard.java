import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Dashboard extends JFrame {
    private String cardNumber;

    // UI Colors
    private final Color BG = new Color(17, 32, 49);
    private final Color CARD_BG = new Color(35, 55, 71);
    private final Color BTN_BG = new Color(52, 91, 99);
    private final Color BTN_HOVER = new Color(64, 142, 145);
    private final Color TEXT = new Color(212, 236, 221);

    public Dashboard(String cardNumber) {
        this.cardNumber = cardNumber;

        setTitle("ATM Dashboard");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("ATM Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(TEXT);
        title.setBounds(130, 20, 300, 30);
        add(title);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(CARD_BG);
        panel.setBounds(60, 70, 320, 250);
        add(panel);

        JButton balanceBtn = createButton("Check Balance", 30);
        balanceBtn.addActionListener(e -> showBalance());
        panel.add(balanceBtn);

        JButton depositBtn = createButton("Deposit Money", 85);
        depositBtn.addActionListener(e -> depositMoney());
        panel.add(depositBtn);

        JButton withdrawBtn = createButton("Withdraw Cash", 140);
        withdrawBtn.addActionListener(e -> withdrawMoney());
        panel.add(withdrawBtn);

        JButton exitBtn = createButton("Exit", 195);
        exitBtn.addActionListener(e -> System.exit(0));
        panel.add(exitBtn);

        getContentPane().setBackground(BG);
        setVisible(true);
    }

    private JButton createButton(String text, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(70, y, 180, 40);
        btn.setBackground(BTN_BG);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 15));
        btn.setFocusPainted(false);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(BTN_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(BTN_BG);
            }
        });
        return btn;
    }

    private void showBalance() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT balance FROM accounts WHERE card_number = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, cardNumber);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double bal = rs.getDouble("balance");
                JOptionPane.showMessageDialog(this, "Your current balance is: ₹" + bal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void depositMoney() {
        String amtStr = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
        if (amtStr == null) return;

        try {
            double amt = Double.parseDouble(amtStr);

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "UPDATE accounts SET balance = balance + ? WHERE card_number = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setDouble(1, amt);
                pst.setString(2, cardNumber);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Deposit successful!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount!");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void withdrawMoney() {
        String amtStr = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
        if (amtStr == null) return;

        try {
            double amt = Double.parseDouble(amtStr);

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "SELECT balance FROM accounts WHERE card_number = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, cardNumber);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    double bal = rs.getDouble("balance");

                    if (bal >= amt) {
                        String updateSql = "UPDATE accounts SET balance = balance - ? WHERE card_number = ?";
                        PreparedStatement pst2 = conn.prepareStatement(updateSql);
                        pst2.setDouble(1, amt);
                        pst2.setString(2, cardNumber);
                        pst2.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Withdrawal successful!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient balance!");
                    }
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount!");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
