package sample.model;

import java.util.List;

public class Ordonnance {
    private int id;
    private List<Medicament> medicamentList;
    private String observation;



    public Ordonnance() {
    }

    public Ordonnance(int id, List<Medicament> medicamentList, String observation) {
        this.id = id;
        this.medicamentList = medicamentList;
        this.observation = observation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Medicament> getMedicamentList() {
        return medicamentList;
    }
    public String medicamentsString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Medicament medicament : medicamentList){
            stringBuilder.append(medicament.getNom());
            stringBuilder.append(",");
            stringBuilder.append(medicament.getDose());
            stringBuilder.append(",");
            stringBuilder.append(medicament.getAstuce());
            stringBuilder.append(",");
            stringBuilder.append(medicament.getDuration());
            stringBuilder.append("-");

        }
         int i = stringBuilder.lastIndexOf("-");
        stringBuilder.deleteCharAt(i);
        return stringBuilder.toString();
    }

    public void setMedicamentList(List<Medicament> medicamentList) {
        this.medicamentList = medicamentList;
    }
    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
