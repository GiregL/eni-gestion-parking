package fr.eni.gestionParking.ihm;

import fr.eni.gestionParking.bll.exceptions.BLLException;
import fr.eni.gestionParking.bll.exceptions.XMLSerializerException;
import fr.eni.gestionParking.bll.expose.PersonneService;
import fr.eni.gestionParking.bll.expose.ServiceFactory;
import fr.eni.gestionParking.bll.expose.VoitureService;
import fr.eni.gestionParking.bll.expose.XMLService;
import fr.eni.gestionParking.bo.Personne;
import fr.eni.gestionParking.bo.Voiture;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.Notifications;

import javax.management.Notification;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

public class Controller {

    private static final Logger LOGGER = Logger.getLogger(Controller.class.getSimpleName());

    private Personne selectedPersonne;
    private Voiture selectedVoiture;

    //// Tables

    @FXML
    private TableView<Voiture> voitureTableView;

    @FXML
    private TableView<Personne> personnesTableView;

    //// Voiture

    @FXML
    private TextField nomVoitureField;

    @FXML
    private TextField plaqueImmatField;

    @FXML
    private ComboBox<Personne> personnePicker;

    @FXML
    private Button ajouterVoitureButton;

    @FXML
    private Button modifierVoitureButton;

    @FXML
    private Button supprimerVoitureButton;

    //// Personne

    @FXML
    private TextField nomPersonneField;

    @FXML
    private TextField prenomPersonneField;

    @FXML
    private Button ajouterPersonneButton;

    @FXML
    private Button modifierPersonneButton;

    @FXML
    private Button supprimerPersonneButton;

    //// Exports

    @FXML
    private Button exportFormatXML;

    @FXML
    private Button exportFormatCSV;

    public Controller() {}

    //// ---------------------------------------------------------------------------------------------------------------
    //// Init
    //// ---------------------------------------------------------------------------------------------------------------


    @FXML
    public void initialize() {
        this.initPersonneTableView();
        this.initVoitureTableView();

        this.selectedPersonne = null;
        this.selectedVoiture = null;

        try {
            List<Personne> personnes = ServiceFactory.getPersonneService().getAllPersonnes();
            this.personnesTableView.getItems().addAll(personnes);

            // Voiture table
            this.voitureTableView.getItems().addAll(ServiceFactory.getVoitureService().getAll());

            // Personne picker
            this.personnePicker.getItems().addAll(personnes);
            this.personnePicker.getItems().add(null);

        } catch (BLLException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    private void initPersonneTableView() {
        // Nom
        this.personnesTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nom"));
        // Prénom
        this.personnesTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("prenom"));

        this.personnesTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.personnesTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.selectedPersonne = newSelection;
                this.setPersonneFields(this.selectedPersonne);
                try {
                    if (ServiceFactory.getPersonneService().getPersonneVoiture(this.selectedPersonne).size() != 0) {
                        this.supprimerPersonneButton.setDisable(true);
                    } else {
                        this.supprimerPersonneButton.setDisable(false);
                    }
                } catch (BLLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initVoitureTableView() {
        this.voitureTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Nom
        this.voitureTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nom"));

        // Plaque d'immat
        this.voitureTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("plaqueImmatriculation"));

        // Personne
        TableColumn<Voiture, String> col = (TableColumn<Voiture, String>) this.voitureTableView.getColumns().get(2);
        col.setCellValueFactory(e -> {
            Voiture voiture = e.getValue();
            if (voiture.isLinked()) {
                return new SimpleStringProperty(voiture.getPersonne().getNom() + " " + voiture.getPersonne().getPrenom());
            } else {
                return new SimpleStringProperty("");
            }
        });

        this.voitureTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.selectedVoiture = newSelection;
                this.setVoitureFields(this.selectedVoiture);
                this.initPersonneComboBox(this.selectedVoiture);
            }
        });
    }

    private void initPersonneComboBox(Voiture voiture) {
        this.personnePicker.setValue(voiture.getPersonne());
    }

    private void setVoitureFields(Voiture voiture) {
        this.nomVoitureField.setText(voiture.getNom());
        this.plaqueImmatField.setText(voiture.getPlaqueImmatriculation());
    }

    private void setPersonneFields(Personne personne) {
        this.nomPersonneField.setText(personne.getNom());
        this.prenomPersonneField.setText(personne.getPrenom());
    }

    private void reloadPersonnes() {
        Personne chosen = this.personnePicker.getValue();
        this.personnePicker.getItems().clear();
        try {
            List<Personne> personnes = ServiceFactory.getPersonneService().getAllPersonnes();
            this.personnePicker.getItems().addAll(personnes);
            this.personnesTableView.getItems().addAll(personnes);
        } catch (BLLException e) {
            e.printStackTrace();
            System.exit(3);
        }
        if (this.personnePicker.getItems().contains(chosen)) {
            this.personnePicker.setValue(chosen);
        }
    }

    private void reloadVoitures() throws BLLException {
        this.voitureTableView.getItems().addAll(ServiceFactory.getVoitureService().getAll());
    }

    //// ---------------------------------------------------------------------------------------------------------------
    //// Handlers
    //// ---------------------------------------------------------------------------------------------------------------

    @FXML
    public void saveToCSV(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Exporter au format CSV");
        chooser.setInitialFileName("data.csv");
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("data", ".csv"));
        File target = chooser.showSaveDialog(((Node) event.getTarget()).getScene().getWindow());
        // TODO
    }

    @FXML
    public void saveToXML(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Exporter au format XML");
        chooser.setInitialFileName("data.xml");
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("xml", ".xml"));
        File target = chooser.showSaveDialog(((Node) event.getTarget()).getScene().getWindow());

        XMLService service = ServiceFactory.getXMLService();
        try {
            service.writeToFile(target, this.personnesTableView.getItems(), List.of());
            ImageView logo = getImageView("success-icon.svg");
            logo.setFitHeight(48);
            logo.setFitWidth(48);
            Notifications.create()
                    .graphic(logo)
                    .title("Export au format XML")
                    .text("Export des données au format XML réalisé avec succès.")
                    .onAction(e -> {
                        try {
                            Runtime.getRuntime().exec("explorer.exe /select " + target.getAbsolutePath());
                        } catch (IOException ex) {
                            LOGGER.warning("Failed to open file : " + ex.getMessage());
                        }
                    })
                    .show();
            LOGGER.info("[saveToXML] - Export done.");
        } catch (XMLSerializerException e) {
            ImageView logo = getImageView("error-icon.png");
            logo.setFitHeight(48);
            logo.setFitWidth(48);
            Notifications.create()
                    .graphic(logo)
                    .title("Export au format XML")
                    .text("Erreur lors de l'export des données au format XML.")
                    .show();
            LOGGER.severe("[saveToXML] Failed to export data.");
            throw new RuntimeException(e);
        }
    }

    // Voitures

    private void showErrorContextMessage(Node target, String message) {
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem item = new MenuItem(message);
        contextMenu.getItems().clear();
        contextMenu.getItems().add(item);
        contextMenu.show(target, Side.RIGHT, 10, 0);
    }

    private boolean validateVoitureForm() {
        VoitureService service = ServiceFactory.getVoitureService();
        boolean result = true;

        if (!service.validateName(this.nomVoitureField.getText())) {
            showErrorContextMessage(this.nomVoitureField, "Nom invalide, il ne peut être vide ni excéder 255 caractères.");
            result = false;
        }

        if (!service.validatePlaqueImmat(this.plaqueImmatField.getText())) {
            showErrorContextMessage(this.plaqueImmatField, "Format de plaque d'immatriculation invalide.");
            result = false;
        }

        return result;
    }

    @FXML
    public void ajouterVoiture(ActionEvent event) {
        VoitureService service = ServiceFactory.getVoitureService();
        try {
            if (validateVoitureForm()) {
                this.selectedVoiture = new Voiture();
                this.selectedVoiture.setNom(this.nomVoitureField.getText());
                this.selectedVoiture.setPlaqueImmatriculation(this.plaqueImmatField.getText());
                this.selectedVoiture.setPersonne(this.personnePicker.getValue());
                service.save(this.selectedVoiture);
                this.voitureTableView.getItems().clear();
                this.reloadVoitures();
            }
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void modifierVoiture(ActionEvent event) {
        VoitureService service = ServiceFactory.getVoitureService();
        try {
            if (validateVoitureForm()) {
                if (this.selectedVoiture != null) {
                    this.selectedVoiture.setPersonne(this.personnePicker.getValue());
                    this.selectedVoiture.setNom(this.nomVoitureField.getText());
                    this.selectedVoiture.setPlaqueImmatriculation(this.plaqueImmatField.getText());

                    service.update(this.selectedVoiture);
                    this.voitureTableView.getItems().clear();
                    this.reloadVoitures();
                } else {
                    showErrorContextMessage(this.modifierVoitureButton, "Aucune voiture n'est sélectionnée.");
                }
            }
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void supprimerVoiture(ActionEvent event) {
        VoitureService service = ServiceFactory.getVoitureService();
        try {
            if (this.selectedVoiture != null) {
                service.delete(this.selectedVoiture);
                this.voitureTableView.getItems().clear();
                this.reloadVoitures();
            } else {
                showErrorContextMessage(this.supprimerVoitureButton, "Aucune voiture n'est sélectionnée.");
            }
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
    }

    // Personnes

    private boolean validatePersonneForm() {
        PersonneService service = ServiceFactory.getPersonneService();
        boolean result = true;

        if (!service.validateNom(this.nomPersonneField.getText())) {
            showErrorContextMessage(this.nomPersonneField, "Nom invalide, il ne peut être vide ni excéder 255 caractères.");
            result = false;
        }

        if (!service.validatePrenom(this.prenomPersonneField.getText())) {
            showErrorContextMessage(this.prenomPersonneField, "Nom invalide, il ne peut être vide ni excéder 255 caractères.");
            result = false;
        }

        return result;
    }

    @FXML
    public void ajouterPersonne(ActionEvent event) {
        PersonneService service = ServiceFactory.getPersonneService();
        try {
            if (validatePersonneForm()) {
                Personne personne = new Personne();
                personne.setPrenom(this.prenomPersonneField.getText());
                personne.setNom(this.nomPersonneField.getText());
                service.insertPersonne(personne);

                this.personnesTableView.getItems().clear();
                this.reloadPersonnes();
            }
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void modifierPersonne(ActionEvent event) {
        PersonneService service = ServiceFactory.getPersonneService();
        try {
            if (validatePersonneForm()) {
                if (this.selectedPersonne != null) {
                    this.selectedPersonne.setPrenom(this.prenomPersonneField.getText());
                    this.selectedPersonne.setNom(this.nomPersonneField.getText());
                    service.update(this.selectedPersonne);

                    this.personnesTableView.getItems().clear();
                    this.reloadPersonnes();
                } else {
                    showErrorContextMessage(this.modifierPersonneButton, "Aucune personne n'est sélectionnée.");
                }
            }
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void supprimerPersonne(ActionEvent event) {
        PersonneService service = ServiceFactory.getPersonneService();
        try {
            if (this.selectedPersonne != null) {
                service.removePersonne(this.selectedPersonne);
                this.personnesTableView.getItems().clear();
                this.reloadPersonnes();
            } else {
                showErrorContextMessage(this.supprimerPersonneButton, "Aucune personne n'est sélectionnée.");
            }
        } catch (BLLException e) {
            throw new RuntimeException(e);
        }
    }

    //
    //      Others
    //

    private ImageView getImageView(String name) {
        InputStream input = getClass().getClassLoader().getResourceAsStream("images/" + name);
        Image image = new Image(input);
        return new ImageView(image);
    }
}
