/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author chris
 */
public class Timetable {

    private ArrayList<Programmpunkt> programmpunkte = new ArrayList<>();

    private static Timetable instance;

    public Timetable() {
    }

    public static Timetable getInstance() {
        if (instance == null) {
            instance = new Timetable();
        }
        return instance;
    }

    public void setProgrammpunkt(Programmpunkt i) {
        programmpunkte.add(i);
    }

    public ArrayList<Programmpunkt> getProgrammpunkt() {
        return programmpunkte;
    }

    public Programmpunkt getLastProgrammpunkt() {
        Programmpunkt p = null;
        for (Programmpunkt i : programmpunkte) {
            p = i;
        }

        return p;
    }
    
    public void removeProgrammpunkt(Programmpunkt toDelete){
        try {
            programmpunkte.remove(toDelete);
        } catch (Exception e) {
            System.out.println("Deleting not possible " + e);
        }
    }
    
    public void sort(){
        Programmpunkt[] tempList = programmpunkte.toArray(new Programmpunkt[0]);
        for (Programmpunkt tempList1 : tempList) {
            for (int j = 0; j < tempList.length; j++) {
                Programmpunkt p2 = null;
                try {
                    p2 = tempList[j + 1];
                } catch (ArrayIndexOutOfBoundsException e) {
                    break;
                }
                Programmpunkt p1 = tempList[j];

                System.out.println("EndeH1 " + p1.getEndeH() + " Endeh2 " + p2.getEndeH());
                if (p1.getEndeH() > p2.getEndeH()) {
                    tempList[j + 1] = p1;
                    tempList[j] = p2;
                } else if (p1.getEndeH() == p2.getEndeH()) {
                    if (p1.getEndeM() > p2.getEndeM()) {
                        tempList[j + 1] = p1;
                        tempList[j] = p2;
                    }
                }
            }
        }
        programmpunkte = new ArrayList<>(Arrays.asList(tempList));
    }
}
