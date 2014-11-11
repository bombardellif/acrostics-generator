package controller;

import java.util.List;

public class WrongHyphenationOperation extends Operation {

    private static final Double COST = 0.0;
    
    public WrongHyphenationOperation() {
        super(COST);
    }

    @Override
    public List<Text> execute(Text text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
