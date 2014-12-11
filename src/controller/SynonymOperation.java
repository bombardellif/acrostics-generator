package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.NetSpeakDAO;

public class SynonymOperation extends ContextDependentOperation {

    private static final double localQuality = 0.5;
    private static final Double COST = ((double)1)/localQuality;

    public SynonymOperation() {
        super(COST);
    }
    
    @Override
    public List<Text> execute(Text text) throws Exception{
        if (text != null){
            //Get all the text in one line
            String textInString = text.toOneLineString();
            
            ArrayList<String> words = new ArrayList<>( Arrays.asList(textInString.split(REGEX_SPLITINWORDS)) );

            List<Text> ret = changeForSynonym(text, words);
            
            ret.stream().forEach((r) -> System.out.println(r));
            
            return ret;
        }else{
            return null;
        }
    }
    
    /**
     * Given a list of words of a text, find all possible words that can be changed by their Synonym in this text keeping the semantics. And creates
     * new versions of the original text with these possibilities. If text or words are null, return null
     * 
     * @param text Original Text
     * @param words List of words corresponding to the text
     * @return List of new possible versions of the text. Each element represents a different possibility of removing a word.
     * Or null (in case of null arguments). If there is no possibilities, return empty list.
     * @throws java.lang.Exception Database or Network connection problems
     */
    public List<Text> changeForSynonym(final Text text, final List<String> words) throws Exception{
        if (text != null && words != null){
            //@TODO: Assert that words correspond to text
            
            ArrayList<Text> newPossibleTexts = new ArrayList<>();
            
            //Iterate over word positions. Zero means first word. words.size()-1 means position of last word
            for (int textWordPos = 0; textWordPos < words.size(); textWordPos++){
                
                List<String> nGram = getNGramAroundWord(DEFAULTDELETE_NGRAM_SIZE, words, textWordPos);
                
                assert nGram != null;
                assert nGram.size() > 0;
                
                int posIntoNGram;
                //If it is in the beginning of the text, then position into NGRAM have to be correspondent (go to the begin of NGRAM)
                if (textWordPos < nGram.size() / 2){
                    posIntoNGram = textWordPos;
                }else if (textWordPos >= words.size() - (nGram.size() / 2)){
                    //If it is int the end of text, then position into NGRAM have to be correspondent (go to the end of NGRAM)
                    posIntoNGram = nGram.size() - (words.size() - textWordPos);
                }else{
                    //otherwise the position will be always in center of NGRAM
                    posIntoNGram = nGram.size() / 2;
                }
                assert posIntoNGram >=0 && posIntoNGram < nGram.size();
                
                //Actual change of the word
                //@TODO Implement Synonym Database
                String synonym = "_123_";
                nGram.set(posIntoNGram, synonym);
                
                //Create new version of the text without this word if the nGram without this word is frequent in the language
                if (NetSpeakDAO.isFrequent(nGram))
                    newPossibleTexts.add(text.changeWord(textWordPos, synonym));
            }
            
            return newPossibleTexts;
        }else{
            return null;
        }
    }

}
