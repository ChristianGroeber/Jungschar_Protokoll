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
public class Timetable {
    private ArrayList<Programmpunkt> programmpunkte = new ArrayList<>();
    
    private static Timetable instance;
    
    public Timetable(){
        programmpunkte.add(new Programmpunkt(00,00,00,00));
    }
    
    public static Timetable getInstance(){
        if(instance == null){
            instance = new Timetable();
        }
        return instance;
    }
    
    public void setProgrammpunkt(Programmpunkt i){
        programmpunkte.add(i);
    }
    
    public ArrayList<Programmpunkt> getProgrammpunkt(){
        return programmpunkte;
    }
    
    public Programmpunkt getLastProgrammpunkt(){
        Programmpunkt p = null;
        for(Programmpunkt i : programmpunkte){
            p = i;
        }
        
        return p;
    }
}
