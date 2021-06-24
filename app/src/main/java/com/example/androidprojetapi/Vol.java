package com.example.androidprojetapi;

public class Vol {

    private String NumVol;
    private String AeroportDept;
    private String HDepart;
    private String AeroportArr;
    private String HArrivee;

    public void setNumVol(String NumVol) {

        this.NumVol = NumVol;
    }

    public void setAeroportDept(String AeroportDept) {

        this.AeroportDept = AeroportDept;
    }

    public void setHDepart(String HDepart) {
        this.HDepart = HDepart;
    }

    public void setAeroportArr(String AeroportArr) {

        this.AeroportArr = AeroportArr;
    }

    public void setHArrivee(String HArrivee) {

        this.HArrivee = HArrivee;
    }

    public Vol(String NumVol, String AeroportDept, String HDepart,String AeroportArr, String HArrivee) {
        this.NumVol = NumVol;
        this.AeroportDept=AeroportDept;
        this.HDepart = HDepart;
        this.AeroportArr = AeroportArr;
        this.HArrivee = HArrivee;
    }

    public String getNumVol() {

        return NumVol;
    }

    public String getAeroportDept() {

        return AeroportDept;
    }

    public String getHDepart() {

        return HDepart;
    }

    public String getAeroportArr() {

        return AeroportArr;
    }

    public String getHArrivee() {

        return HArrivee;
    }

}
