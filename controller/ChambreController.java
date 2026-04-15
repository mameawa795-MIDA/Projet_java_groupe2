package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Chambre;
import dao.ChambreDao;

public class ChambreController {

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

    private ChambreDao dao = new ChambreDao();
    private Chambre selected;

    @FXML
    public void initialize() {

        colIdCh.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix_nuit"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        charger();
    }

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

        dao.ajouterChambre(c);
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

            dao.modifierChambre(c);
            charger();
            clear();
        }
    }

    // SUPPRIMER
    @FXML

    public void supprimerChambre() {
        if (selected != null) {
            dao.supprimerChambre(selected.getId());
            charger();
            clear();
        }
    }

    // CHARGER
    public void charger() {
        tableChambres.getItems().setAll(dao.getAllChambres());
    }

    // CLEAR
    private void clear() {
        txtNumero.clear();
        txtType.clear();
        txtPrix.clear();
        checkDisponible.setSelected(false);
        selected = null;
    }
}