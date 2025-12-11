import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/atmdb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // This works for 9.4.0 too!
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Database Connected Successfully!");
            return conn;
        } catch (Exception e) {
            System.out.println(" Database Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        getConnection();
    }
}
