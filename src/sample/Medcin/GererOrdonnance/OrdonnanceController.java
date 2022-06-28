package sample.Medcin.GererOrdonnance;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.model.Medicament;
import sample.model.Ordonnance;

public class OrdonnanceController {


    @FXML
    private TextArea observation;

    @FXML
    private TextField medicamentNom;

    @FXML
    private TextField dose;

    @FXML
    private TextField astuce;

    @FXML
    private TextField duration;

    @FXML
    private Label lblErrors;

    @FXML
    private Button ajouterMedicament;

    @FXML
    private TableView<Medicament> MedicamentsList;

    @FXML
    public void ajouterMedicament(){
       if(medicamentNom.getText().isEmpty() || medicamentNom.getText().trim().isEmpty()){
           lblErrors.setText("Medicament must have a name");
            return;
       }

        Medicament medicament = new Medicament();
        medicament.setNom(medicamentNom.getText());
        medicament.setDose(dose.getText());
        medicament.setAstuce(astuce.getText());
        medicament.setDuration(duration.getText());

        MedicamentsList.getItems().add(medicament);

        medicamentNom.clear();
        dose.clear();
        astuce.clear();
        duration.clear();
    }

    @FXML
    public void supprimerMedicament(){
        int index = MedicamentsList.getSelectionModel().getSelectedIndex();
        MedicamentsList.getItems().remove(index);
    }

    public Ordonnance getOrdonnance(){
        Ordonnance ordonnance = new Ordonnance();
        ordonnance.setObservation(observation.getText());
        ordonnance.setMedicamentList(MedicamentsList.getItems());
        return ordonnance;
    }

    public void afficherOrdonnance(Ordonnance ordonnance){
        observation.setText(ordonnance.getObservation());
        MedicamentsList.getItems().addAll(ordonnance.getMedicamentList());

    }

    /*@FXML
    public void handleKeyReleased() {
        String text = medicamentNom.getText();
        boolean disableButton = text.isEmpty() || text.trim().isEmpty();
        ajouterMedicament.setDisable(disableButton);
        if(!disableButton)
            lblErrors.setText("Medicament must have a name");

    }

     */

    public boolean validData(){
        if (observation.getText().isEmpty() || observation.getText().trim().isEmpty()){
            lblErrors.setText("Observation Field Can't be empty");
            return false;
        }

        return true;
    }


}
