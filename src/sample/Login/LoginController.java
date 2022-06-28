package sample.Login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Datasource.Datasource;
import sample.Medcin.Controller;
import sample.Secretaire.SecretaireController;
import sample.model.Medcin;
import sample.model.Secretaire;
import sample.model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoginController  {

    private String fileToLoad = "";

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    /// --

    public static User user;

    @FXML
    public void handleButtonAction(MouseEvent event) {

        if (event.getSource() == btnSignin) {

            if (logIn().equals("Success")) {
                try {


                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();

                    stage.close();



                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fileToLoad));
                    Scene scene = new Scene(loader.load());
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    String date = LocalDate.now().format(df);
                    if(user instanceof Medcin){
                        Controller controller = loader.getController();
                        controller.listRendezVous(date);
                        controller.pnlAgenda.toFront();
                        controller.afficherMedcinInfo(user);
                    }else{
                        SecretaireController secretaireController = loader.getController();
                        secretaireController.listRendezVous(date);
                        secretaireController.pnlAgenda.toFront();

                    }

                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println("failed to load file"+ex.getMessage()+ex.getStackTrace()+ex.getCause());
                }



            }
        }
    }

    public static User getUser(){
        return user;
    }




    private String logIn() {
        String status = "Success";
        String username = txtUsername.getText();
        System.out.println(username);
        String password = txtPassword.getText();
        if(username.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Vide");
            status = "Error";
        } else {
            //query

            try {
                List<String> loginInfo = Datasource.getInstance().login(username);

                if(loginInfo == null || !PasswordSecurity.validatePassword(password,loginInfo.get(1))){

                    setLblError(Color.TOMATO, "Entrer Valid Nom D'Utilisataire/Mot De Passe");
                    return status = "Error";
                }

               user = Datasource.getInstance().queryUser(Integer.parseInt(loginInfo.get(0)));





                 if(user instanceof Medcin){
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                    fileToLoad = "/sample/Medcin/Medcin1.fxml";
                //     sample\Medcin\Medcin1.fxml
                     //C:\Users\PC\Desktop\CabinetMedicale\src\sample\Medcin\Medcin1.fxml
                } else if(user instanceof Secretaire){
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                    fileToLoad = "/sample/Secretaire/Secretaire.fxml";
                }else {
                    setLblError(Color.TOMATO, "Entrer Valid Nom D'Utilisataire/Mot De Passe");

                    status = "Error";
                }
            } catch (Exception ex) {
                System.err.println(" exception "+ex.getMessage());
                status = "Exception";
            }
        }
        
        return status;
    }
    
    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }



}
