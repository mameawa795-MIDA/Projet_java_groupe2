package com.hotel.dao;

import com.hotel.database.DatabaseConnection;
import com.hotel.model.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();

        String sql = """
                SELECT r.id,
                       r.id_client,
                       r.id_chambre,
                       CONCAT(c.prenom, ' ', c.nom) AS client_nom,
                       ch.numero AS chambre_numero,
                       r.date_debut,
                       r.date_fin,
                       DATEDIFF(r.date_fin, r.date_debut) * ch.prix_nuit AS montant_total
                FROM reservation r
                JOIN client c ON r.id_client = c.id
                JOIN chambre ch ON r.id_chambre = ch.id
                ORDER BY r.id DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("id_client"),
                        rs.getInt("id_chambre"),
                        rs.getString("client_nom"),
                        rs.getString("chambre_numero"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getDouble("montant_total")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public List<Reservation> getReservationsByClient(int clientId) {
        List<Reservation> reservations = new ArrayList<>();

        String sql = """
                SELECT r.id,
                       r.id_client,
                       r.id_chambre,
                       CONCAT(c.prenom, ' ', c.nom) AS client_nom,
                       ch.numero AS chambre_numero,
                       r.date_debut,
                       r.date_fin,
                       DATEDIFF(r.date_fin, r.date_debut) * ch.prix_nuit AS montant_total
                FROM reservation r
                JOIN client c ON r.id_client = c.id
                JOIN chambre ch ON r.id_chambre = ch.id
                WHERE r.id_client = ?
                ORDER BY r.id DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getInt("id_client"),
                        rs.getInt("id_chambre"),
                        rs.getString("client_nom"),
                        rs.getString("chambre_numero"),
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate(),
                        rs.getDouble("montant_total")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public boolean isChambreReservee(int idChambre, LocalDate dateDebut, LocalDate dateFin) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM reservation
                WHERE id_chambre = ?
                AND (? < date_fin AND ? > date_debut)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idChambre);
            ps.setDate(2, Date.valueOf(dateDebut));
            ps.setDate(3, Date.valueOf(dateFin));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation(id_client, id_chambre, date_debut, date_fin) VALUES(?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reservation.getIdClient());
            ps.setInt(2, reservation.getIdChambre());
            ps.setDate(3, Date.valueOf(reservation.getDateDebut()));
            ps.setDate(4, Date.valueOf(reservation.getDateFin()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteReservation(int id) {
        String sql = "DELETE FROM reservation WHERE id=?";

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
