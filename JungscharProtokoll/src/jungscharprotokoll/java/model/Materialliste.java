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
public class Materialliste {
    ArrayList<String> material = new ArrayList<>();
    
    public void addMaterial(String mat){
        material.add(mat);
    }
    
    public ArrayList<String> getMaterial(){
        return material;
    }
}
