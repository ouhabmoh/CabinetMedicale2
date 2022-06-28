package sample.Medcin.GererConsultation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sample.Medcin.GererOrdonnance.OrdonnanceController;
import sample.model.Consultation;
import sample.model.Ordonnance;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ConsultationController {

    @FXML
    private AnchorPane main1;

    @FXML
    private TextArea diagnosticChamp;

    @FXML
    private TextField maladiesChamp;

    @FXML
    private TextArea observationChamp;

    @FXML
    private TextField montantPayeChamp;

    @FXML
    private DatePicker dateChamp;

    @FXML
    private Label lblErrors;

    Ordonnance ordonnance = null;

    public Consultation getNewConsultation(){

        Consultation consultation = new Consultation();
        consultation.setDiagnostic(diagnosticChamp.getText());
        consultation.setMaladies(maladiesChamp.getText());
        consultation.setObservation(observationChamp.getText());
        consultation.setMontantPayee(Integer.parseInt(montantPayeChamp.getText()));
        consultation.setDate(dateChamp.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        consultation.setOrdonnance(ordonnance);




        return consultation;
    }

    public void afficherConsultation(Consultation consultation){
        diagnosticChamp.setText(consultation.getDiagnostic());
        maladiesChamp.setText(consultation.getMaladies());
        observationChamp.setText(consultation.getObservation());
        montantPayeChamp.setText(String.valueOf(consultation.getMontantPayee()));
        dateChamp.setValue(LocalDate.parse(consultation.getDate(),DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    @FXML
    public void ajouterOrdonnance(){
        System.out.println("done");
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(main1.getScene().getWindow());
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
            ordonnance = ordonnanceController.getOrdonnance();
        }

    }

    public boolean validData(){
        lblErrors.setTextFill(Color.TOMATO);
        if(diagnosticChamp.getText().isEmpty() || diagnosticChamp.getText().trim().isEmpty()){
            lblErrors.setText("diagnostic shouldn't be empty");
            return false;
        }

        if(observationChamp.getText().isEmpty() || observationChamp.getText().trim().isEmpty()){
            lblErrors.setText("observation field shouldn't be empty");
            return false;
        }
        if(maladiesChamp.getText().isEmpty() || maladiesChamp.getText().trim().isEmpty()){
            lblErrors.setText("maladies field shouldn't be empty");
            return false;
        }
        if(montantPayeChamp.getText().isEmpty()){
            lblErrors.setText("montant payee field shouldn't be empty");
            return false;

        }else {
            int d;
            try {
                d =  Integer.parseInt(montantPayeChamp.getText());
            } catch (NumberFormatException e) {
                lblErrors.setText("montant payee field must be a digital number");
                return false;
            }
            if(d < 0){
                lblErrors.setText("montant payée shouldn't be a negative number");
                return false;
            }
        }
        if (dateChamp.getValue() == null){
            lblErrors.setText("date field souldn't be empty");
            return false;
        }


        return true;
    }
}
