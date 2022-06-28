package sample;

import javafx.collections.ObservableList;
import sample.model.RendezVous;

public interface RendezVousDAO {
    boolean ajouterRendezVous(RendezVous rendezVous);
    boolean modifierRendezVous(RendezVous oldRendezVous, RendezVous newRendezVous);
    boolean supprimerRendezVous(RendezVous rendezVous);
    RendezVous chercherRendezVous(String nom, String prenom);
    ObservableList<RendezVous> rendezVousList(String date);
}
