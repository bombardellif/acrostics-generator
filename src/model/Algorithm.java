/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.HyphenationOperation;
import controller.LineBreakOperation;
import controller.Operation;
import controller.State;
import controller.SynonymOperation;
import controller.Text;
import controller.WordInsertionDeletionOperation;
import controller.WrongHyphenationOperation;
import controller.WrongSpellingOperation;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author standard
 */
public class Algorithm {
    
    
    /* GA parameters */
    /*private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    
    */
    private long generatedNodesNo;
    
    private static final int TIMEOUT_SECONDS = 15*60; // 15 Minutes
    
    // Evolve a population
    public State execute(Text text, String acrostic) throws TimeoutException, Exception {
        
        
        State S0 = new State(text);
        setGeneratedNodesNo(1);
        
       
        
        //list of all operators that will be applied to a node
        //has to be corrected later!
        ArrayList<Operation> operatorList = new ArrayList<>();
        operatorList.add(new LineBreakOperation());
        operatorList.add(new WrongHyphenationOperation());
        operatorList.add(new WordInsertionDeletionOperation());
        operatorList.add(new SynonymOperation());
        operatorList.add(new HyphenationOperation());
        operatorList.add(new WrongSpellingOperation());
        
        Comparator<State> comparator = new StateCostComparator();
        PriorityQueue<State> stateQueue = new PriorityQueue<>(comparator);
        
        HashSet visitedTexts;
        visitedTexts = new HashSet();
        
        //not isGoal
        if(!(S0.getText()).goalTest(acrostic)){
            
           
                                      
            boolean add; //success of adding state to stateQueue
            
            //add state S0 to state queue
            add = stateQueue.add(S0);
            if(!add){
                System.err.println("Adding State to stateQueue failed!");
                return null;
   
                
            }
            
            
            add = visitedTexts.add(S0.getText());
            if(!add){
               System.err.println("Adding Text to HashSet failed!");
               return null;            
            }
              
            
            LocalDateTime timeOfStart = LocalDateTime.now();
            while(!stateQueue.isEmpty()){
                
                State Si;
                Si = stateQueue.poll();
                //System.out.println(Si.getCost()+" , "+Si.getEstimatedCost());
                
                /*** Timeout control        ***/
                LocalDateTime timeCurrent = LocalDateTime.now();
                if (Duration.between(timeOfStart, timeCurrent).getSeconds() > TIMEOUT_SECONDS) {
                    System.out.println("==== END BY TIMEOUT ====");
                    System.out.println("The Algorithm Result:");
                    System.out.println();
                    System.out.println(Si.getText());

                    System.out.println("Print Operator List:");
                    List<Operation> operatorExecutedList = Si.getAppliedOperations();
                    for (Operation operator : operatorExecutedList) {
                        System.out.println(operator);
                    }
                    
                    throw new TimeoutException("End by timeout");
                }
                /*** END of Timeout control ***/
                
                for(Operation operator : operatorList){
                    
                    List<Text> newTexts;
                    newTexts = operator.execute(Si.getText());
                    
                    
                    for(Text newText: newTexts){
                        
                        boolean contains = visitedTexts.contains(newText);
                        
                        //not contains
                        if(!contains){
                            visitedTexts.add(newText);
                            
                            //create applied operation list for the new state
                            ArrayList<Operation> appliedOperatorList = new ArrayList<>(Si.getAppliedOperations());
                            appliedOperatorList.add(operator);
                            
                            
                            State newState = new State(newText);
                            generatedNodesNo++; 
                            
                            newState.setCost(Si.getCost() + operator.getCost());
                            newState.setAppliedOperations(appliedOperatorList);
                            
                            boolean isTheGoal = (newState.getText()).goalTest(acrostic);
                            
                            //isTheGoal
                            if(isTheGoal){
                                return newState;
                                
                            }
                          
                            //not isTheGoal
                            else{
                                double estCost = newState.estimateCost(acrostic);
                            
                                newState.setEstimatedCost(estCost);
                                
                                stateQueue.add(newState);
                                
                                
                                
                                
                            }
                            
                            
                            
                            
                            
                            
                            
                            
                        }
                        
                        
                        
                        
                        
                        
                        
                    }
                    
                    
                    
                    
                    
                    
                }
                
                
                
                
                
                
                
                
                
                
                
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            return null;
            
        }
        
        //isGoal
        else{
            return S0;
            
            
            
        }
       
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
     
        
        
        
        
        
        
        
    }

    /**
     * @return the generatedNodesNo
     */
    public long getGeneratedNodesNo() {
        return generatedNodesNo;
    }

    /**
     * @param generatedNodesNo the generatedNodesNo to set
     */
    public void setGeneratedNodesNo(long generatedNodesNo) {
        this.generatedNodesNo = generatedNodesNo;
    }

    
    
    
    
    
    
    
    
    
    
    
}
