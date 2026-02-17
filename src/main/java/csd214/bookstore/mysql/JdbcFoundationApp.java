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
            Foundation foundation = new Foundation("100", 20, "Full coverage");
            insertFoundation(conn, foundation);
            // 3. Read
            System.out.println("--- READING ---");
            listFoundation(conn);
            // 4. Update
            System.out.println("--- UPDATING ---");
            updateFoundationPrice(conn, "Full coverage", 25.50);

            // 5. Delete
            System.out.println("--- DELETING ---");
            deleteFoundation(conn, "Full coverage");
            listFoundation(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection conn) throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS foundation (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "product_id VARCHAR(36), " +
                "coverage VARCHAR(50), " +
                "shade VARCHAR(255), " +
                "price DOUBLE)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'foundation' ready.");
        }
    }
    private static void insertFoundation(Connection conn, Foundation f) throws SQLException {
        // SECURITY: Use ? to prevent SQL Injection
        String sql = "INSERT INTO foundation (product_id, coverage, shade, price ) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getProductId()); // UUID
            ps.setString(2, f.getCoverage());
            ps.setString(3, f.getShade());
            ps.setDouble(4, f.getPrice());
            ps.executeUpdate();
            System.out.println("Saved: " + f.toString());
        }
    }
    private static void listFoundation(Connection conn) throws SQLException {
        String sql = "SELECT * FROM foundation";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d | UUID: %s | Coverage: %s| Shade: %s | Price: $%.2f%n",
                        rs.getInt("id"),
                        rs.getString("product_id"),
                        rs.getString("coverage"),
                        rs.getString("shade"),
                        rs.getDouble("price"));
            }
        }
    }
    private static void updateFoundationPrice(Connection conn, String coverage, double newPrice) throws SQLException {
        String sql = "UPDATE foundation SET price = ? WHERE coverage = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newPrice);
            ps.setString(2, coverage);
            int rows = ps.executeUpdate();
            System.out.println("Updated " + rows + " foundation(s).");
        }
    }

    private static void deleteFoundation(Connection conn, String coverage) throws SQLException {
        String sql = "DELETE FROM foundation WHERE coverage = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, coverage);
            ps.executeUpdate();
            System.out.println("Deleted foundation: " + coverage);
        }
    }
}


