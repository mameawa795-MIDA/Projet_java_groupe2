package com.hotel.model;

public class Chambre {
    private int id;
    private String numero;
    private String type;
    private double prixNuit;
    private boolean disponible;

    public Chambre() {
    }

    public Chambre(int id, String numero, String type, double prixNuit, boolean disponible) {
        this.id = id;
        this.numero = numero;
        this.type = type;
        this.prixNuit = prixNuit;
        this.disponible = disponible;
    }

    public Chambre(String numero, String type, double prixNuit, boolean disponible) {
        this.numero = numero;
        this.type = type;
        this.prixNuit = prixNuit;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrixNuit() {
        return prixNuit;
    }

    public void setPrixNuit(double prixNuit) {
        this.prixNuit = prixNuit;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return numero + " - " + type + " (" + prixNuit + " DH)";
    }
}
