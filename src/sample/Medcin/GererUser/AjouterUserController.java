package sample.Medcin.GererUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.CabinetMedicale;
import sample.Datasource.Datasource;
import sample.Login.PasswordSecurity;
import sample.Validation;
import sample.model.Patient;
import sample.model.User;
import sample.model.UserFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AjouterUserController {

    @FXML
    private TextField usernameChamp;

    @FXML
    private PasswordField motDePasse;

    @FXML
    private PasswordField motDePasse2;

    @FXML
    private ToggleGroup roleChamp;

    @FXML
    private TextField nomChamp;

    @FXML
    private TextField prenomChamp;

    @FXML
    private DatePicker dDNChamp;

    @FXML
    private TextField adresseChamp;

    @FXML
    private TextField telChamp;

    @FXML
    private TextField emailChamp;

    @FXML
    private RadioButton medcin;

    @FXML
    private RadioButton secretaire;

    @FXML
    private Label lblErrors;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    @FXML
    public void onButtonClicked(ActionEvent e){
        if(e.getSource().equals(cancelButton)){
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
        if (e.getSource().equals(okButton)){
            if(isDataValid()){


                User user = getUser();
                if(CabinetMedicale.getUserDAO().ajouterUser(user))
                    System.out.println("patient ajouter");



                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        }

    }

    public User getUser(){

        UserFactory userFactory = new UserFactory();
        RadioButton radioButton = (RadioButton) roleChamp.getSelectedToggle();
        String role = radioButton.getText();
        User user = userFactory.getUser(role);
        user.setPseudo(usernameChamp.getText());
        if(!(motDePasse.getText().charAt(4) == ':')){
            try {
                user.setMotDePasse(PasswordSecurity.generateStorngPasswordHash(motDePasse.getText()));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }else
            user.setMotDePasse(motDePasse.getText());


        user.setRole(role);
        user.setNom(nomChamp.getText());
        user.setPrenom(prenomChamp.getText());
        user.setDateDeNaissance(dDNChamp.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        user.setAdresse(adresseChamp.getText());
        user.setTel(telChamp.getText());
        user.setEmail(emailChamp.getText());
        return user;

    }


    public Boolean isDataValid(){

        if(!Validation.hasContent(usernameChamp.getText())){
            lblErrors.setText("s'il te plait ajouter un username");
            return false;
        }
        if(!Validation.hasContent(motDePasse.getText()))
        {
            lblErrors.setText("s'il te plait ajouter un mot de passe");
            return false;
        }else if(motDePasse.getText().compareTo(motDePasse2.getText()) != 0){
            lblErrors.setText("s'il te plait confirmer le mot de passe");
            return false;
        }

        if(!Validation.hasContent(nomChamp.getText())){
            lblErrors.setText("s'il te plait ajouter un nom");
            return false;
        }

        if(!Validation.hasContent(prenomChamp.getText())){
            lblErrors.setText("s'il te plait ajouter un prenom");
            return false;
        }


        if(CabinetMedicale.getUserDAO().verifyUsername(usernameChamp.getText())){
            lblErrors.setText("Pseudo déja existe");
            return false;
        }

        return true;

    }


}
