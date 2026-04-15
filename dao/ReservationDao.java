package dao;

import database.DatabaseConnection;
import model.Reservation;
import model.Client;
import model.Chambre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

    // ================= AJOUTER =================
    public void ajouterReservation(Reservation r) {

        String sql = "INSERT INTO reservation (client_id, chambre_id, date_debut, date_fin) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getClient().getId());
            ps.setInt(2, r.getChambre().getId());
            ps.setDate(3, Date.valueOf(r.getDateDebut()));
            ps.setDate(4, Date.valueOf(r.getDateFin()));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= SUPPRIMER =================
    public void supprimerReservation(int id) {

        String sql = "DELETE FROM reservation WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= LISTER =================
    public List<Reservation> getAllReservations() {

        List<Reservation> list = new ArrayList<>();

        String sql = """
            SELECT r.id,
                   c.id as client_id, c.nom, c.prenom, c.telephone,
                   ch.id as chambre_id, ch.numero, ch.type, ch.prix_nuit, ch.disponible,
                   r.date_debut, r.date_fin
            FROM reservation r
            JOIN client c ON r.client_id = c.id
            JOIN chambre ch ON r.chambre_id = ch.id
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Client client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone")
                );

                Chambre chambre = new Chambre(
                        rs.getInt("chambre_id"),
                        rs.getString("numero"),
                        rs.getString("type"),
                        rs.getDouble("prix_nuit"),
                        rs.getBoolean("disponible")
                );

                Reservation r = new Reservation(
                        rs.getInt("id"),
                        client,
                        chambre,
                        rs.getDate("date_debut").toLocalDate(),
                        rs.getDate("date_fin").toLocalDate()
                );

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
