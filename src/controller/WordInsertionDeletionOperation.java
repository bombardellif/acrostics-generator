package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordInsertionDeletionOperation extends ContextDependentOperation {

    private static final double localQuality = 0.5;
    private static final Double COST = ((double)1)/localQuality;

    public WordInsertionDeletionOperation() {
        super(COST);
    }
    
    @Override
    public List<Text> execute(Text text) throws Exception{
        if (text != null){
            //Get all the text in one line
            String textInString = text.toOneLineString();
            
            ArrayList<String> words = new ArrayList<>( Arrays.asList(textInString.split(REGEX_SPLITINWORDS, -1)) );
        
            List<Text> ret = insertWord(text, words);
            ret.addAll(deleteWord(text, words));
            
            System.out.println("==========");
            ret.stream().forEach(r -> {
                System.out.println(r);
            });
            
            return ret;
        }else{
            return null;
        }
    }
    
    @Override
    public String toString(){
        return "WorInsertionDeletionOperation";
    }
}
