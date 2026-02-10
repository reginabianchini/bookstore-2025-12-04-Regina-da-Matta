package csd214.bookstore.mysql;

import csd214.bookstore.pojos.Lipstick;

import java.sql.*;

public class JdbcLipstickApp {
    private static final String URL = "jdbc:mysql://localhost:3333/bookstore";
    private static final String USER = "csd214";
    private static final String PASS = "itstudies12345";
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            // 1. Create Table
            createTable(conn);
            // 2. Insert
            System.out.println("--- INSERTING ---");
            Lipstick l1 = new Lipstick("Super Lipstick", 19.99);
            insertLipstick(conn, l1);
            // 3. Read
            System.out.println("--- READING ---");
            listLipstick(conn);
            // 4. Update
            System.out.println("--- UPDATING ---");
            updateLipstickPricePrice(conn);

            // 5. Delete
            System.out.println("--- DELETING ---");
            deleteLipstick(conn, "Lipstick");
            listLipstick(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void createTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS lipstick (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "product_id VARCHAR(36), " +
                "foundation_coverage VARCHAR(255), " +
                "price DOUBLE)" +
                "CONSTRAINT lipstick_fk " +
                "FOREIGN KEY (product_id)" +
                "REFERENCES lipstick(product_id)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'lipstick' ready.");
        }
    }
    private static void insertLipstick(Connection conn, Lipstick l) throws SQLException {
        // SECURITY: Use ? to prevent SQL Injection
        String sql = "INSERT INTO lipstick (product_id, lipstick_shade, price) VALUES (?, ?, ?)";
        lipstick(URL, USER, PASS, (PreparedStatement) l.prepareStatement(sql), l.getProductId(), l.getShade(), l.getPrice(), l, sql);
    }

    static void lipstick(String url, String user, String pass, PreparedStatement preparedStatement, String productId, String shade, double price, Lipstick l, String sql) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = preparedStatement) {
            ps.setString(1, productId); // UUID
            ps.setString(2, shade);
            ps.setDouble(3, price);
            ps.executeUpdate();
            System.out.println("Saved Foundation: " + shade);
        }
    }

    private static void listLipstick(Connection conn) throws SQLException {
        String sql = "SELECT * FROM lipstick";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d | UUID: %s | Name: %s | Price: $%.2f%n",
                        rs.getInt("id"),
                        rs.getString("product_id"),
                        rs.getString("lipstick_coverage"),
                        rs.getDouble("price"));
            }
        }
    }
    private static void updateLipstickPricePrice(Connection conn) throws SQLException {
        String sql = "UPDATE lipstick SET price = ? WHERE lipstick_coverage = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, 25.5);
            ps.setString(2, "Super Lipstick");
            int rows = ps.executeUpdate();
            System.out.println("Updated " + rows + " lipstick(s).");
        }
    }

    private static void deleteLipstick(Connection conn, String name) throws SQLException {
        String sql = "DELETE FROM lipstick WHERE lipstick_shade = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
            System.out.println("Deleted lipstick: " + name);
        }
    }
}