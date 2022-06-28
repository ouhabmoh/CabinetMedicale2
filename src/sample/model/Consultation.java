package sample.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Consultation {
    private int id;
    private SimpleStringProperty code;
    private String diagnostic;
    private SimpleStringProperty medcin;
    private SimpleStringProperty date;
    private SimpleStringProperty maladies;
    private String observation;
    private int montantPayee;
    private Ordonnance ordonnance;

    public Consultation(int id, String diagnostic, SimpleStringProperty date, SimpleStringProperty maladies, String observation, int montantPayee, Ordonnance ordonnance) {
        this.id = id;
        this.diagnostic = diagnostic;
        this.date = date;
        this.maladies = maladies;
        this.observation = observation;
        this.montantPayee = montantPayee;
        this.ordonnance = ordonnance;
    }

    public Consultation() {
        this.code = new SimpleStringProperty();
        this.medcin = new SimpleStringProperty();
        this.date = new SimpleStringProperty();
        this.maladies = new SimpleStringProperty();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        if(diagnostic == null)
            this.diagnostic = " ";
        else
            this.diagnostic = diagnostic;
    }

    public String getMedcin() {
        return medcin.get();
    }

    public SimpleStringProperty medcinProperty() {
        return medcin;
    }

    public void setMedcin(String medcin) {
        this.medcin.set(medcin);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getMaladies() {
        return maladies.get();
    }

    public SimpleStringProperty maladiesProperty() {
        return maladies;
    }

    public void setMaladies(String maladies) {
        this.maladies.set(maladies);
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getMontantPayee() {
        return montantPayee;
    }

    public void setMontantPayee(int montantPayee) {
        this.montantPayee = montantPayee;
    }

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consultation that = (Consultation) o;
        return getMontantPayee() == that.getMontantPayee() &&
                getDiagnostic().equals(that.getDiagnostic()) &&
                getMedcin().equals(that.getMedcin()) &&
                getDate().equals(that.getDate()) &&
                getMaladies().equals(that.getMaladies()) &&
                Objects.equals(getObservation(), that.getObservation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDiagnostic(), getMedcin(), getDate(), getMaladies(), getObservation(), getMontantPayee());
    }
}
