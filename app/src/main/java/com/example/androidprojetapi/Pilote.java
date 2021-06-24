package com.example.androidprojetapi;

public class Pilote {

    private Integer IdPilote;
    private String NomPilote;
    private String PrenomPilote;
    private String Matricule;

    public void setIdPilote(Integer IdPilote) {

        this.IdPilote = IdPilote;
    }

    public void setNomPilote(String NomPilote) {

        this.NomPilote = NomPilote;
    }

    public void setPrenomPilote(String PrenomPilote) {
        this.PrenomPilote = PrenomPilote;
    }

    public void setMatricule(String Matricule) {

        this.Matricule = Matricule;
    }

    public Pilote(Integer IdPilote, String NomPilote, String PrenomPilote,String Matricule) {
        this.IdPilote = IdPilote;
        this.NomPilote=NomPilote;
        this.PrenomPilote = PrenomPilote;
        this.Matricule = Matricule;
    }

    public Integer getIdPilote() {

        return IdPilote;
    }

    public String getNomPilote() {

        return NomPilote;
    }

    public String getPrenomPilote() {

        return PrenomPilote;
    }

    public String getMatricule() {

        return Matricule;
    }

}
