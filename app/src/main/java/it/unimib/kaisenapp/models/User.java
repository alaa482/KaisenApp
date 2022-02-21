package it.unimib.kaisenapp.models;

public class User {

    public String fullName, mail, imId;
    public int numSf,ore;

    public User(){

    }

    public User(String fullName, String mail, int numSf, int ore, String imId){
        this.fullName = fullName;
        this.mail = mail;
        this.numSf = numSf;
        this.ore = ore;
        this.imId = imId;
    }
    public void setImId(String imId){
        this.imId = imId;
    }

}
