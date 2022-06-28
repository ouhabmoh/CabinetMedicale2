package sample.Secretaire.GererRendezVous;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.model.RendezVous;

import java.time.format.DateTimeFormatter;

public class RendezVousController {

    @FXML
    private TextField nomChamp;

    @FXML
    private TextField prenomChamp;

    @FXML
    private DatePicker dateChamp;

    @FXML
    private ComboBox heure;

    @FXML
    private ComboBox min;

    public RendezVous getRendezVous(){
        RendezVous rendezVous = new RendezVous();
        rendezVous.setNom(nomChamp.getText());
        rendezVous.setPrenom(prenomChamp.getText());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        rendezVous.setDate(dateChamp.getValue().format(df));
        String strH = (String) heure.getSelectionModel().getSelectedItem();
        String strM = (String) min.getSelectionModel().getSelectedItem();
        rendezVous.setHeure(strH+":"+strM);
        return rendezVous;
    }
}
