package sample.Medcin.GererDossierMedicale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Datasource.Datasource;
import sample.Login.LoginController;
import sample.Medcin.Controller;
import sample.Medcin.GererConsultation.ConsultationController;
import sample.Medcin.GererOrdonnance.ImprimerOrdonnanceController;
import sample.Medcin.GererOrdonnance.OrdonnanceController;
import sample.Medcin.GererPatient.PatientController;
import sample.model.Consultation;
import sample.model.Ordonnance;
import sample.model.Patient;
import sample.model.User;

import java.io.IOException;
import java.util.Optional;

public class DossierMedicaleController {

    @FXML
    private AnchorPane main;



    @FXML
    private Label idChamp;

    @FXML
    private Label nomChamp;

    @FXML
    private Label prenomChamp;

    @FXML
    private Label dDNChamp;

    @FXML
    private Label grSangChamp;

    @FXML
    private Label maladiesChamp;

    @FXML
    private Label adresseChamp;

    @FXML
    private Label telChamp;

    @FXML
    private Label emailChamp;

    @FXML
    private Label longueurChamp;

    @FXML
    private Label poidChamp;

    @FXML
    private TableView<Consultation> ConsultationsList;

    @FXML
    private Label consultationCodeChamp;

    @FXML
    private Label diagnosticChamp;

    @FXML
    private Label maladiesCChamp;

    @FXML
    private Label observationChamp;

    @FXML
    private Label montantPayeChamp;

    @FXML
    private Label dateCChamp;

    private Task<ObservableList<Consultation>> consultaionTask;

    private Patient patient;

    public void initialize(){
        patient = PatientController.getSelectedPatient();
        afficherPatient(patient);
    }

    public void afficherPatient(Patient patient) {

        idChamp.setText(String.valueOf(patient.getId()));
        nomChamp.setText(patient.getNom());
        prenomChamp.setText(patient.getPrenom());
        dDNChamp.setText(patient.getDateDeNaissance());
        adresseChamp.setText(patient.getAdresse());
        telChamp.setText(patient.getTel());
        emailChamp.setText(patient.getEmail());
        poidChamp.setText(String.valueOf(patient.getPoids()));
        longueurChamp.setText(String.valueOf(patient.getLongueur()));
        maladiesChamp.setText(patient.getMaladies());
        grSangChamp.setText(patient.getGrSang());

    }

    @FXML
    public void afficherConsultation() {
        Consultation consultation = (Consultation) ConsultationsList.getSelectionModel().getSelectedItem();
        if (consultation == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pas De Consultation Selectionner");
            alert.setHeaderText(null);
            alert.setContentText("S'il Te Plait Selectioner un Consultation");
            alert.showAndWait();
            return;
        }
        consultationCodeChamp.setText(String.valueOf(consultation.getCode()));
        diagnosticChamp.setText(consultation.getDiagnostic());
        maladiesCChamp.setText(consultation.getMaladies());
        observationChamp.setText(consultation.getObservation());
        montantPayeChamp.setText(String.valueOf(consultation.getMontantPayee()));
        dateCChamp.setText(consultation.getDate());

    }

    @FXML
    public void ajouterConsultation() {
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(main.getScene().getWindow());
        dialog.setTitle("Ajouter Consultation");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Medcin/GererConsultation/ConsultationDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }


        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {


            ConsultationController consultationController = fxmlLoader.getController();

            if(consultationController.validData()) {
                Consultation consultation = consultationController.getNewConsultation();
                User user = LoginController.getUser();
                String medcinNom = user.getNom() + " " + user.getPrenom();

                consultation.setMedcin(medcinNom);
                int patientID = patient.getId();
                int consultationCount = Datasource.getInstance().countConsultations();
                String code = "C."+consultation.getDate()+"."+consultationCount+"."+patientID;
                consultation.setCode(code);
                int consultationID = (Datasource.getInstance().insertConsultation(patientID,  consultation));
                if (consultationID != -1) {
                    consultation.setId(consultationID);


                    ConsultationsList.getItems().add(consultation);
                    if (consultation.getOrdonnance() != null) {
                        int ordonnanceId = Datasource.getInstance().insertOrdonnance(consultationID, consultation.getOrdonnance());
                        if (ordonnanceId != -1)
                            consultation.getOrdonnance().setId(ordonnanceId);
                    }
                }
            }

            }catch (Exception e){
                System.out.println("failed to insert Consultation "+e.getMessage());
            }



        }
    }

    @FXML
    public void modifierConsultation() {
        Consultation consultation = (Consultation) ConsultationsList.getSelectionModel().getSelectedItem();
       int index = ConsultationsList.getSelectionModel().getSelectedIndex();
        if (consultation == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pas De Consultation Selectionner");
            alert.setHeaderText(null);
            alert.setContentText("S'il Te Plait Selectioner un Consultation");
            alert.showAndWait();
            return;
        }
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(main.getScene().getWindow());
        dialog.setTitle("Modifier Consultation");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Medcin/GererConsultation/ConsultationDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        ConsultationController consultationController = fxmlLoader.getController();
        consultationController.afficherConsultation(consultation);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if(consultationController.validData()){
                Consultation newConsultation = consultationController.getNewConsultation();
                if(!consultation.equals(newConsultation)){
                    newConsultation.setId(consultation.getId());
                    newConsultation.setMedcin(consultation.getMedcin());

                    if (Datasource.getInstance().updateConsultation(newConsultation))
                        ConsultationsList.getItems().set(index,newConsultation);

                }

            }
        }
    }

    @FXML
    public void supprimerConsultation(){
        Consultation consultation = (Consultation) ConsultationsList.getSelectionModel().getSelectedItem();
        int index = ConsultationsList.getSelectionModel().getSelectedIndex();
        if(consultation == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pas De Consultation Selectionner");
            alert.setHeaderText(null);
            alert.setContentText("S'il Te Plait Selectioner un Consultation");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer Consultation");
        alert.setHeaderText(null);
        alert.setContentText("Vous Etes Sur De Supprimer Consultation: " + consultation.getId());

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            if(Datasource.getInstance().deleteConsultation(consultation.getId()))
            ConsultationsList.getItems().remove(index);

        }
    }


    public void getConsultationsList() {
        consultaionTask = new GetConsultationsListTask();
        ConsultationsList.itemsProperty().bind(consultaionTask.valueProperty());
        new Thread(consultaionTask).start();
    }



    @FXML
    public void afficherOrdonnance() {
        Consultation consultation = (Consultation) ConsultationsList.getSelectionModel().getSelectedItem();
        if (consultation == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pas De Consultation Selectionner");
            alert.setHeaderText(null);
            alert.setContentText("S'il Te Plait Selectioner un Consultation");
            alert.showAndWait();
            return;
        }
        if(consultation.getOrdonnance() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Il n'y'a pas d'ordonnance");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y'a pas d'ordonnance");
            alert.showAndWait();
            return;
        }
        FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/sample/Medcin/GererOrdonnance/Ordonnance.fxml"));
        try {
            Parent root = fXMLLoader.load();
            ImprimerOrdonnanceController imprimerOrdonnanceController = fXMLLoader.getController();
            imprimerOrdonnanceController.loadPrescription(consultation.getOrdonnance(),patient,consultation.getDate());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Imprimer Ordonnance");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException ex) {
            System.out.println("ordonnace failed in dossier medicale"+ ex.getMessage());
        }
    }

    @FXML
    public void ajouterOrdonnance(){
        Consultation consultation = (Consultation) ConsultationsList.getSelectionModel().getSelectedItem();
        if(consultation == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pas De Consultation Selectionner");
            alert.setHeaderText(null);
            alert.setContentText("S'il Te Plait Selectioner un Consultation");
            alert.showAndWait();
            return;
        }

        if(consultation.getOrdonnance() != null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Il y'a deja un ordonnance pour cette consultation");
            alert.setHeaderText(null);
            alert.setContentText("Il y'a deja un ordonnance pour cette consultation");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(main.getScene().getWindow());
        dialog.setTitle("Ajouter Ordonnance");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Medcin/GererOrdonnance/OrdonnanceDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();

        }
        System.out.println("done1");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            OrdonnanceController ordonnanceController = fxmlLoader.getController();
            Ordonnance ordonnance = ordonnanceController.getOrdonnance();
            int ordonnanceId = Datasource.getInstance().insertOrdonnance(consultation.getId(),ordonnance);
            if(ordonnanceId != -1){
                ordonnance.setId(ordonnanceId);
                consultation.setOrdonnance(ordonnance);
            }


        }


    }

    @FXML
    public void modifierOrdonnance(){
        Consultation consultation = (Consultation) ConsultationsList.getSelectionModel().getSelectedItem();
        int index = ConsultationsList.getSelectionModel().getSelectedIndex();
        if(consultation == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pas De Consultation Selectionner");
            alert.setHeaderText(null);
            alert.setContentText("S'il Te Plait Selectioner un Consultation");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(main.getScene().getWindow());
        dialog.setTitle("Ajouter Ordonnance");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/Medcin/GererOrdonnance/OrdonnanceDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();

        }
        System.out.println("done1");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        OrdonnanceController ordonnanceController = fxmlLoader.getController();
        ordonnanceController.afficherOrdonnance(consultation.getOrdonnance());


        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Ordonnance newOrdonnance = ordonnanceController.getOrdonnance();
            newOrdonnance.setId(consultation.getOrdonnance().getId());
            if(Datasource.getInstance().updateOrdonnance(newOrdonnance))
                consultation.setOrdonnance(newOrdonnance);

        }
    }

    @FXML
    public void supprimerOrdonnance(){
        Consultation consultation =  ConsultationsList.getSelectionModel().getSelectedItem();

        if(consultation == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pas De Consultation Selectionner");
            alert.setHeaderText(null);
            alert.setContentText("S'il Te Plait Selectioner un Consultation");
            alert.showAndWait();
            return;
        }
        if(consultation.getOrdonnance() == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Il n'y'a pas d'ordonnance");
            alert.setHeaderText(null);
            alert.setContentText("Il n'y'a pas d'ordonnance");
            alert.showAndWait();
            return;
        }
        int id = consultation.getOrdonnance().getId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer Ordonnance");
        alert.setHeaderText(null);
        alert.setContentText("Vous Etes Sur De Supprimer Ordonnance: " + id);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            if (Datasource.getInstance().deleteOrdonnance(id))
                consultation.setOrdonnance(null);
        }


    }

    //Task<ObservableList<Consultation>>

    class GetConsultationsListTask extends Task {
        @Override
        public ObservableList<Consultation> call() {
            System.out.println(Integer.parseInt(idChamp.getText()));
            return FXCollections.observableArrayList
                    (Datasource.getInstance().queryConsultations(Integer.parseInt(idChamp.getText())));
        }
    }
}
