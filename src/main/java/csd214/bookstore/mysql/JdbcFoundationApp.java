package csd214.bookstore.mysql;
import csd214.bookstore.pojos.Foundation;

import java.sql.*;

public class JdbcFoundationApp {
    private static final String URL = "jdbc:mysql://localhost:3333/bookstore";
    private static final String USER = "csd214";
    private static final String PASS = "itstudies12345";
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            // 1. Create Table
            createTable(conn);
            // 2. Insert
            System.out.println("--- INSERTING ---");
            Foundation f1 = new Foundation("Super Foundation", 19.99);
            insertFoundation(conn, f1);
            // 3. Read
            System.out.println("--- READING ---");
            listFoundation(conn);
            // 4. Update
            System.out.println("--- UPDATING ---");
            updateFoundationPricePrice(conn);

            // 5. Delete
            System.out.println("--- DELETING ---");
            deleteFoundation(conn, "Super Foundation");
            listFoundation(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void createTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS foundations (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "product_id VARCHAR(36), " +
                "foundation_coverage VARCHAR(255), " +
                "price DOUBLE)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'foundation' ready.");
        }
    }
    private static void insertFoundation(Connection conn, Foundation f) throws SQLException {
        // SECURITY: Use ? to prevent SQL Injection
        String sql = "INSERT INTO foundations (product_id, foundation_name, price) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getProductId()); // UUID
            ps.setString(2, f.getFoundationCoverage());
            ps.setDouble(3, f.getPrice());
            ps.executeUpdate();
            System.out.println("Saved: " + f.getFoundationCoverage());
        }
    }
    private static void listFoundation(Connection conn) throws SQLException {
        String sql = "SELECT * FROM foundations";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d | UUID: %s | Name: %s | Price: $%.2f%n",
                        rs.getInt("id"),
                        rs.getString("product_id"),
                        rs.getString("foundation_coverage"),
                        rs.getDouble("price"));
            }
        }
    }
    private static void updateFoundationPricePrice(Connection conn) throws SQLException {
        String sql = "UPDATE widgets SET price = ? WHERE foundation_coverage = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, 25.5);
            ps.setString(2, "Super Foundation");
            int rows = ps.executeUpdate();
            System.out.println("Updated " + rows + " foundations(s).");
        }
    }

    private static void deleteFoundation(Connection conn, String name) throws SQLException {
        String sql = "DELETE FROM foundation WHERE foundation_coverage = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
            System.out.println("Deleted foundation: " + name);
        }
    }
}