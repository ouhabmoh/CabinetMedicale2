package sample.model;

public class Adresse {
    private String rue;
    private String nm;
    private String zipCode;
    private String ville;
    private String Wilaya;

    public Adresse(String rue, String nm, String zipCode, String ville, String wilaya) {
        this.rue = rue;
        this.nm = nm;
        this.zipCode = zipCode;
        this.ville = ville;
        Wilaya = wilaya;
    }

    public Adresse() {
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getNm() {
        return nm;
    }

    public void setNm(String nm) {
        this.nm = nm;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getWilaya() {
        return Wilaya;
    }

    public void setWilaya(String wilaya) {
        Wilaya = wilaya;
    }

    @Override
    public String toString() {
        return  Wilaya+", "+ville+", "+rue+", "+nm;

    }
}
