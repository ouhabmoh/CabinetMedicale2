package sample.Medcin.GererUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.CabinetMedicale;
import sample.Datasource.Datasource;
import sample.Login.PasswordSecurity;
import sample.Medcin.Controller;
import sample.Validation;
import sample.model.User;
import sample.model.UserFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ModifierUserController {

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

    User selectedUser;

    public void initialize(){
        selectedUser = Controller.getSelectedUser();
        afficherUser(selectedUser);
    }



    @FXML
    public void onButtonClicked(ActionEvent e){
        if(e.getSource().equals(cancelButton)){
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
        if (e.getSource().equals(okButton)){
            if(isDataValid()){


                User newUser = getUser();
                newUser.setId(selectedUser.getId());
                if(!newUser.equals(selectedUser))
                    CabinetMedicale.getUserDAO().modifierUser(selectedUser, newUser);



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

    public void afficherUser(User user){
        usernameChamp.setText(user.getPseudo());
        motDePasse.setText(user.getMotDePasse());
        motDePasse2.setText(user.getMotDePasse());
        if(user.getRole().equalsIgnoreCase("medcin")){
            roleChamp.selectToggle((Toggle) medcin);
        }else
            roleChamp.selectToggle((Toggle) secretaire);

        nomChamp.setText(user.getNom());
        prenomChamp.setText(user.getPrenom());
        dDNChamp.setValue(LocalDate.parse(user.getDateDeNaissance(),DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        adresseChamp.setText(user.getAdresse());
        telChamp.setText(user.getTel());
        emailChamp.setText(user.getEmail());
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

        if(!usernameChamp.getText().equalsIgnoreCase(selectedUser.getPseudo())){
            if(CabinetMedicale.getUserDAO().verifyUsername(usernameChamp.getText())){
                lblErrors.setText("invalid username");
                return false;
            }

        }

        return true;

    }


}
