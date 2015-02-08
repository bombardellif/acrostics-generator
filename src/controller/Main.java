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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Algorithm;

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
        FileReader fr = new FileReader("Text/KeinWrong.txt");
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
            State state = bestfit.execute(text1, "Buch");
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
        
        //Example of use of the tester
        //Here you make the list of file names of the texts
        //String testTextsFiles[] = {"Text/KeinWrong.txt"};
        //Here is the main call with the output file name
        //new AlgorithmTester().execute(new ArrayList<>(Arrays.asList(testTextsFiles)), "Text/TestResults.txt");

    }
}
