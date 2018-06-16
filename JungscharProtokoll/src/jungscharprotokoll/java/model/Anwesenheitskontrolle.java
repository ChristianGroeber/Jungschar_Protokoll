/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.model;

import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class Anwesenheitskontrolle {
    private static Anwesenheitskontrolle instance;
    
    private ArrayList<Leiter> anwesend = new ArrayList<>();
    private ArrayList<Leiter> abwesend = new ArrayList<>();
    private ArrayList<Leiter> anwesendNachmittag = new ArrayList<>();
    private Leiter zustaendig;

    public Leiter getZustaendig() {
        return zustaendig;
    }

    public void setZustaendig(Leiter zustaendig) {
        this.zustaendig = zustaendig;
    }
    
    public  static Anwesenheitskontrolle getInstance(){
        if(instance == null){
            instance = new Anwesenheitskontrolle();
        }
        return instance;
    }

    public ArrayList<Leiter> getAnwesend() {
        return anwesend;
    }

    public void setAnwesend(ArrayList<Leiter> anwesend) {
        this.anwesend = anwesend;
    }

    public ArrayList<Leiter> getAbwesend() {
        return abwesend;
    }

    public void setAbwesend(ArrayList<Leiter> abwesend) {
        this.abwesend = abwesend;
    }

    public ArrayList<Leiter> getAbwesendNachmittag() {
        return anwesendNachmittag;
    }

    public void setAbwesendNachmittag(ArrayList<Leiter> anwesendNachmittag) {
        this.anwesendNachmittag = anwesendNachmittag;
    }
}
