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
public class Tester {

    public void outputInConsole(ArrayList<String> str) {
        for (String i : str) {
            System.out.println(i);
        }
    }

    public void outputInConsole(String[] str) {
        for (String i : str) {
            System.out.println(i);
        }
    }
    
    public void splitText(String text, String splitCharacter){
        String[] str = text.split(splitCharacter);
        System.out.println("Split Text: \n");
        for(String i : str){
            System.out.println(i);
        }
    }
    
    
}
