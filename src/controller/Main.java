/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author william
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args)throws FileNotFoundException, IOException, InterruptedException {
            // TODO code application logic here
        
        
       //Read in the input text as string
       String input ="";
       String temp;
       FileReader fr = new FileReader ("Text/Goethe");
        try (BufferedReader br = new BufferedReader (fr)) {
            while( (temp = br.readLine()) != null ) {
                input += temp;
            }
            br.close();
        
       
            
            
        System.out.println("ORIGINAL TEXT");
        System.out.println();
        System.out.println(input);
       
        
        
        
        
        LineBreakOperation lbOp = new LineBreakOperation();
        Text text = lbOp.StringToText(input);
        System.out.println();
        System.out.println(text);
        
        //apply linebreak operation
        lbOp.execute(text);
        
        
        
        
        
        
        
    
            // TODO code application logic here
        }//try  
        
        
   
    }
	
}
