package com.hotel.model;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int idClient;
    private int idChambre;
    private String clientNomComplet;
    private String chambreNumero;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double montantTotal;

    public Reservation() {
    }

    public Reservation(int id, int idClient, int idChambre, String clientNomComplet,
                       String chambreNumero, LocalDate dateDebut, LocalDate dateFin, double montantTotal) {
        this.id = id;
        this.idClient = idClient;
        this.idChambre = idChambre;
        this.clientNomComplet = clientNomComplet;
        this.chambreNumero = chambreNumero;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montantTotal = montantTotal;
    }

    public Reservation(int idClient, int idChambre, LocalDate dateDebut, LocalDate dateFin) {
        this.idClient = idClient;
        this.idChambre = idChambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdChambre() {
        return idChambre;
    }

    public String getClientNomComplet() {
        return clientNomComplet;
    }

    public String getChambreNumero() {
        return chambreNumero;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdChambre(int idChambre) {
        this.idChambre = idChambre;
    }

    public void setClientNomComplet(String clientNomComplet) {
        this.clientNomComplet = clientNomComplet;
    }

    public void setChambreNumero(String chambreNumero) {
        this.chambreNumero = chambreNumero;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }
}
