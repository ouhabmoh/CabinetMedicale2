package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class User extends Personne {
    protected SimpleStringProperty pseudo = new SimpleStringProperty();
    protected SimpleStringProperty role = new SimpleStringProperty();
    protected String motDePasse;

    public User(SimpleIntegerProperty id, SimpleStringProperty nom, SimpleStringProperty prenom, SimpleStringProperty dateDeNaissance, SimpleStringProperty adresse, SimpleStringProperty email, SimpleStringProperty tel, SimpleStringProperty pseudo) {
        super(id, nom, prenom, dateDeNaissance, adresse, email, tel);
        this.pseudo = pseudo;
    }

    public User() {
        this.pseudo = new SimpleStringProperty();
    }

    public String getPseudo() {
        return pseudo.get();
    }

    public SimpleStringProperty pseudoProperty() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo.set(pseudo);
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    @Override
    public boolean equals(Object obj) {
        User user = (User) obj;
       if(!(pseudo.get().equals(user.getPseudo()))){
           return false;
       }

       if(!(motDePasse.equals(user.getMotDePasse()))){
           return false;
       }
       if(!(nom.get().equals(user.getNom()))){
           return false;
       }
       if(!(prenom.get().equals(user.getPrenom()))){
           return false;
       }
       if(!(role.get().equals(user.getRole()))){
           return false;
       }
       if(!(dateDeNaissance.get().equals(user.getDateDeNaissance()))){
           return false;
       }
       if(!(adresse.get().equals(user.getAdresse()))){
           return false;
       }
       if(!(tel.get().equals(user.getTel()))){
           return false;
       }
       if(!(email.get().equals(user.getEmail()))){
           return false;
       }


       return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "pseudo='" + pseudo + '\'' +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateDeNaissance=" + dateDeNaissance +
                ", adresse=" + adresse +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
