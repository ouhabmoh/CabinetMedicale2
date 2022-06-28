package sample;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import sample.Datasource.Datasource;
import sample.model.Patient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PatientDAOImpl implements PatientDAO {
   static Callback<Patient, Observable[]> extractor = new Callback<Patient, Observable[]>() {
        List<Property> properties = new ArrayList<>();
        @Override
        public Observable[] call(Patient p) {
            JavaBeanObjectProperty id = null;
            JavaBeanObjectProperty nom = null;
            JavaBeanObjectProperty prenom = null;
            try {
                id = JavaBeanObjectPropertyBuilder.create()
                        .bean(p).name("id").build();
                nom = JavaBeanObjectPropertyBuilder.create()
                        .bean(p).name("nom").build();
                prenom = JavaBeanObjectPropertyBuilder.create()
                        .bean(p).name("prenom").build();
                // hack around losing weak references ...
                properties.add(id);
                properties.add(prenom);
                properties.add(nom);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return new Observable[] {nom, prenom, id};
        }
    };
    private static ObservableList<Patient> patientList = FXCollections.observableArrayList(extractor);


    public PatientDAOImpl() {
        patientList();
    }

    @Override
    public boolean ajouterPatient(Patient patient) {
        int id = Datasource.getInstance().insertPatient(patient);
       if(id > 0){
           patient.setId(id);
           patientList.add(patient);
           return true;
       }
       return false;
    }

    @Override
    public boolean supprimerPatient(Patient patient) {
        if(Datasource.getInstance().deletePatient(patient.getId())){
            patientList.remove(patient);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifierPatient(Patient patient, Patient newPatient) {
        if(Datasource.getInstance().updatePatient(newPatient)){
           patient.setId(newPatient.getId());
           patient.setNom(newPatient.getNom());
           patient.setPrenom(newPatient.getPrenom());
           return true;
        }
        return false;
    }

    @Override
    public Optional<Patient> chercherPatient(String nom, String prenom) {
        for(Patient patient:patientList){
            if(patient.getNom().equalsIgnoreCase(nom) && patient.getPrenom().equalsIgnoreCase(prenom))
                return Optional.of(patient);
        }
        return Optional.empty();
    }


    private void patientList() {
        Task<ObservableList<Patient>> taskPatient = new GetAllPatientsTask();
        taskPatient.setOnSucceeded((e) -> patientList.setAll(taskPatient.getValue()));
        new Thread(taskPatient).start();

    }

    @Override
    public void bindTable(TableView<Patient> patientTableView) {


        patientTableView.setItems(patientList);

    }

    @Override
    public  ObservableList<Patient> getPatientList()
    {
        SortedList<Patient> sortedList = new SortedList<>(patientList,
                (o1, o2) -> o1.getId() > o2.getId() ? -1 : 1);
        return sortedList;
    }

    static class GetAllPatientsTask extends Task {

        @Override
        public ObservableList<Patient> call() {

            return FXCollections.observableArrayList
                    (Datasource.getInstance().queryPatients());
        }
    }
}
