package controller;

import java.util.List;

public abstract class ContextDependentOperation extends Operation {

    public ContextDependentOperation(Double cost) {
        super(cost);
    }

    protected List insertWord(Text text) {
        return null;
    }

    protected List deleteWord(Text text) {
        return null;
    }

}
