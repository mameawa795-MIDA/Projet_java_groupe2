package model;

import java.time.LocalDate;

public class Reservation {

    private int id;
    private Client client;
    private Chambre chambre;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public Reservation(int id, Client client, Chambre chambre, LocalDate dateDebut, LocalDate dateFin) {
        this.id = id;
        this.client = client;
        this.chambre = chambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Reservation(Client client, Chambre chambre, LocalDate dateDebut, LocalDate dateFin) {
        this.client = client;
        this.chambre = chambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() { return id; }
    public Client getClient() { return client; }
    public Chambre getChambre() { return chambre; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
}
