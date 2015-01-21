package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Algorithm;

/**
 *
 * @author william
 */
public class AlgorithmTester {    
    static final private String HEADERLINE = "=============================";
    static final private String FOOTERLINE = "==========================================================";
    //A estimative of the size of the input text
    static final private int STRINGBUILDER_INITIALSIZE = 700;
    
    static final private int NUMBER_ACROSTICS = 2;
    static final private int NUMBER_LETTER_ALPHABET = 29;
    
    HashMap<Character, String> mostFreqWordsByInitialLetter;
    
    public AlgorithmTester(){
        mostFreqWordsByInitialLetter = new HashMap<>(NUMBER_LETTER_ALPHABET);
        
        mostFreqWordsByInitialLetter.put('a', "auf");
        mostFreqWordsByInitialLetter.put('b', "bei");
        mostFreqWordsByInitialLetter.put('c', "CDU");
        mostFreqWordsByInitialLetter.put('d', "der");
        mostFreqWordsByInitialLetter.put('e', "ein");
        mostFreqWordsByInitialLetter.put('f', "für");
        mostFreqWordsByInitialLetter.put('g', "gegen");
        mostFreqWordsByInitialLetter.put('h', "hat");
        mostFreqWordsByInitialLetter.put('i', "in");
        mostFreqWordsByInitialLetter.put('j', "Jahr");
        mostFreqWordsByInitialLetter.put('k', "kann");
        mostFreqWordsByInitialLetter.put('l', "lassen");
        mostFreqWordsByInitialLetter.put('m', "mit");
        mostFreqWordsByInitialLetter.put('n', "nicht");
        mostFreqWordsByInitialLetter.put('o', "oder");
        mostFreqWordsByInitialLetter.put('p', "Prozent");
        mostFreqWordsByInitialLetter.put('q', "Qualität");
        mostFreqWordsByInitialLetter.put('r', "rund");
        mostFreqWordsByInitialLetter.put('s', "sich");
        mostFreqWordsByInitialLetter.put('t', "Teil");
        mostFreqWordsByInitialLetter.put('u', "und");
        mostFreqWordsByInitialLetter.put('v', "von");
        mostFreqWordsByInitialLetter.put('w', "werden");
        mostFreqWordsByInitialLetter.put('x', "X");
        mostFreqWordsByInitialLetter.put('y', "Yen");
        mostFreqWordsByInitialLetter.put('z', "zu");
        mostFreqWordsByInitialLetter.put('ü', "über");
        mostFreqWordsByInitialLetter.put('ö', "öffentlichen");
        mostFreqWordsByInitialLetter.put('ä', "ändern");
    }
    
    private List<String> chooseAcrosticsForText(Text text){
        assert text != null;
        assert !text.toString().isEmpty();
        
        ArrayList<String> acrostics = new ArrayList<>(NUMBER_ACROSTICS);
        
        //One possible acrostic is the first word of the text (self-referential acrostic)
        String acrostic = text.getFirstWord();
        assert acrostic != null;
        assert !acrostic.isEmpty();
        
        acrostics.add(acrostic);
        
        Character firstLetter = text.getFirstLetter();
        assert firstLetter != null;
        acrostic = mostFreqWordsByInitialLetter.get(firstLetter);
        assert acrostic != null;
        assert !acrostic.isEmpty();
        
        acrostics.add(acrostic);
        
        return acrostics;
    }
    
    private String getResultLog(final Text text, final String acrostic, final Algorithm algorithm, final State finalState){
        StringBuilder sb = new StringBuilder();
        sb.append("Input Text: ").append(text).append("\n");
        sb.append("Acrostic: ").append(acrostic).append("\n");
        if (finalState == null){
            sb.append("Result: FAIL").append("\n");
        }else{
            sb.append("Result: SUCCESS").append("\n");
            sb.append("Result Text: ").append(finalState.getText()).append("\n");
        }
        sb.append("Applied Operators (In Final Result): ").append(
                finalState.getAppliedOperations()
                        .stream().map(o -> o.toString()))
                .append("\n");
        sb.append("Number of Genereted Nodes: ").append(algorithm.getGeneratedNodesNo()).append("\n");
        //@TODO:
        //sb.append("Number of Goal Checks: ").append(algorithm.getGoalChecksNo()).append("\n");
        //sb.append("Execution Time: ").append(algorithm.getLastExecutionTime()).append("\n");
        
        return sb.toString();
    }
    
    private String runTests(final Text text) throws Exception{
        assert text != null;
        assert !text.toString().isEmpty();

        List<String> acrostics = chooseAcrosticsForText(text);
        
        assert acrostics != null;
        assert !acrostics.toString().isEmpty();
        
        String log = "\n";
        //Execute algorithm one time per acrostic
        for(String acrostic : acrostics){
            assert !acrostic.isEmpty();
            
            Algorithm acrosticsAlg = new Algorithm();
            State state = acrosticsAlg.execute(text, acrostic);
            
            log = getResultLog(text, acrostic, acrosticsAlg, state);
        }
        
        return log;
    }

    public void execute(final List<String> inputFileNames, final String outputFileName) throws IOException, Exception{
        
        if (outputFileName == null || outputFileName.isEmpty()){
            throw new IllegalArgumentException("AlgorithmTester::execute: output file name invalid");
        }
        
        if (inputFileNames != null && !inputFileNames.isEmpty()){
            
            //Used to turn the input text into an Text Object
            HyphenationOperation hyOp = new HyphenationOperation();
            //Used to build the input strings
            String line;

            //Iterate over the input file names, execute the test and do logs for each text
            for(String inputFileName : inputFileNames){
                try(BufferedReader reader = new BufferedReader(new FileReader(new File(inputFileName)))){

                    //Read the file line by line and construct the input string
                    StringBuilder input = new StringBuilder(STRINGBUILDER_INITIALSIZE);
                    while( (line = reader.readLine()) != null ) {
                        input.append(line);
                    }
                    
                    if (input.length() > 0){
                        //Important: turns input into an text object
                        Text inputText = hyOp.StringToText(input.toString());
                        
                        //Execute the actual tests
                        String runLog = runTests(inputText);
                        
                        //Build actual logs
                        StringBuilder sbLog = new StringBuilder();
                        sbLog.append(HEADERLINE).append(inputFileName).append(HEADERLINE).append("\n");
                        sbLog.append("Current Time: ").append(new Date().toString()).append("\n");
                        sbLog.append("Input String: ").append(input.toString()).append("\n");
                        sbLog.append(runLog);
                        sbLog.append(FOOTERLINE);
                        
                    }else{
                        //Log empty input string
                        StringBuilder sbLog = new StringBuilder();
                        sbLog.append(HEADERLINE).append(inputFileName).append(HEADERLINE).append("\n");
                        sbLog.append("Current Time: ").append(new Date().toString()).append("\n");
                        sbLog.append("ERROR: Empty File").append("\n");
                        sbLog.append(FOOTERLINE);
                    }
                } catch (FileNotFoundException ex) {
                    //FileNotFound is an exception that can de logged in our output file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputFileName)))){

                        writer.append(HEADERLINE).append(inputFileName).append(HEADERLINE);
                        writer.append("Current Time: ").append(new Date().toString());
                        writer.append("ERROR: File not Found");
                        writer.append(FOOTERLINE);

                    } catch (IOException ex1) {
                        //But if occurred an IO error we call the java logger
                        Logger.getLogger(AlgorithmTester.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                } catch (IOException ex){
                    //////We do not save in the output file IO Errors
                    Logger.getLogger(AlgorithmTester.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
