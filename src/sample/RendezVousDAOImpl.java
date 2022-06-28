package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import sample.Datasource.Datasource;
import sample.Medcin.Controller;
import sample.model.Patient;
import sample.model.RendezVous;

public class RendezVousDAOImpl implements RendezVousDAO{

    private ObservableList<RendezVous> rendezVousList = FXCollections.observableArrayList();


    @Override
    public boolean ajouterRendezVous(RendezVous rendezVous) {
        int id = Datasource.getInstance().insertRendezVous(rendezVous);
        if(id > -1){
            rendezVous.setId(id);
            rendezVousList.add(rendezVous);
            return true;
        }
            return false;

    }

    @Override
    public boolean modifierRendezVous(RendezVous oldRendezVous, RendezVous newRendezVous) {
        return false;
    }

    @Override
    public boolean supprimerRendezVous(RendezVous rendezVous) {
        return false;
    }

    @Override
    public RendezVous chercherRendezVous(String nom, String prenom) {
        return null;
    }

    @Override
    public ObservableList<RendezVous> rendezVousList(String date) {
        setRendezVousList(date);
        SortedList<RendezVous> sortedList = new SortedList<>(rendezVousList,
                (o1, o2) -> o1.getId() > o2.getId() ? -1 : 1);
        return sortedList;
    }

    public void setRendezVousList(String date){
        Task<ObservableList<RendezVous>> taskRendezVous = new GetAllRendezVousTask(date);
        taskRendezVous.setOnSucceeded(e-> rendezVousList.setAll(taskRendezVous.getValue())
        );

    }

    class GetAllRendezVousTask extends Task {
        private String date;

        public GetAllRendezVousTask(String date) {
            this.date = date;
        }

        @Override
        public ObservableList<RendezVous> call() {

            return FXCollections.observableArrayList
                    (Datasource.getInstance().queryRendezVous(date));
        }
    }
}
