package com.example.androidprojetapi;

public class Affectation {

    private String IdAffectation;
    private String NumVol;
    private String DateVol;
    private Integer AffectationCode;
    private String NumAvion;
    private Integer IdPilote;

    public void setIdAffectation(String IdAffectation) {

        this.IdAffectation = IdAffectation;
    }

    public void setNumVol(String NumVol) {

        this.NumVol = NumVol;
    }

    public void setDateVol(String DateVol) {

        this.DateVol = DateVol;
    }

    public void setAffectationCode(Integer AffectationCode) {

        this.AffectationCode = AffectationCode;
    }

    public void setNumAvion(String NumAvion) {

        this.NumAvion = NumAvion;
    }

    public void setIdPilote(Integer IdPilote) {

        this.IdPilote = Affectation.this.IdPilote;
    }

    public Affectation(String IdAffectation, String NumVol, String DateVol,Integer AffectationCode, String NumAvion, Integer IdPilote) {
        this.IdAffectation = IdAffectation;
        this.NumVol=NumVol;
        this.DateVol = DateVol;
        this.AffectationCode = AffectationCode;
        this.NumAvion = NumAvion;
        this.IdPilote = IdPilote;
    }

    public String getIdAffectation() {

        return IdAffectation;
    }

    public String getNumVol() {

        return NumVol;
    }

    public String getDateVol() {

        return DateVol;
    }

    public Integer getAffectationCode() {

        return AffectationCode;
    }

    public String getNumAvion() {

        return NumAvion;
    }

    public Integer getIdPilote() {

        return IdPilote;
    }

}
