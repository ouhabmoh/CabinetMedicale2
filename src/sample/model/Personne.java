package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class  Personne {
    protected SimpleIntegerProperty id;
    protected SimpleStringProperty nom;
    protected SimpleStringProperty prenom;
    protected SimpleStringProperty dateDeNaissance;
    protected SimpleStringProperty adresse;
    protected SimpleStringProperty email;
    protected SimpleStringProperty tel;

    public Personne(SimpleIntegerProperty id, SimpleStringProperty nom, SimpleStringProperty prenom, SimpleStringProperty dateDeNaissance, SimpleStringProperty adresse, SimpleStringProperty email, SimpleStringProperty tel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.email = email;
        this.tel = tel;
    }

    public Personne() {
        this.id = new SimpleIntegerProperty();
        this.nom = new SimpleStringProperty();
        this.prenom = new SimpleStringProperty();
        this.dateDeNaissance = new SimpleStringProperty();
        this.adresse = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.tel = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNom() {
        return nom.get();
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        if(nom == null || nom.isEmpty() || nom.trim().isEmpty())
            this.nom.set(" ");
        else
            this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        if(prenom == null || prenom.isEmpty() || prenom.trim().isEmpty())
            this.prenom.set(" ");
        else
            this.prenom.set(prenom);
    }

    public String getDateDeNaissance() {
        return dateDeNaissance.get();
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        if(dateDeNaissance == null || dateDeNaissance.isEmpty() || dateDeNaissance.trim().isEmpty())
            this.dateDeNaissance.set(" ");
        else
            this.dateDeNaissance.set(dateDeNaissance);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public SimpleStringProperty adresseProperty() {
        return adresse;
    }

    public void setAdresse(String adresse) {

        if(adresse == null || adresse.isEmpty() || adresse.trim().isEmpty())
            this.adresse.set(" ");
        else
            this.adresse.set(adresse);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isEmpty() || email.trim().isEmpty())
            this.email.set(" ");
        else
            this.email.set(email);

    }

    public String getTel() {
        return tel.get();
    }

    public SimpleStringProperty telProperty() {
        return tel;
    }

    public void setTel(String tel) {
        if(tel == null || tel.isEmpty() || tel.trim().isEmpty())
            this.tel.set(" ");
        else
            this.tel.set(tel);
    }
}


