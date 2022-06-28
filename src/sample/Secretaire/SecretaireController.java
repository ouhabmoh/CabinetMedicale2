package sample.Secretaire;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Datasource.Datasource;
import sample.Login.LoginController;
import sample.Secretaire.GererRendezVous.RendezVousController;
import sample.model.RendezVous;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SecretaireController {

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private TableView<sample.model.RendezVous> RendezVous;

    @FXML
    public Pane pnlAgenda;

    private Task<ObservableList<RendezVous>> taskRendezVous;



    @FXML
    private DatePicker date;

    @FXML
    public void afficherRendezVous(){
        if(date.getValue() == null)
            return;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        listRendezVous(date.getValue().format(df));
    }

    @FXML
    public void ajouterRendezVous(){

        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Ajouter Rendez-Vous");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("GererRendezVous/RendezVousDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            RendezVousController rendezVousController = fxmlLoader.getController();
            RendezVous rendezVous = rendezVousController.getRendezVous();
            int id = Datasource.getInstance().insertRendezVous(rendezVous);
            rendezVous.setId(id);
            if(taskRendezVous.getValue() != null && taskRendezVous.getValue().get(0).getDate().equals(rendezVous.getDate()))
                taskRendezVous.getValue().add(rendezVous);
        }
    }



    @FXML
    public void supprimerRendezVous(){
        RendezVous rendezVous = RendezVous.getSelectionModel().getSelectedItem();
        int index = RendezVous.getSelectionModel().getSelectedIndex();
        if(rendezVous == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pas De Rendez-Vous Selectionner");
            alert.setHeaderText(null);
            alert.setContentText("S'il Te Plait Selectioner un Rendez-Vous");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer Rendez-Vous");
        alert.setHeaderText(null);
        alert.setContentText("Vous Etes Sur De Supprimer Rendez-Vous de: " + rendezVous.getNom() + " " +rendezVous.getPrenom());

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            if(Datasource.getInstance().supprimerRendezVous(rendezVous.getId()))
                RendezVous.getItems().remove(index);

        }
    }

    @FXML
    public void deconnecter(){
        try {
            Stage stage = (Stage) mainPanel.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Login/login.fxml"));
            Scene scene = new Scene(loader.load());
            LoginController loginController = loader.getController();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void listRendezVous(String date) {

        pnlAgenda.toFront();
        pnlAgenda.setStyle("-fx-background-color : #02030A");
        taskRendezVous = new GetAllRendezVousTask(date);
        RendezVous.itemsProperty().bind(taskRendezVous.valueProperty());
        pnlAgenda.toFront();


      /*  progressBar.progressProperty().bind(task.progressProperty());

        progressBar.setVisible(true);

        task.setOnSucceeded(e -> progressBar.setVisible(false));
        task.setOnFailed(e -> progressBar.setVisible(false));

       */

        new Thread(taskRendezVous).start();


    }

    class GetAllRendezVousTask extends Task {
        private String date ;

        public GetAllRendezVousTask(String date) {
            this.date = date;
        }

        @Override
        public ObservableList<RendezVous> call() {

            return FXCollections.observableArrayList
                    (Datasource.getInstance().queryRendezVous(date));
        }
    }
}
