package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import model.Client;
import database.DatabaseConnection;

public class ClientDao {

    // Ajouter un client et récupérer l'ID généré
    public void ajouterClient(Client client) {
        String sql = "INSERT INTO client(nom, prenom, telephone) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, client.getNom());
            pst.setString(2, client.getPrenom());
            pst.setString(3, client.getTelephone());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                int idGenere = rs.getInt(1);
                client.setId(idGenere);  // mettre l'ID dans l'objet Client
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Récupérer tous les clients depuis la base
    public List<Client> getAllClients() {
        List<Client> clientsList = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Client c = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone")
                );
                clientsList.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientsList;
    }
    public void supprimerClient(int id) {
        String sql = "DELETE FROM client WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Client supprimé !");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifierClient(Client client) {
        String sql = "UPDATE client SET nom=?, prenom=?, telephone=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getTelephone());
            ps.setInt(4, client.getId());

            ps.executeUpdate();

            System.out.println("Client modifié !");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}