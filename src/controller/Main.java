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
        
        //Example of use of the tester
        //String testTextsFiles[] = {"Text/William/1.txt","Text/William/2.txt","Text/William/3.txt","Text/William/4.txt","Text/William/6.txt","Text/William/7.txt","Text/William/8.txt","Text/William/9.txt","Text/William/10.txt","Text/William/11.txt","Text/William/12.txt","Text/William/13.txt","Text/William/15.txt","Text/William/16.txt","Text/William/17.txt","Text/William/19.txt","Text/William/20.txt","Text/William/21.txt","Text/William/22.txt","Text/William/23.txt","Text/William/24.txt","Text/William/25.txt","Text/William/26.txt","Text/William/27.txt","Text/William/28.txt"};      
        //new AlgorithmTester().execute(new ArrayList<>(Arrays.asList(testTextsFiles)), "Text/William/TestResults1.txt");

    }
}
