
package model;

public class Client {

    private int id;  // 🔹 ajouter l'ID
    private String nom;
    private String prenom;
    private String telephone;

    // Constructeur avec id (pour récupérer depuis la base)
    public Client(int id, String nom, String prenom, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    // Constructeur sans id (pour insertion)
    public Client(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    // 🔹 Getter et Setter pour l'ID

    // Getters et setters existants


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }



    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }


    @Override
    public String toString() {
        return nom + " " + prenom;
    }
}