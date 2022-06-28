package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Personne{
    private SimpleStringProperty code;
    private SimpleStringProperty maladies  ;
    private SimpleIntegerProperty poids;
    private SimpleIntegerProperty longueur;
    private SimpleStringProperty grSang;
    private SimpleStringProperty date1;
    private List<Consultation> consultationList;

    public Patient(SimpleIntegerProperty id, SimpleStringProperty nom, SimpleStringProperty prenom, SimpleStringProperty dateDeNaissance, SimpleStringProperty adresse, SimpleStringProperty email, SimpleStringProperty tel, SimpleStringProperty maladies, SimpleIntegerProperty poids, SimpleIntegerProperty longueur, SimpleStringProperty grSang, SimpleStringProperty date1) {
        super(id, nom, prenom, dateDeNaissance, adresse, email, tel);
        this.maladies = maladies;
        this.poids = poids;
        this.longueur = longueur;
        this.grSang = grSang;
        this.date1 = date1;
    }

    public Patient() {
        this.code = new SimpleStringProperty();
        this.maladies = new SimpleStringProperty();
        this.poids = new SimpleIntegerProperty();
        this.longueur = new SimpleIntegerProperty();
        this.grSang = new SimpleStringProperty();
        this.date1 = new SimpleStringProperty();
    }

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public SimpleStringProperty maladiesProperty() {
        return maladies;
    }

    public String getMaladies() {
        return maladies.getValue();
    }

    public void setMaladies(String maladies) {
        this.maladies.set(maladies);
    }

    public int getPoids() {
        return poids.get();
    }

    public int poidsProperty() {
        return poids.getValue();
    }

    public void setPoids(int poids) {
        this.poids.set(poids);
    }

    public int getLongueur() {
        return longueur.get();
    }

    public int longueurProperty() {
        return longueur.getValue();
    }

    public void setLongueur(int longueur) {
        this.longueur.set(longueur);
    }

    public String getGrSang() {
        return grSang.get();
    }

    public SimpleStringProperty grSangProperty() {
        return grSang;
    }

    public void setGrSang(String grSang) {
        this.grSang.set(grSang);
    }

    public String getDate1() {
        return date1.get();
    }

    public SimpleStringProperty date1Property() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1.set(date1);
    }

    public List<Consultation> getConsultationList() {
        return consultationList;
    }

    public void setConsultationList(List<Consultation> consultationList) {

        this.consultationList = new ArrayList<>(consultationList);

    }

    @Override
    public String toString() {
        return "Patient{" +
                "maladies=" + maladies +
                ", poids=" + poids +
                ", longueur=" + longueur +
                ", grSang=" + grSang +
                ", date1=" + date1 +
                ", id=" + id +
                ", nom=" + nom +
                ", prenom=" + prenom +
                ", dateDeNaissance=" + dateDeNaissance +
                ", adresse=" + adresse +
                ", email=" + email +
                ", tel=" + tel +
                '}';
    }
}
