/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import de.denkselbst.sentrick.sbd.BaselineSbd;
import de.denkselbst.sentrick.sbd.SentenceBoundaryDetector;
import de.denkselbst.sentrick.tokeniser.token.Token;
import de.denkselbst.sentrick.util.SbdProvider;
import de.denkselbst.sentrick.util.SbdProviders;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Algorithm;
import model.NetSpeakDAO;

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
    public static void main(String[] args)throws FileNotFoundException, IOException, InterruptedException, Exception {

       //Read in the input text as string
       String input ="";
       String temp;
       FileReader fr = new FileReader ("Text/DonaldKnuth");
        try (BufferedReader br = new BufferedReader (fr)) {
            while( (temp = br.readLine()) != null ) {
                input += temp;
            }
            br.close();
        
        
            
        /*  
        LineBreakOperation lbOp = new LineBreakOperation();
        Text text5 = lbOp.StringToText(input); 
        List<Text> lineBreakResults = lbOp.execute(text5);
          */  
            
            
            
            
            
            
         
        //System.out.println("input:");
        //System.out.println(input);
            
        HyphenationOperation hyOp = new HyphenationOperation();
        //String res = hyOp.correctHyphenation(input);
        //System.out.println(res);
        
        
        Text text1 = hyOp.StringToText(input);
        System.out.println("Text1:");
        System.out.println(text1);
        System.out.println();
        
        
/*
        
        List<Text> resultList = hyOp.execute(text1);
        
        for(int i=0; i< resultList.size(); i++){
            
            System.out.println(i + ".");
            System.out.println(resultList.get(i));
            System.out.println();
            System.out.println();
            
            
        }
        
        
        
 */       
        
        
        
        /*    
        EnsureConstraintsOperation ecOp = new EnsureConstraintsOperation();
        Text te = ecOp.StringToText(input);
        System.out.println("Vorher:");
        System.out.println(te);
        
        net.davidashen.text.Hyphenator h=new net.davidashen.text.Hyphenator();
        String word = "einen";
        String word1 = input;
        //h.setErrorHandler(null new MyErrorHandler());
        h.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("dehyphx.tex")));
        String hyphenated_word=h.hyphenate(word1);
        
        System.out.println();
        System.out.println(hyphenated_word);
        */
        
        
   
            
            
            
        /*for(String provider : SbdProviders.list())
            System.err.println("  "+provider);

        System.exit(0);
        */
        
            
        /*    
        File inputFile = null;
	String encoding = null;
        
        Reader in = null;
            
        String provider = "de.denkselbst.sentrick.classifiers.german.GermanSbdProvider";
        SbdProvider sbdProvider = (SbdProvider) Class.forName(provider).newInstance();    
            
        inputFile = new File("Text/DonaldKnuth");  
        
        in = new InputStreamReader(new FileInputStream(inputFile));
        
        SentenceBoundaryDetector sbd = sbdProvider.getPlainTextSentenceBoundaryDetector(in);
        
        Token t = null;
        
        ArrayList<Integer> dotIndexList = new ArrayList<>();
        int i=0;
        
        while( (t=sbd.readToken()) != null){
            
            if(dotIndexList.isEmpty())
                dotIndexList.add(t.getLength());
            
            else
            dotIndexList.set(i, dotIndexList.get(i)+t.getLength());
            
            System.out.print(t.getText());
            if(t.isSentenceBoundary()){
		i++;
                dotIndexList.add(i,dotIndexList.get(i-1));
                
                System.out.print("</s>");
                
                
            }
        }
        
        sbd.close();
        
       
        System.out.println(dotIndexList);

	System.err.println("\n\nDone.");
        */
            
        /*    
        System.out.println("ORIGINAL TEXT");
        System.out.println();
        System.out.println(input);
       
        
        LineBreakOperation lbOp = new LineBreakOperation();
        Text text = lbOp.StringToText(input);
        System.out.println();
        System.out.println(text);
        */
        
        //new WordInsertionDeletionOperation().execute(text);
        //new SynonymOperation().execute(text);
        //System.exit(0);
        
        /*
        //apply linebreak operation
        List<Text> resultLineBreakOp = lbOp.execute(text);
        for(Text te : resultLineBreakOp){
            System.out.println(te);
            System.out.println();
            
        }
       
       System.out.println("SIZE:" + resultLineBreakOp.size());
        
        */
        

        Algorithm bestfit = new Algorithm();
        State state = bestfit.execute(text1, "knet");
        if(state == null){
            System.out.println("NO ACROSTIC CONSTRUCTABLE!");
            return;
        }
        
        
        
        
        System.out.println("The Algorithm Result:");
        System.out.println();
        System.out.println(state.getText());
        
       
        System.out.println("Print Operator List:");
        List<Operation> operatorList = state.getAppliedOperations();
        for(Operation operator : operatorList){
            System.out.println(operator);
            
        }
        
        System.out.println();
        System.out.println("Number Generated Nodes: " + bestfit.getGeneratedNodesNo());
        
                
        
        }//try
               
    }
}
