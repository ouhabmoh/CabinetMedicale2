package sample.Medcin.GererPatient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.CabinetMedicale;
import sample.OperationType;
import sample.Validation;
import sample.model.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AjouterPatientController {

    @FXML
    private TextField nomChamp;

    @FXML
    private TextField prenomChamp;

    @FXML
    private DatePicker dDNChamp;

    @FXML
    private ComboBox grSangChamp;

    @FXML
    private TextField maladiesChamp;

    @FXML
    private TextField adresseChamp;

    @FXML
    private TextField telChamp;

    @FXML
    private TextField emailChamp;

    @FXML
    private TextField longueurChamp;

    @FXML
    private TextField poidChamp;

    @FXML
    private Label lblErrors;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    private OperationType operationType;

    private Patient patient;

    private int index;

    @FXML
    public void initialize(){
        Validation.numericField(poidChamp);
        Validation.numericField(longueurChamp);
    }




    @FXML
    public void onButtonClicked(ActionEvent e){
        if(e.getSource().equals(cancelButton)){
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
        if (e.getSource().equals(okButton)){
            if(validData()){


                        Patient patient01 = getNewPatient();
                        if(CabinetMedicale.getPatientDAO().ajouterPatient(patient01))
                            System.out.println("patient ajouter");



                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        }

    }

    public Patient getNewPatient(){
        Patient patient = new Patient();
        patient.setNom(nomChamp.getText());
        patient.setPrenom(prenomChamp.getText());
        patient.setAdresse(adresseChamp.getText());
        patient.setDateDeNaissance(dDNChamp.getValue().toString());
        patient.setMaladies(maladiesChamp.getText());
        patient.setLongueur(Validation.getNumber(longueurChamp.getText()));
        patient.setPoids(Validation.getNumber(poidChamp.getText()));
        patient.setDate1(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        patient.setEmail(emailChamp.getText());
        patient.setGrSang((String) grSangChamp.getSelectionModel().getSelectedItem());
        patient.setTel(telChamp.getText());


        return patient;
    }



    public Boolean validData(){

        if(!Validation.hasContent(nomChamp.getText())){
            lblErrors.setText("s'il te plait ajouter un nom");
            return false;
        }

        if(!Validation.hasContent(prenomChamp.getText())){
            lblErrors.setText("s'il te plait ajouter un prenom");
            return false;
        }

        if(!Validation.ensureNotNull(dDNChamp.getValue())){
            lblErrors.setText("s'il te plait ajouter date de naissance");
            return false;
        }

        return true;

    }





}
