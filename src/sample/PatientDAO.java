package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientDAO {

boolean ajouterPatient(Patient patient);

boolean supprimerPatient(Patient patient);

boolean modifierPatient(Patient patient, Patient newPatient);

Optional<Patient> chercherPatient(String nom, String prenom);


ObservableList<Patient> getPatientList();

void bindTable(TableView<Patient> patientTableView);

}
