package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.Client;
import dao.ClientDao;

public class ClientController {

    @FXML private TextField txtNom;
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

        loadClients();
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


}