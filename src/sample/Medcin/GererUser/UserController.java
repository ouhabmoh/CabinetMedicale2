package sample.Medcin.GererUser;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import sample.CabinetMedicale;
import sample.Datasource.Datasource;
import sample.Login.PasswordSecurity;
import sample.Utility;
import sample.model.User;
import sample.model.UserFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class UserController {

    @FXML
    private TableView<User> UsersList;

    @FXML
    private AnchorPane mainPanel;



    @FXML
    private Pane pnlUser;



    private static User selectedUser;




    public static User getSelectedUser() {
        return selectedUser;
    }

    public void initialize(){
        listUsers();
    }

    public void listUsers() {
        if (UsersList.getItems().isEmpty())
            UsersList.setItems(CabinetMedicale.getUserDAO().getUsers());

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
    public void ajouterUser() {
        String titre = "Ajouter User";
        String path = "GererUser/ajouteruserdialog.fxml";
        newDialog(mainPanel.getScene(), titre, path);

    }

    @FXML
    public void modifierUser() {
        selectedUser = UsersList.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Utility.showAlert("Pas De User Selectionner", "S'il Te Plait Selectioner un User");

            return;
        }
        String titre = "Modifier User";
        String path = "GererUser/modifieruserdialog.fxml";
        newDialog(mainPanel.getScene(), titre, path);

    }


    @FXML
    public void supprimerUser() {
        User user = UsersList.getSelectionModel().getSelectedItem();
        int index = UsersList.getSelectionModel().getSelectedIndex();
        if (user == null) {
            Utility.showAlert("Pas De User Selectionner", "S'il Te Plait Selectioner un User");

            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer User");
        alert.setHeaderText(null);
        alert.setContentText("Vous Etes Sur De Supprimer Patient: " + user.getNom() + " " + user.getPrenom());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            CabinetMedicale.getUserDAO().supprimerUser(user);

        }
    }

}
