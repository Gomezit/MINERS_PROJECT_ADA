/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import com.sun.jndi.toolkit.url.Uri;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author andres
 */
public class ManageFile {
    
    

    public void write(ArrayList<Mine> mines, String filename) {

        Gson gson = new Gson();
        String textJson = gson.toJson(mines);
        File f;
        FileWriter w;
        BufferedWriter bw;
        PrintWriter wr;

        try {

            f = new File(filename);
            w = new FileWriter(f);
            bw = new BufferedWriter(w);
            wr = new PrintWriter(bw);

            wr.write(textJson);

            wr.close();
            bw.close();

        } catch (IOException e) {

            System.out.println("Error al escribir el archivo: " + e);

        }

    }

    
   
    public String read(String fileName) {

        File file;
        FileReader fr;
        BufferedReader br;
        String text = "";

        try {

            file = new File(fileName);
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line;

            
            while ((line = br.readLine()) != null) {
                
                //System.out.println("Reading");
                //System.out.println(line);
                text += line;
            }

            br.close();
            fr.close();

        } catch (Exception e) {

            System.out.println("Error al leer el archivo :  " + e);
        }
        
        return text;

    }

}
