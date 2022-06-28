package sample.model;

public class Medicament {
    private String nom;
    private String dose;
    private String astuce;
    private String duration;

    public Medicament(String nom, String dose, String astuce, String duration) {
        this.nom = nom;
        this.dose = dose;
        this.astuce = astuce;
        this.duration = duration;
    }

    public Medicament() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if(nom == null)
            this.nom = " ";
        else
            this.nom = nom;
    }

    public String getDose() {

        return dose;
    }

    public void setDose(String dose) {
        if (dose == null || dose.isEmpty())
            this.dose = " ";
        else
            this.dose = dose;
    }

    public String getAstuce() {
        return astuce;
    }

    public void setAstuce(String astuce) {
        if (astuce == null || astuce.isEmpty())
            this.astuce = " ";
        else
            this.astuce = astuce;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        if (duration == null || duration.isEmpty())
            this.duration = " ";
        else
            this.duration = duration;
    }

    @Override
    public String toString() {
        return nom +" "+dose+" "+astuce+" "+duration;
    }
}
