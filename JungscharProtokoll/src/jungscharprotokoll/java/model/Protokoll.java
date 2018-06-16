/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jungscharprotokoll.java.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author chris
 */
public class Protokoll {

    private static Protokoll instance;

    private File file;

    private Tester test = new Tester();
    
    private final String EINSCHUB = "     ";
    private final String NEWLINE = "\n";
    
    private int line = 0;
    
    private int nextLine = 57;

    private Protokoll() {

    }

    public static Protokoll getInstance() {
        if (instance == null) {
            instance = new Protokoll();
        }
        return instance;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File f) {
        this.file = f;
    }
    
    public String getEinschub(){
        return EINSCHUB;
    }
    
    public String getNewLine(){
        return NEWLINE;
    }
      

    /**
     * Writes to the file that it's saved
     *
     * @param text: Text that has to be written to the file
     * @param lineToContinue: In what line to insert the given text
     * @throws java.io.FileNotFoundException
     * @throws java.io.UnsupportedEncodingException
     */
    public void writeToFile(String text, int lineToContinue) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        //get the text of the file and put it into an ArrayList.

        ArrayList<String> textFileNeu = null;
        if (lineToContinue == 0) {
            String[] str = text.split("\n");
            textFileNeu = new ArrayList<>(Arrays.asList(str));
        } else {
            ArrayList<String> textFile = getFileText();
            textFileNeu = new ArrayList<>();
            for (int i = 0; i < textFile.size(); i++) {
                if (i == lineToContinue) {
                    textFileNeu.add(text);
                }
                textFileNeu.add(textFile.get(i));
            }
        }

        //Write to the file
        try (PrintWriter writer = new PrintWriter(file.getPath(), "UTF-8")) {
            for (String str : textFileNeu) {
                writer.println(str);
            }
        } catch (Exception e) {
            //Error
            System.out.println(e + "\nError with writing text: " + text);
        }
    }

    /**
     * Gets the text of the file
     *
     * @return ArrayList of type String
     * @throws java.io.FileNotFoundException
     */
    public ArrayList<String> getFileText() throws FileNotFoundException, IOException {
        ArrayList<String> string = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.getPath()))) {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    string.add(line);
                }
            } catch (IOException e) {
                System.out.println(e + "\nError while reading from File");
            }
        }
//        test.outputInConsole(string);
        return string;
    }

    public ArrayList<String> getFileText(String text, String split) {
        ArrayList<String> string;
        String[] arrString = text.split(split);
        string = new ArrayList<>(Arrays.asList(arrString));
        return string;
    }
    
    public  String getFileString() throws IOException{
        ArrayList<String> text = getFileText();
        String ret = "";
        for(String i : text){
            ret += i + NEWLINE;
        }
        return ret;
    }
    
    public int getNextLine(int line) throws IOException{
        ArrayList<String> text = getFileText();
        
        int nextLine = 1;
        for(String i : text){
            System.out.println(i.contains("<!--End line ") && i.contains(Integer.toString(line)));
            if(i.contains("<!--End line " + line + "-->")){
                return nextLine;
            }else{
                nextLine++;
            }
        }
        return 42;
    }
    
    public int getNextLine2(){
        return nextLine;
    }
    
    public void setNextLine2(int i){
        nextLine = i;
    }
    
    public int getLine(){
        return line;
    } 
    
    public void addLine(){
        line = line + 1;
    }
    
    public int getInsertIn(String comment) throws IOException{
        int insert = 0;
        ArrayList<String> text = getFileText();
        int x = 0;
        for(String i : text){
            if(i.contains(comment)){
                insert = x;
                break;
            }
            x++;
        }
        insert = insert + 1;
        return insert;
    }
}
