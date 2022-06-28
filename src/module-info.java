module CabinetMedicale {
    requires javafx.fxml;
    requires javafx.controls;

    requires java.sql;
    requires javafx.web;
    requires j2html;


    opens sample;
    opens sample.Login;
    opens sample.Medcin;
    opens sample.Medcin.GererConsultation;
    opens sample.Medcin.GererDossierMedicale;
    opens sample.Medcin.GererOrdonnance;
    opens sample.Medcin.GererPatient;
    opens sample.Medcin.GererUser;
    opens sample.model;
    opens sample.Secretaire;
    opens sample.Secretaire.GererRendezVous;
}