package controller;

import dao.ClientDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Client;
import model.Chambre;
import dao.ChambreDao;


import javafx.scene.control.*;





public class ReservationController  {

    @FXML
    private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtTelephone;

    @FXML private TableView<Client> tableClients;
    @FXML private TableColumn<Client, Integer> colId;
    @FXML private TableColumn<Client, String> colNom;
    @FXML private TableColumn<Client, String> colPrenom;
    @FXML private TableColumn<Client, String> colTelephone;

    private ObservableList<Client> clientsList = FXCollections.observableArrayList();
    private ClientDao dao = new ClientDao();


    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));


        // ===== CHAMBRE =====
        colIdCh.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix_nuit"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));


        comboClient.setItems(FXCollections.observableArrayList(dao.getAllClients()));
        comboChambre.setItems(FXCollections.observableArrayList(chambreDao.getAllChambres()));

        colClient.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("Client"));
        colChambre.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("Chambre"));
        colDebut.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("Début"));
        colFin.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty("Fin"));

        tableReservations.setItems(reservations);



        loadClients();
        loadChambres();
        loadReservations();

    }

    private void loadClients() {
        clientsList.clear();
        clientsList.addAll(dao.getAllClients());
        tableClients.setItems(clientsList);
    }

    @FXML
    public void ajouterClient() {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String telephone = txtTelephone.getText();

        if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs !");
            return;
        }

        Client client = new Client(nom, prenom, telephone);
        dao.ajouterClient(client);

        loadClients();

        txtNom.clear();
        txtPrenom.clear();
        txtTelephone.clear();

        System.out.println("Client ajouté avec ID = " + client.getId());
    }

    @FXML
    public void supprimerClient() {
        Client selectedClient = tableClients.getSelectionModel().getSelectedItem();

        if (selectedClient == null) {
            System.out.println("Aucun client sélectionné !");
            return;
        }

        ClientDao dao = new ClientDao();
        dao.supprimerClient(selectedClient.getId());

        loadClients(); // rafraîchir tableau
    }

    @FXML
    public void selectionClient() {
        Client c = tableClients.getSelectionModel().getSelectedItem();

        if (c != null) {
            txtNom.setText(c.getNom());
            txtPrenom.setText(c.getPrenom());
            txtTelephone.setText(c.getTelephone());
        }
    }

    public void chargerClients() {
        ClientDao dao = new ClientDao();
        tableClients.getItems().setAll(dao.getAllClients());
    }

    @FXML
    public void modifierClient() {
        Client selectedClient = tableClients.getSelectionModel().getSelectedItem();

        if (selectedClient == null) {
            System.out.println("Sélectionne un client !");
            return;
        }

        selectedClient.setNom(txtNom.getText());
        selectedClient.setPrenom(txtPrenom.getText());
        selectedClient.setTelephone(txtTelephone.getText());

        ClientDao dao = new ClientDao();
        dao.modifierClient(selectedClient);

        loadClients(); // rafraîchir
    }

    @FXML private TextField txtNumero;
    @FXML private TextField txtType;
    @FXML private TextField txtPrix;
    @FXML private CheckBox checkDisponible;

    @FXML private TableView<Chambre> tableChambres;
    @FXML private TableColumn<Chambre, Integer> colIdCh;
    @FXML private TableColumn<Chambre, String> colNumero;
    @FXML private TableColumn<Chambre, String> colType;
    @FXML private TableColumn<Chambre, Double> colPrix;
    @FXML private TableColumn<Chambre, Boolean> colDisponible;

    private ChambreDao chambreDao = new ChambreDao();



    // 👇 ICI tu ajoutes la méthode
    private void loadChambres() {
        tableChambres.getItems().setAll(chambreDao.getAllChambres());
    }
    private Chambre selected;



    // AJOUTER
    @FXML

    public void ajouterChambre() {
        Chambre c = new Chambre(
                0,
                txtNumero.getText(),
                txtType.getText(),
                Double.parseDouble(txtPrix.getText()),
                checkDisponible.isSelected()
        );

        chambreDao.ajouterChambre(c);
        charger();
        clear();
    }

    // SELECTION
    @FXML

    public void selectionChambre() {
        selected = tableChambres.getSelectionModel().getSelectedItem();

        if (selected != null) {
            txtNumero.setText(selected.getNumero());
            txtType.setText(selected.getType());
            txtPrix.setText(String.valueOf(selected.getPrix_nuit()));
            checkDisponible.setSelected(selected.isDisponible());
        }
    }

    // MODIFIER
    @FXML

    public void modifierChambre() {
        if (selected != null) {

            Chambre c = new Chambre(
                    selected.getId(),
                    txtNumero.getText(),
                    txtType.getText(),
                    Double.parseDouble(txtPrix.getText()),
                    checkDisponible.isSelected()
            );

            chambreDao.modifierChambre(c);
            charger();
            clear();
        }
    }

    // SUPPRIMER
    @FXML

    public void supprimerChambre() {
        if (selected != null) {
            chambreDao.supprimerChambre(selected.getId());
            charger();
            clear();
        }
    }

    // CHARGER
    public void charger() {
        tableChambres.getItems().setAll(chambreDao.getAllChambres());
    }

    // CLEAR
    private void clear() {
        txtNumero.clear();
        txtType.clear();
        txtPrix.clear();
        checkDisponible.setSelected(false);
        selected = null;
    }









    // ===== FXML =====
    @FXML private ComboBox<Client> comboClient;
    @FXML private ComboBox<Chambre> comboChambre;

    @FXML private DatePicker dateDebut;
    @FXML private DatePicker dateFin;

    @FXML private TableView<String> tableReservations;
    @FXML private TableColumn<String, String> colClient;
    @FXML private TableColumn<String, String> colChambre;
    @FXML private TableColumn<String, String> colDebut;
    @FXML private TableColumn<String, String> colFin;

    private ObservableList<String> reservations =
            FXCollections.observableArrayList();






    // ===== RESERVER =====
    @FXML
    public void ajouterReservation() {

        Client client = comboClient.getValue();
        Chambre chambre = comboChambre.getValue();

        if (client == null || chambre == null || dateDebut.getValue() == null || dateFin.getValue() == null) {
            System.out.println("❌ Remplis tous les champs !");
            return;
        }

        if (!chambre.isDisponible()) {
            System.out.println("❌ Chambre déjà réservée !");
            return;
        }

        String res = client.getNom()
                + " → " + chambre.getNumero()
                + " (" + dateDebut.getValue()
                + " - " + dateFin.getValue() + ")";

        reservations.add(res);

        chambre.setDisponible(false);
        chambreDao.modifierChambre(chambre);

        refresh();
    }

    // ===== ANNULER =====
    @FXML
    public void supprimerReservation() {

        String selected = tableReservations.getSelectionModel().getSelectedItem();

        if (selected == null) return;

        reservations.remove(selected);

        refresh();
    }

    // ===== REFRESH =====
    private void refresh() {
        tableReservations.getItems().setAll(reservations);

        comboChambre.setItems(FXCollections.observableArrayList(chambreDao.getAllChambres()));
    }

    private void loadReservations() {
        tableReservations.setItems(reservations);
    }



}
