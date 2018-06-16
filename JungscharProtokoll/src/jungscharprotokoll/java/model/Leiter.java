/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.model;

/**
 *
 * @author chris
 */
public class Leiter {

    private String aemtli;
    private final String name;
    private final String nachname;
    private String email;
    private String gruppe;
    private String position;

    public Leiter(String aemtli, String name, String nachname, String email, String gruppe, String position) {
        this.aemtli = aemtli;
        this.name = name;
        this.nachname = nachname;
        this.email = email;
        this.gruppe = gruppe;
        this.position = position;
    }

    public Leiter(String name, String nachname) {
        this.name = name;
        this.nachname = nachname;
    }

    public String getAemtli() {
        return aemtli;
    }

    public String getName() {
        return name;
    }

    public String getNachname() {
        return nachname;
    }

    public String getEmail() {
        return email;
    }

    public String getGruppe() {
        return gruppe;
    }

    public String getPosition() {
        return position;
    }

    public void setAemtli(String aemtli) {
        this.aemtli = aemtli;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
