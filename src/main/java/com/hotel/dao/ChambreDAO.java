package com.hotel.dao;

import com.hotel.database.DatabaseConnection;
import com.hotel.model.Chambre;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChambreDAO {

    public List<Chambre> getAllChambres() {
        List<Chambre> chambres = new ArrayList<>();
        String sql = "SELECT * FROM chambre ORDER BY id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                chambres.add(new Chambre(
                        rs.getInt("id"),
                        rs.getString("numero"),
                        rs.getString("type"),
                        rs.getDouble("prix_nuit"),
                        rs.getBoolean("disponible")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chambres;
    }

    public boolean addChambre(Chambre chambre) {
        String sql = "INSERT INTO chambre(numero, type, prix_nuit, disponible) VALUES(?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, chambre.getNumero());
            ps.setString(2, chambre.getType());
            ps.setDouble(3, chambre.getPrixNuit());
            ps.setBoolean(4, chambre.isDisponible());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateChambre(Chambre chambre) {
        String sql = "UPDATE chambre SET numero=?, type=?, prix_nuit=?, disponible=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, chambre.getNumero());
            ps.setString(2, chambre.getType());
            ps.setDouble(3, chambre.getPrixNuit());
            ps.setBoolean(4, chambre.isDisponible());
            ps.setInt(5, chambre.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteChambre(int id) {
        String sql = "DELETE FROM chambre WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isDisponible(int idChambre) {
        String sql = "SELECT disponible FROM chambre WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idChambre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("disponible");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Chambre> getChambresDisponibles(LocalDate dateDebut, LocalDate dateFin) {
        List<Chambre> chambres = new ArrayList<>();

        String sql = """
                SELECT * FROM chambre c
                WHERE c.disponible = true
                AND c.id NOT IN (
                    SELECT r.id_chambre
                    FROM reservation r
                    WHERE (? < r.date_fin AND ? > r.date_debut)
                )
                ORDER BY c.numero
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(dateDebut));
            ps.setDate(2, Date.valueOf(dateFin));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                chambres.add(new Chambre(
                        rs.getInt("id"),
                        rs.getString("numero"),
                        rs.getString("type"),
                        rs.getDouble("prix_nuit"),
                        rs.getBoolean("disponible")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chambres;
    }
}
