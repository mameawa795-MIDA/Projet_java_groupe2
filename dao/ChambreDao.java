package dao;

import model.Chambre;
import database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChambreDao {

    // AJOUTER
    public void ajouterChambre(Chambre c) {
        String sql = "INSERT INTO chambre(numero, type, prix_nuit, disponible) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNumero());
            ps.setString(2, c.getType());
            ps.setDouble(3, c.getPrix_nuit());
            ps.setBoolean(4, c.isDisponible());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MODIFIER
    public void modifierChambre(Chambre c) {
        String sql = "UPDATE chambre SET numero=?, type=?, prix_nuit=?, disponible=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNumero());
            ps.setString(2, c.getType());
            ps.setDouble(3, c.getPrix_nuit());
            ps.setBoolean(4, c.isDisponible());
            ps.setInt(5, c.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SUPPRIMER
    public void supprimerChambre(int id) {
        String sql = "DELETE FROM chambre WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LISTER
    public List<Chambre> getAllChambres() {
        List<Chambre> list = new ArrayList<>();

        String sql = "SELECT * FROM chambre";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Chambre c = new Chambre(
                        rs.getInt("id"),
                        rs.getString("numero"),
                        rs.getString("type"),
                        rs.getDouble("prix_nuit"),
                        rs.getBoolean("disponible")
                );
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
