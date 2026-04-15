
import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hotel", "root", "");

            System.out.println("Connexion réussie !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}