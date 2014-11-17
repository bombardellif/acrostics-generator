package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordInsertionDeletionOperation extends ContextDependentOperation {

    private static final Double COST = 0.0;

    public WordInsertionDeletionOperation() {
        super(COST);
    }
    
    @Override
    public List<Text> execute(Text text) throws Exception{
        if (text != null){
            //Get all the text in one line
            String textInString = text.toOneLineString();
            
            ArrayList<String> words = new ArrayList<>( Arrays.asList(textInString.split(REGEX_SPLITINWORDS)) );
        
            return insertWord(text, words);
            
            //@TODO
            //deleteWord(textInString);
            
        }else{
            return null;
        }
    }

}