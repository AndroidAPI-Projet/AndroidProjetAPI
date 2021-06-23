package com.example.androidprojetapi;

public class Utilisateur {
    private String idUtilisateur;
    private String userName;
    private String email;
    private String type;
    private String password;
    private String valideuser;
    private String changepwd;
    private String sexe;
    private String nom;
    private String prenom;
    private String tel;
    private String adresse;
    private String ville;
    private String codePostal;

    public Utilisateur(String idUtilisateur, String userName, String email, String type, String password, String valideuser, String changepwd, String sexe, String nom, String prenom, String tel, String adresse, String ville, String codePostal) {
        this.idUtilisateur = idUtilisateur;
        this.userName = userName;
        this.email = email;
        this.type = type;
        this.password = password;
        this.valideuser = valideuser;
        this.changepwd = changepwd;
        this.sexe = sexe;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    public Utilisateur(){}

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValideuser() {
        return valideuser;
    }

    public void setValideuser(String valideuser) {
        this.valideuser = valideuser;
    }

    public String getChangepwd() {
        return changepwd;
    }

    public void setChangepwd(String changepwd) {
        this.changepwd = changepwd;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
}
