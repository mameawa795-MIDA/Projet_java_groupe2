package com.hotel.dao;

import com.hotel.database.DatabaseConnection;
import com.hotel.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client ORDER BY id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public boolean addClient(Client client) {
        String sql = "INSERT INTO client(nom, prenom, telephone) VALUES(?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getTelephone());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateClient(Client client) {
        String sql = "UPDATE client SET nom=?, prenom=?, telephone=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getTelephone());
            ps.setInt(4, client.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteClient(int id) {
        String sql = "DELETE FROM client WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
