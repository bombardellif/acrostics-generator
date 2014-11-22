package controller;

import java.util.ArrayList;
import java.util.List;
import model.FirstLetterFrequency;


public class State {

  
    private Text text;
     
    private Double cost;

    private Double estimatedCost;
    

    private List<Operation> appliedOperations;
    
    //when qmax is low we can walk deeper in the tree,
    //since the add up of costs will later be significant  
    private final double qmax = 0.8;

    
    //constructor
    public State(Text text) {
          
        
        this.text = text;
        this.cost = 0.0;
        this.appliedOperations = new ArrayList<>();
        this.estimatedCost = 0.0;
    }
    
    
    
    
    
    //constructor
    public State(Text text, Double cost, Double estimatedCost,List<Operation> appliedOperations) {
          
        
        this.text = text;
        this.cost = cost;
        this.appliedOperations = new ArrayList<>(appliedOperations);
        this.estimatedCost = estimatedCost;
    }
    
    
    
    
    
    
    
    

    public void State(Text text, Double cost, List<Operation> appliedOperations) {
        if (text == null)
            throw new IllegalArgumentException("State: Parameter text must not be null");
        if (cost == null)
            throw new IllegalArgumentException("State: Parameter cost must not be null");
        if (appliedOperations == null)
            throw new IllegalArgumentException("State: Parameter appliedOperations must not be null");
        
        this.setText(new Text(text));
        this.setCost(cost);
        this.setAppliedOperations(new ArrayList<>(appliedOperations));
        this.setEstimatedCost((Double) 0.0);
    }

    public Double estimateCost(String acrostic) {
        if (acrostic == null)
            throw new IllegalArgumentException("estimateCost: Parameter acrostic must not be null");
        
        String remainingAcrostic = this.getText().remainingAcrostic(acrostic);
        
        //double estEff = estimatedEffort(remainingAcrostic);
        //Text text2 = this.getText();
        //double freqInv = text2.probabilityLeastFrequentLetterInversed(remainingAcrostic);
        
        
        this.setEstimatedCost((Double) Math.log(this.estimatedEffort(remainingAcrostic))*((double)1/qmax) / Math.log(this.getText().probabilityLeastFrequentLetterInversed(remainingAcrostic)));
        
        return this.getEstimatedCost();
    }

    private Double estimatedEffort(String remainigAcrostic) {
        if (remainigAcrostic == null)
            throw new IllegalArgumentException("estimatedEffort: Parameter remainigAcrostic must not be null");
        
        double result = 1.0;
        for (int i=remainigAcrostic.length()-1; i>=0; i--) {
            result *= FirstLetterFrequency.getFrequencyInversed(remainigAcrostic.charAt(i));
        }
        return result;
    }

    public Double estimatedTotalCost() {
        return this.getCost() + this.getEstimatedCost();
    }

    public Text getText() {
        return text;
    }
	
    public List<Operation> getAppliedOperations() {
        return appliedOperations;
    }

    /**
     * @return the cost
     */
    public Double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * @return the estimatedCost
     */
    public Double getEstimatedCost() {
        return estimatedCost;
    }

    /**
     * @param estimatedCost the estimatedCost to set
     */
    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    /**
     * @param text the text to set
     */
    public void setText(Text text) {
        this.text = text;
    }

    /**
     * @param appliedOperations the appliedOperations to set
     */
    public void setAppliedOperations(List<Operation> appliedOperations) {
        this.appliedOperations = appliedOperations;
    }

}
