package model;

public class Chambre {

    private int id;
    private String numero;
    private String type;
    private double prix_nuit;
    private boolean disponible;

    public Chambre() {}

    public Chambre(int id, String numero, String type, double prix_nuit, boolean disponible) {
        this.id = id;
        this.numero = numero;
        this.type = type;
        this.prix_nuit = prix_nuit;
        this.disponible = disponible;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPrix_nuit() { return prix_nuit; }
    public void setPrix_nuit(double prix_nuit) { this.prix_nuit = prix_nuit; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return "Chambre " + numero + " (" + type + ")";
    }
}