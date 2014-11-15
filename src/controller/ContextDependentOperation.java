package controller;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public abstract class ContextDependentOperation extends Operation {
    
    protected static final String REGEX_SPLITINWORDS = "\\s";
    private static final int DEFAULT_NGRAM_SIZE = 5;
    private static final int MIN_NGRAM_SIZE = 1;
    private static final int MAX_NGRAM_SIZE = 5;
    
    public ContextDependentOperation(Double cost) {
        super(cost);
    }
    
    /**
     * Given n(size) of the NGRAM and a list of words, returns the NGRAM with the central word in the given position. If it is not possible
     * to place this word in center, then place it as near to the center as possible. If the size of the list of words is less than n, then
     * returns the biggest NGRAM possible. If list of words is null, return null.
     * @param n Size of desired NGRAM. Has to lie between MIN_NGRAM_SIZE and MAX_NGRAM_SIZE.
     * @param words List of words, from which the NGRAM should be taken.
     * @param centralPosition Position in the list of the central word of desired NGRAM. Cannot be out of bounds.
     * @return List of words that represents the NGRAM just taken. Or null in case of null arguments
     */
    private List<String> getNGramAround(int n, List<String> words, int centralPosition){
        if (words != null){
            if (n < MIN_NGRAM_SIZE || n > MAX_NGRAM_SIZE){
                throw new InvalidParameterException("Invalid value for n");
            }
            if (centralPosition < 0 || centralPosition >= words.size()){
                throw new InvalidParameterException("Invalid value for centralPosition");
            }
            
            //Consider a text smaller than the NGRAM
            if (words.size() < n){
                n = words.size();
            }
            //begin and end positions of the NGRAM
            int begin = centralPosition - ((n-1)/2);
            int end = centralPosition + (n/2);
            
            assert (end - begin)+1 == n;
            
            //In case that the centralPosition is one of the first words
            if (begin < 0){
                //Then shifts the NGRAM to the right, so it keeps the desired size
                end += -begin;
                begin = 0;
            }
            //In case that the centralPosition is one of the first words
            if (end >= words.size()){
                //Then shifts the NGRAM to the left, so it keeps the desired size
                begin -= end - words.size();
                end = words.size();
            }
            
            assert begin >= 0 && begin < words.size();
            assert end >= 0 && end < words.size();
            assert (end - begin)+1 == n;
            
            return new ArrayList<>(words.subList(begin, end+1));
        }else{
            return null;
        }
    }
    
    /**
     * Given a list of words of a text, find all possible words that can be inserted in this text keeping the semantics. And creates
     * new versions of the original text with these possibilities. If text or words are null, return null
     * @param text Original Text
     * @param words List of words corresponding to the text
     * @return List of new possible versions of the text. Each element represents a different possibility of inserting a word.
     * Or null (in case of null arguments)
     */
    protected List<Text> insertWord(Text text, List<String> words){
        if (text != null && words != null){
            
            //@TODO: Assert that words correspond to text
            
            int pos = 0;
            for (String word : words){
                List<String> nGram = getNGramAround(DEFAULT_NGRAM_SIZE, words, pos);
                
                
                pos++;
            }
            return null;
        }else{
            return null;
        }
    }
    
    protected List deleteWord(Text text) {
        return null;
    }

}
