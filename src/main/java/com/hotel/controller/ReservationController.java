package com.hotel.controller;

import com.hotel.dao.ChambreDAO;
import com.hotel.dao.ClientDAO;
import com.hotel.dao.ReservationDAO;
import com.hotel.model.Chambre;
import com.hotel.model.Client;
import com.hotel.model.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReservationController {

    // Clients
    @FXML private TextField txtNom;
    @FXML private TextField txtPrenom;
    @FXML private TextField txtTelephone;
    @FXML private TableView<Client> tableClients;
    @FXML private TableColumn<Client, Integer> colClientId;
    @FXML private TableColumn<Client, String> colClientNom;
    @FXML private TableColumn<Client, String> colClientPrenom;
    @FXML private TableColumn<Client, String> colClientTelephone;

    // Chambres
    @FXML private TextField txtNumero;
    @FXML private TextField txtType;
    @FXML private TextField txtPrix;
    @FXML private CheckBox chkDisponible;
    @FXML private TableView<Chambre> tableChambres;
    @FXML private TableColumn<Chambre, Integer> colChambreId;
    @FXML private TableColumn<Chambre, String> colChambreNumero;
    @FXML private TableColumn<Chambre, String> colChambreType;
    @FXML private TableColumn<Chambre, Double> colChambrePrix;
    @FXML private TableColumn<Chambre, Boolean> colChambreDisponible;

    // Réservations
    @FXML private ComboBox<Client> cbClient;
    @FXML private ComboBox<Client> cbFiltreClient;
    @FXML private ComboBox<Chambre> cbChambre;
    @FXML private DatePicker dpDateDebut;
    @FXML private DatePicker dpDateFin;
    @FXML private Label lblMontant;
    @FXML private TableView<Reservation> tableReservations;
    @FXML private TableColumn<Reservation, Integer> colReservationId;
    @FXML private TableColumn<Reservation, String> colReservationClient;
    @FXML private TableColumn<Reservation, String> colReservationChambre;
    @FXML private TableColumn<Reservation, LocalDate> colReservationDateDebut;
    @FXML private TableColumn<Reservation, LocalDate> colReservationDateFin;
    @FXML private TableColumn<Reservation, Double> colReservationMontant;

    private final ClientDAO clientDAO = new ClientDAO();
    private final ChambreDAO chambreDAO = new ChambreDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @FXML
    public void initialize() {
        configurerTables();
        chargerClients();
        chargerChambres();
        chargerReservations();
        ajouterListeners();
        lblMontant.setText("Montant estimé : 0.00 DH");
    }

    private void configurerTables() {
        colClientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colClientNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colClientPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colClientTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        colChambreId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colChambreNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colChambreType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colChambrePrix.setCellValueFactory(new PropertyValueFactory<>("prixNuit"));
        colChambreDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        colReservationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colReservationClient.setCellValueFactory(new PropertyValueFactory<>("clientNomComplet"));
        colReservationChambre.setCellValueFactory(new PropertyValueFactory<>("chambreNumero"));
        colReservationDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colReservationDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colReservationMontant.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
    }

    private void ajouterListeners() {
        tableClients.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, selected) -> {
            if (selected != null) {
                txtNom.setText(selected.getNom());
                txtPrenom.setText(selected.getPrenom());
                txtTelephone.setText(selected.getTelephone());
            }
        });

        tableChambres.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, selected) -> {
            if (selected != null) {
                txtNumero.setText(selected.getNumero());
                txtType.setText(selected.getType());
                txtPrix.setText(String.valueOf(selected.getPrixNuit()));
                chkDisponible.setSelected(selected.isDisponible());
            }
        });

        cbChambre.valueProperty().addListener((obs, oldVal, newVal) -> calculerMontantEstime());
        dpDateDebut.valueProperty().addListener((obs, oldVal, newVal) -> calculerMontantEstime());
        dpDateFin.valueProperty().addListener((obs, oldVal, newVal) -> calculerMontantEstime());
    }

    private void chargerClients() {
        ObservableList<Client> clients = FXCollections.observableArrayList(clientDAO.getAllClients());
        tableClients.setItems(clients);
        cbClient.setItems(clients);
        cbFiltreClient.setItems(clients);
    }

    private void chargerChambres() {
        tableChambres.setItems(FXCollections.observableArrayList(chambreDAO.getAllChambres()));
    }

    private void chargerReservations() {
        tableReservations.setItems(FXCollections.observableArrayList(reservationDAO.getAllReservations()));
    }

    private void calculerMontantEstime() {
        Chambre chambre = cbChambre.getValue();
        LocalDate debut = dpDateDebut.getValue();
        LocalDate fin = dpDateFin.getValue();

        if (chambre == null || debut == null || fin == null || !debut.isBefore(fin)) {
            lblMontant.setText("Montant estimé : 0.00 DH");
            return;
        }

        long nbNuits = ChronoUnit.DAYS.between(debut, fin);
        double montant = nbNuits * chambre.getPrixNuit();

        lblMontant.setText(String.format("Montant estimé : %.2f DH (%d nuit(s))", montant, nbNuits));
    }

    @FXML
    private void onAjouterClient() {
        if (txtNom.getText().isEmpty() || txtPrenom.getText().isEmpty() || txtTelephone.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez remplir tous les champs du client.");
            return;
        }

        Client client = new Client(
                txtNom.getText().trim(),
                txtPrenom.getText().trim(),
                txtTelephone.getText().trim()
        );

        if (clientDAO.addClient(client)) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Client ajouté avec succès.");
            viderFormClient();
            chargerClients();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter le client.");
        }
    }

    @FXML
    private void onModifierClient() {
        Client selected = tableClients.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection", "Veuillez sélectionner un client.");
            return;
        }

        selected.setNom(txtNom.getText().trim());
        selected.setPrenom(txtPrenom.getText().trim());
        selected.setTelephone(txtTelephone.getText().trim());

        if (clientDAO.updateClient(selected)) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Client modifié avec succès.");
            viderFormClient();
            chargerClients();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier le client.");
        }
    }

    @FXML
    private void onSupprimerClient() {
        Client selected = tableClients.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection", "Veuillez sélectionner un client.");
            return;
        }

        if (clientDAO.deleteClient(selected.getId())) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Client supprimé avec succès.");
            viderFormClient();
            chargerClients();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer ce client. Il a peut-être des réservations.");
        }
    }

    @FXML
    private void onViderClient() {
        viderFormClient();
    }

    private void viderFormClient() {
        txtNom.clear();
        txtPrenom.clear();
        txtTelephone.clear();
        tableClients.getSelectionModel().clearSelection();
    }

    @FXML
    private void onAjouterChambre() {
        try {
            if (txtNumero.getText().isEmpty() || txtType.getText().isEmpty() || txtPrix.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez remplir tous les champs de la chambre.");
                return;
            }

            Chambre chambre = new Chambre(
                    txtNumero.getText().trim(),
                    txtType.getText().trim(),
                    Double.parseDouble(txtPrix.getText().trim()),
                    chkDisponible.isSelected()
            );

            if (chambreDAO.addChambre(chambre)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Chambre ajoutée avec succès.");
                viderFormChambre();
                chargerChambres();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter la chambre.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être un nombre valide.");
        }
    }

    @FXML
    private void onModifierChambre() {
        Chambre selected = tableChambres.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection", "Veuillez sélectionner une chambre.");
            return;
        }

        try {
            selected.setNumero(txtNumero.getText().trim());
            selected.setType(txtType.getText().trim());
            selected.setPrixNuit(Double.parseDouble(txtPrix.getText().trim()));
            selected.setDisponible(chkDisponible.isSelected());

            if (chambreDAO.updateChambre(selected)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Chambre modifiée avec succès.");
                viderFormChambre();
                chargerChambres();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de modifier la chambre.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix doit être un nombre valide.");
        }
    }

    @FXML
    private void onSupprimerChambre() {
        Chambre selected = tableChambres.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection", "Veuillez sélectionner une chambre.");
            return;
        }

        if (chambreDAO.deleteChambre(selected.getId())) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Chambre supprimée avec succès.");
            viderFormChambre();
            chargerChambres();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer cette chambre. Elle a peut-être des réservations.");
        }
    }

    @FXML
    private void onViderChambre() {
        viderFormChambre();
    }

    private void viderFormChambre() {
        txtNumero.clear();
        txtType.clear();
        txtPrix.clear();
        chkDisponible.setSelected(false);
        tableChambres.getSelectionModel().clearSelection();
    }

    @FXML
    private void onAfficherChambresDisponibles() {
        LocalDate debut = dpDateDebut.getValue();
        LocalDate fin = dpDateFin.getValue();

        if (debut == null || fin == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez choisir la date de début et la date de fin.");
            return;
        }

        if (!debut.isBefore(fin)) {
            showAlert(Alert.AlertType.ERROR, "Validation", "La date de début doit être inférieure à la date de fin.");
            return;
        }

        List<Chambre> chambresDisponibles = chambreDAO.getChambresDisponibles(debut, fin);
        cbChambre.setItems(FXCollections.observableArrayList(chambresDisponibles));

        if (chambresDisponibles.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Information", "Aucune chambre disponible pour cette période.");
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Information", "Chambres disponibles chargées avec succès.");
        }
    }

    @FXML
    private void onReserver() {
        Client client = cbClient.getValue();
        Chambre chambre = cbChambre.getValue();
        LocalDate debut = dpDateDebut.getValue();
        LocalDate fin = dpDateFin.getValue();

        if (client == null || chambre == null || debut == null || fin == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez remplir tous les champs de réservation.");
            return;
        }

        if (!debut.isBefore(fin)) {
            showAlert(Alert.AlertType.ERROR, "Validation", "La date de début doit être inférieure à la date de fin.");
            return;
        }

        if (!chambreDAO.isDisponible(chambre.getId())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Cette chambre est marquée comme indisponible.");
            return;
        }

        if (reservationDAO.isChambreReservee(chambre.getId(), debut, fin)) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Cette chambre est déjà réservée pendant cette période.");
            return;
        }

        Reservation reservation = new Reservation(client.getId(), chambre.getId(), debut, fin);

        if (reservationDAO.addReservation(reservation)) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Réservation enregistrée avec succès.");
            chargerReservations();
            calculerMontantEstime();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'enregistrer la réservation.");
        }
    }

    @FXML
    private void onAnnulerReservation() {
        Reservation selected = tableReservations.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection", "Veuillez sélectionner une réservation.");
            return;
        }

        if (reservationDAO.deleteReservation(selected.getId())) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Réservation annulée avec succès.");
            chargerReservations();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'annuler la réservation.");
        }
    }

    @FXML
    private void onAfficherReservationsClient() {
        Client client = cbFiltreClient.getValue();

        if (client == null) {
            showAlert(Alert.AlertType.WARNING, "Filtre", "Veuillez sélectionner un client.");
            return;
        }

        tableReservations.setItems(FXCollections.observableArrayList(
                reservationDAO.getReservationsByClient(client.getId())
        ));
    }

    @FXML
    private void onAfficherToutesReservations() {
        chargerReservations();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
