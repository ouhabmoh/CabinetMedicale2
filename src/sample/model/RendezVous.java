package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class RendezVous {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nom;
    private SimpleStringProperty prenom;
    private SimpleStringProperty date;
    private SimpleStringProperty patientStatut;
    private SimpleStringProperty heure;

    public RendezVous(SimpleIntegerProperty id, SimpleStringProperty nom, SimpleStringProperty prenom, SimpleStringProperty date) {

        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
    }

    public RendezVous() {
        this.id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty();
        this.prenom = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.patientStatut = new SimpleStringProperty();
        this.heure = new SimpleStringProperty();
    }



    public int getId() {
        return id.get();
    }


    public void setId(int id) {
        this.id.set(id);
    }

    public String getNom() {
        return nom.get();
    }


    public void setNom(String nom) {
        this.nom.set(nom);
    }



    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public String getDate() {
        return date.get();
    }


    public void setDate(String date) {
        this.date.set(date);
    }

    public String getPatientStatut() {
        return patientStatut.get();
    }

    public SimpleStringProperty patientStatutProperty() {
        return patientStatut;
    }

    public void setPatientStatut(String patientStatut) {
        this.patientStatut.set(patientStatut);
    }

    public String getHeure() {
        return heure.get();
    }

    public SimpleStringProperty heureProperty() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure.set(heure);
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id.get() +
                ", nom=" + nom.get() +
                ", prenom=" + prenom.get() +
                ", date=" + date.get() +
                '}';
    }
}