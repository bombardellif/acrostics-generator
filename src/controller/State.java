package controller;

import model.FirstLetterFrequency;

public class State {

    private Double cost;

    private Double estimatedCost;

    private Text text;

    public void State(Text text, Double cost) {
        if (text == null)
            throw new IllegalArgumentException("State: Parameter text must not be null");
        if (cost == null)
            throw new IllegalArgumentException("State: Parameter cost must not be null");
        
        this.text = text;
        this.cost = cost;
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
        return this.text;
    }

}
