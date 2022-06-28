package sample.Medcin.GererPatient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.CabinetMedicale;
import sample.Datasource.Datasource;
import sample.Utility;
import sample.Validation;
import sample.model.Patient;

import java.io.IOException;
import java.time.LocalDate;

public class PatientController {

    @FXML
    private TableView<Patient> PatientsList;

    @FXML
    private AnchorPane mainPanel;


    @FXML
    private Pane pnlPatient;


    @FXML
    private TextField nomChamp;

    @FXML
    private TextField prenomChamp;



    private static Patient selectedPatient;



    public static Patient getSelectedPatient() {
        return selectedPatient;
    }


    public void initialize(){
        listPatients();
    }

    public void newDialog(Scene scene, String title, String path) {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(scene.getWindow());
        dialog.setTitle(title);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(path));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.showAndWait();
    }

    @FXML
    public void afficherDossierMedicale() {
        selectedPatient = (Patient) PatientsList.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            Utility.showAlert("Pas De Patient Selectionner", "S'il Te Plait Selectioner un Patient");

            return;
        }

        String path = "GererDossierMedicale/DossierMedicaleDialog.fxml";
        String title = "Dossier Medicale de " + selectedPatient.getNom() + " " + selectedPatient.getPrenom();
        newDialog(mainPanel.getScene(), title, path);


    }

    @FXML
    public void chercherPatient() {
        if (!Validation.hasContent(nomChamp.getText()) || !Validation.hasContent(prenomChamp.getText()))
            return;
        selectedPatient = CabinetMedicale.getPatientDAO().chercherPatient(nomChamp.getText(), prenomChamp.getText()).isPresent() ? CabinetMedicale.getPatientDAO().chercherPatient(nomChamp.getText(), prenomChamp.getText()).get() : null;
        if (selectedPatient == null) {
            Utility.showAlert("Ce Patient n'existe pas", "Ce Patient n'existe pas");

            return;
        }
        String path = "GererDossierMedicale/DossierMedicaleDialog.fxml";
        String title = "Dossier Medicale de " + selectedPatient.getNom() + " " + selectedPatient.getPrenom();
        newDialog(mainPanel.getScene(), title, path);


    }

    @FXML
    public void ajouterPatient() {
        String path = "GererPatient/ajouterpatientdialog.fxml";
        String title = "Ajouter Patient";
        newDialog(mainPanel.getScene(), title, path);


    }


    @FXML
    public void modifierPatient() {
        selectedPatient = (Patient) PatientsList.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            Utility.showAlert("Pas De Patient Selectionner", "S'il Te Plait Selectioner un Patient");

            return;
        }
        String path = "GererPatient/modifierpatientdialog.fxml";
        String title = "Modifier Patient";
        newDialog(mainPanel.getScene(), title, path);


    }

    @FXML
    public void supprimerPatient() {
        selectedPatient = (Patient) PatientsList.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            Utility.showAlert("Pas De Patient Selectionner", "S'il Te Plait Selectioner un Patient");

            return;
        }
        LocalDate localDate = Datasource.getInstance().queryLastConsultationDate(selectedPatient.getId());
        if (localDate != null && !LocalDate.now().isAfter(localDate.plusYears(5))) {
            Utility.showAlert("tu peut pas supprimer cette patient ", "S'il Te Plait Selectioner un Patient anciennes");

            return;
        }

        if (Utility.showConfirmationAlert("Supprimer Patient", "Vous Etes Sur De Supprimer Patient: " + selectedPatient.getNom() + " " + selectedPatient.getPrenom()))
            CabinetMedicale.getPatientDAO().supprimerPatient(selectedPatient);
    }

    public void listPatients() {

        if (PatientsList.getItems().isEmpty()) {
            System.out.println("done list patients");

            PatientsList.setItems(CabinetMedicale.getPatientDAO().getPatientList());
        }


    }


}
