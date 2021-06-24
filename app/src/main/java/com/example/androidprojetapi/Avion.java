package com.example.androidprojetapi;

public class Avion {

    private String NumAvion;
    private String TypeAvion;
    private String BaseAeroport;

    public void setNumAvion(String NumAvion) {

        this.NumAvion = NumAvion;
    }

    public void setTypeAvion(String TypeAvion) {

        this.TypeAvion = TypeAvion;
    }

    public void setBaseAeroport(String BaseAeroport) {
        this.BaseAeroport = BaseAeroport;
    }

    public Avion(String NumAvion, String TypeAvion, String BaseAeroport,String AeroportArr, String HArrivee) {
        this.NumAvion = NumAvion;
        this.TypeAvion=TypeAvion;
        this.BaseAeroport = BaseAeroport;
    }

    public String getNumAvion() {

        return NumAvion;
    }

    public String getTypeAvion() {

        return TypeAvion;
    }

    public String getBaseAeroport() {

        return BaseAeroport;
    }

}
