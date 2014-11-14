package controller;

import java.util.List;

public class WordInsertionDeletionOperation extends ContextDependentOperation {

    private static final Double COST = 0.0;

    public WordInsertionDeletionOperation() {
        super(COST);
    }
    
    @Override
    public List<Text> execute(Text text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
