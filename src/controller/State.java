package controller;

import java.util.ArrayList;
import java.util.List;
import model.FirstLetterFrequency;

public class State {

    private Double cost;

    private Double estimatedCost;

    private Text text;
    
    private List<Operation> appliedOperations;

    public void State(Text text, Double cost, List<Operation> appliedOperations) {
        if (text == null)
            throw new IllegalArgumentException("State: Parameter text must not be null");
        if (cost == null)
            throw new IllegalArgumentException("State: Parameter cost must not be null");
        if (appliedOperations == null)
            throw new IllegalArgumentException("State: Parameter appliedOperations must not be null");
        
        this.text = new Text(text);
        this.cost = cost;
        this.appliedOperations = new ArrayList<>(appliedOperations);
        this.estimatedCost = 0.0;
    }

    public Double estimateCost(String acrostic) {
        if (acrostic == null)
            throw new IllegalArgumentException("estimateCost: Parameter acrostic must not be null");
        
        String remainingAcrostic = this.text.remainingAcrostic(acrostic);
        
        this.estimatedCost = Math.log(this.estimatedEffort(remainingAcrostic))
                            / Math.log(this.text.probabilityLeastFrequentLetterInversed(remainingAcrostic));
        
        return this.estimatedCost;
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
        return this.cost + this.estimatedCost;
    }

    public Text getText() {
        return new Text(this.text);
    }
	
	public List<Operation> getAppliedOperations() {
        return new ArrayList<>(this.appliedOperations);
    }

}
