package controller;

import java.util.List;

public abstract class Operation {

    /**
     * 	
     */
    private final Double cost;
    
    public Operation(Double cost) {
        this.cost = cost;
    }

    /**
     * 						
     */
    public abstract List<Text> execute(Text text);

    public Double getCost() {
        return this.cost;
    }

    public String getWord(String source, int position) {
        return null;
    }

}
