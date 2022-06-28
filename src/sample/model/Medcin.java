package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Medcin extends User {
    public Medcin(SimpleIntegerProperty id, SimpleStringProperty nom, SimpleStringProperty prenom, SimpleStringProperty dateDeNaissance, SimpleStringProperty adresse, SimpleStringProperty email, SimpleStringProperty tel, SimpleStringProperty pseudo) {
        super(id, nom, prenom, dateDeNaissance, adresse, email, tel, pseudo);
    }

    public Medcin() {
    }
}
