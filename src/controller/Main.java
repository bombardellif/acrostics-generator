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
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, Exception {

        //Read in the input text as string
        String input = "";
        String temp;
        FileReader fr = new FileReader("Text/FriedrichDerGrosse");
        try (BufferedReader br = new BufferedReader(fr)) {
            while ((temp = br.readLine()) != null) {
                input += temp;
            }
            br.close();

            HyphenationOperation hyOp = new HyphenationOperation();
            Text text1 = hyOp.StringToText(input);

            System.out.println("ORIGINAL TEXT:");
            System.out.println(text1);
            System.out.println();

            Algorithm bestfit = new Algorithm();
            State state = bestfit.execute(text1, "Fritz");
            if (state == null) {
                System.out.println("NO ACROSTIC CONSTRUCTABLE!");
                return;
            }

            System.out.println("The Algorithm Result:");
            System.out.println();
            System.out.println(state.getText());

            System.out.println("Print Operator List:");
            List<Operation> operatorList = state.getAppliedOperations();
            for (Operation operator : operatorList) {
                System.out.println(operator);

            }

            System.out.println();
            System.out.println("Number Generated Nodes: " + bestfit.getGeneratedNodesNo());

        }

    }
}
