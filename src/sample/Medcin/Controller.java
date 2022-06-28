package sample.Medcin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Datasource.Datasource;
import sample.Login.LoginController;
import sample.model.RendezVous;
import sample.model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private TableView<sample.model.RendezVous> RendezVous;

   @FXML
   private StackPane stackPane;

    @FXML
    private TableView<User> UsersList;


    @FXML
    private Button btnAgenda;

    @FXML
    private Button btnPatient;


    @FXML
    private Button btnUser;


    @FXML
    private Button btnSignout;


    private Pane panePatient;


    @FXML
    public Pane pnlAgenda;


    private Pane pnlUser;

    @FXML
    private DatePicker date;



    @FXML
    private Label medcinId;

    @FXML
    private Label medcinUsername;

    @FXML
    private Label medcinNom;


    private static User selectedUser;




    public static User getSelectedUser() {
        return selectedUser;
    }



    private Task<ObservableList<RendezVous>> taskRendezVous;

    public void initialize(){

    }

    public Pane loadPane(String path){
        Pane pane = null;
        try{
            pane = FXMLLoader.load(getClass().getResource(path));


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return pane;
    }

    public void addPane(Pane panel){
        try{

            stackPane.getChildren().add(panel);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void showThis(Pane pane){
        for(Node node:stackPane.getChildren()){
            if(node.equals(pane))
                pane.setVisible(true);
            else
                node.setVisible(false);
        }
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnPatient) {
            if(panePatient == null){
                panePatient = loadPane("patientpanel.fxml");
                addPane(panePatient);

            }
            showThis(panePatient);



        }
        if (actionEvent.getSource() == btnUser) {
           if(pnlUser == null){
               pnlUser = loadPane("userpanel.fxml");
               addPane(pnlUser);
           }
            showThis(pnlUser);


        }
        if (actionEvent.getSource() == btnAgenda) {
            pnlAgenda.setStyle("-fx-background-color : #fff");
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            listRendezVous(LocalDate.now().format(df));
        }

        if (actionEvent.getSource() == btnSignout) {
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
    }

    @FXML
    public void afficherRendezVous() {
        if (date.getValue() == null)
            return;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        listRendezVous(date.getValue().format(df));

    }



    public void listRendezVous(String date) {

        pnlAgenda.toFront();

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










    public void afficherMedcinInfo(User user) {
        medcinId.setText(String.valueOf(user.getId()));
        medcinUsername.setText(user.getPrenom());
        medcinNom.setText(user.getNom() + " " + user.getPrenom());
    }




    class GetAllRendezVousTask extends Task {
        private String date;

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
