import java.sql.*;

public class SqlManager {
    private static final String URL = "jdbc:sqlite:experiments.db";

    // Create table if not exists
    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS experiments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "volume REAL," +
                "pH REAL" +
                ");";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert one reading
    public static void insertData(String name, double volume, double pH) {
        String sql = "INSERT INTO experiments(name, volume, pH) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, volume);
            pstmt.setDouble(3, pH);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve average pH or compare runs
    public static void compareRuns() {
        String sql = "SELECT name, AVG(pH) as avg_pH FROM experiments GROUP BY name";
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n Average pH per Experiment:");
            while (rs.next()) {
                System.out.printf("%-25s %.2f%n", rs.getString("name"), rs.getDouble("avg_pH"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
