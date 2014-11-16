package controller;

import java.util.ArrayList;
import java.util.List;
import model.NetSpeakDAO;

public abstract class ContextDependentOperation extends Operation {
    
    protected static final String REGEX_SPLITINWORDS = " ";
    private static final char MANYNEWWORDS_SEARCHCHARACTER = '*';
    private static final int DEFAULTINSERT_NGRAM_SIZE = 4;
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
    private List<String> getNGramAroundWord(int n, List<String> words, int centralPosition) throws IllegalArgumentException{
        if (words != null){
            if (n < MIN_NGRAM_SIZE || n > MAX_NGRAM_SIZE){
                throw new IllegalArgumentException("Invalid value for n");
            }
            if (centralPosition < 0 || centralPosition >= words.size()){
                throw new IllegalArgumentException("Invalid value for centralPosition");
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
     * Given n(size) of the NGRAM and a list of words, returns the NGRAM with the central space in the given position. If it is not possible
     * to place this space in center, then place it as near to the center as possible. If the size of the list of words is less than n, then
     * returns the biggest NGRAM possible. If list of words is null, return null.
     * @param n Size of desired NGRAM. Has to lie between MIN_NGRAM_SIZE and MAX_NGRAM_SIZE.
     * @param words List of words, from which the NGRAM should be taken.
     * @param centralPosition Position in the list of the central space of desired NGRAM. Cannot be out of bounds.
     * @return List of words that represents the NGRAM just taken. Or null in case of null arguments
     */
    private List<String> getNGramAroundSpace(int n, List<String> words, int centralPosition) throws IllegalArgumentException{
        
        //The output of this function is very similar to the getNGramAroundWord, we can therefore
        //translate the position of space to the position of word
        int adaptedCentralPosition = centralPosition - 1;
        if (adaptedCentralPosition < 0){
            adaptedCentralPosition = 0;
        }else if (adaptedCentralPosition >= words.size()){
            adaptedCentralPosition = words.size()-1;
        }
        
        return getNGramAroundWord(n, words, adaptedCentralPosition);
    }
    
    /**
     * Given a list of words of a text, find all possible words that can be inserted in this text keeping the semantics. And creates
     * new versions of the original text with these possibilities. If text or words are null, return null
     * @param text Original Text
     * @param words List of words corresponding to the text
     * @return List of new possible versions of the text. Each element represents a different possibility of inserting a word.
     * Or null (in case of null arguments). If there is no possibilities, return empty list.
     * @throws java.lang.Exception Database or Network connection problems
     */
    protected List<Text> insertWord(Text text, List<String> words) throws Exception{
        if (text != null && words != null){
            
            //@TODO: Assert that words correspond to text
            
            ArrayList<Text> newPossibleTexts = new ArrayList<>();
            
            //Iterate over space positions. Zero means position just before first word. words.size() means position after last word
            for (int textSpacePos = 0; textSpacePos <= words.size(); textSpacePos++){
                
                List<String> nGram = getNGramAroundSpace(DEFAULTINSERT_NGRAM_SIZE, words, textSpacePos);
                
                assert nGram != null;
                assert nGram.size() > 0;
                
                int posIntoNGram;
                //If it is in the beginning of the text, then position into NGRAM have to be correspondent (go to the begin of NGRAM)
                if (textSpacePos < DEFAULTINSERT_NGRAM_SIZE / 2){
                    posIntoNGram = textSpacePos;
                }else if (textSpacePos > words.size() - (DEFAULTINSERT_NGRAM_SIZE / 2)){
                    //If it is int the end of text, then position into NGRAM have to be correspondent (go to the end of NGRAM)
                    posIntoNGram = DEFAULTINSERT_NGRAM_SIZE - (words.size() - textSpacePos);
                }else{
                    //otherwise the position will be always in center of NGRAM
                    posIntoNGram = DEFAULTINSERT_NGRAM_SIZE / 2;
                }
                assert posIntoNGram >=0 && posIntoNGram <= DEFAULTINSERT_NGRAM_SIZE;
                
                List<String> newPossibleWords = NetSpeakDAO.searchNewWords(nGram, MANYNEWWORDS_SEARCHCHARACTER, posIntoNGram);
                assert newPossibleWords != null;
                
                //Create new versions of the text. One new version for each possible word
                for (String newWord : newPossibleWords){
                    newPossibleTexts.add(text.addWordInSpace(newWord, textSpacePos));
                }
            }
            
            return newPossibleTexts;
        }else{
            return null;
        }
    }
    
    protected List<Text> deleteWord(Text text, List<String> words) throws Exception{
        return null;
    }

}
