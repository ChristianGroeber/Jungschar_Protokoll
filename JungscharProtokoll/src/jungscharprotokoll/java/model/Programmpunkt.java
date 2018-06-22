/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author chris
 */
public class Programmpunkt {

    private int beginnH;
    private int beginnM;
    private int endeH;
    private int endeM;
    private String taetigkeit;
    private final ArrayList<Leiter> zustaendig;
    private String htmlText;
    private int punkt;

    public int getPunkt() {
        return punkt;
    }

    public void setPunkt(int punkt) {
        this.punkt = punkt;
    }

    public Programmpunkt() {
        this.zustaendig = new ArrayList<>();
    }

    public Programmpunkt(int beginnH, int beginnM, int endeH, int endeM) {
        this.beginnH = beginnH;
        this.beginnM = beginnM;
        this.endeH = endeH;
        this.endeM = endeM;
        this.zustaendig = new ArrayList<>();
    }

    public String getHtmlText() {
        return htmlText;
    }

    public void setHtmlText(String htmlText) {
        this.htmlText = htmlText;
    }

    public ArrayList<Leiter> getZustaendig() {
        return zustaendig;
    }

    public void addZustaendig(Leiter leiter) {
        zustaendig.add(leiter);
    }

    public int getBeginnH() {
        return beginnH;
    }

    public void setBeginnH(int beginnH) {
        this.beginnH = beginnH;
    }

    public int getBeginnM() {
        return beginnM;
    }

    public void setBeginnM(int beginnM) {
        this.beginnM = beginnM;
    }

    public int getEndeH() {
        return endeH;
    }

    public void setEndeH(int endeH) {
        this.endeH = endeH;
    }

    public int getEndeM() {
        return endeM;
    }

    public void setEndeM(int endeM) {
        this.endeM = endeM;
    }

    public void setTaetigkeit(String taetigkeit) {
        this.taetigkeit = taetigkeit;
    }

    public String getTaetigkeit() {
        return taetigkeit;
    }
}
