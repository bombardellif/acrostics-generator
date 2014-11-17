package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.LetterFrequency;

public class Text {

    private static final String REGEX_SPLITINWORDS = " ";
    
    private ArrayList<String> lines;

    //constructor
    public Text(Text text) {
        if (text == null)
            throw new IllegalArgumentException("Text: Parameter text must not be null");
        
        setLines(text.lines);
    }
    
    //constructor 
    public Text(List<String> lines) {
        setLines(lines);
    }
	
    private void setLines(List<String> lines){
        if (lines == null)
            throw new IllegalArgumentException("Text: Parameter lines must not be null");
        
        this.lines = new ArrayList<>(lines);
    }
    
    private String insertWordInLine(String line, int insertSpacePosition, String word){
        if (line == null){
            throw new IllegalArgumentException("insertWordInLine: Invalid line string");
        }
        if (insertSpacePosition < 0){
            throw new IllegalArgumentException("insertWordInLine: Invalid insert position");
        }
        
        if (word != null){
            StringBuilder sb = new StringBuilder();
            
            //Search for the right position with regex. Each regex occurence means one space in line
            Matcher splitMatcher = Pattern.compile(REGEX_SPLITINWORDS).matcher(line);
            int i = 0;
            int targetPos = 0; //Position in the string (i.e. n-th character in string)
            while(i < insertSpacePosition){
                //If there is no spaces more
                if (splitMatcher.find() == false){
                    //If desired space position is the last (Right after last word)
                    if (i == insertSpacePosition - 1){
                        //Then target is last position of line
                        targetPos = line.length();
                    }else{
                        //Otherwise, the desired space position is invalid, because it is grater than the number of possible insert positions
                        throw new IllegalArgumentException("insertWordInLine: Invalid insert position");
                    }
                }else{
                    //If there is space, then search for the right target string position
                    targetPos = splitMatcher.start();
                }
                i++;
            }

            assert targetPos >= 0 && targetPos <= line.length();
            
            String extraSpace =  (targetPos == 0) ? "" : " ";
            sb.append(line.substring(0, targetPos)) //before new word position
                    .append(extraSpace).append(word)       //new word
                    .append(line.substring(targetPos)); //Rest of line
            
            return sb.toString();
        }else{
            return line;
        }
    }
    
    /**
     * Add the new word inside the text at the space position given. Zero means beginning of the text.
     * One means first space. Last position is after the last word. Does not modify original text, instead create
     * new version (new instance). If new word is not empty nor null, inserts also a space right after.
     * The functions takes care about Capitalized characters (Beginning of sentences) and punctuations.
     * @param word New word to be inserted
     * @param spacePosition n-th space in which the word should be inserted. Must lie in the right limits.
     * @return new instance of text modified. if word is null or empty return copy of the exact original text
     */
    public Text addWordInSpace(String word, int spacePosition) throws IllegalArgumentException{
        assert this.lines != null;
        
        if (spacePosition < 0){
            throw new IllegalArgumentException("addWordInSpace: Invalid space position");
        }
        
        Text newText = this;
        int i = 0;
        int remainingPos = spacePosition;
        boolean inserted = false;
        for (String line : this.lines){
            
            //Remove leading spaces of first line and trailing spaces of last line.
            //Has to do it, so it keeps consistency with toString method
            if (i == 0){
                line = line.replaceFirst("^\\s+", "");
            }else if (i == this.lines.size() - 1){
                line = line.replaceFirst("\\s+$", "");
            }
            
            List<String> words = new ArrayList<>( Arrays.asList(line.split(REGEX_SPLITINWORDS)) );
            
            assert words != null;
            
            //The amount of spaces in this line is given by the number of words. One space before each word
            int spaceCountThisLine;
            if (i > 0 && this.lines.get(i-1).endsWith("-")){
                //If previous line ended with hyphen, then the first "space" is not valid (Continuation of previous hyphenized word)
                spaceCountThisLine = words.size() - 1;
            }else{
                //There is one space before each word
                spaceCountThisLine = words.size();
            }
            
            assert spaceCountThisLine >= 0;
            
            //In the last line consider also the last "space" of the text. i.e. the position after last word.
            if(i == this.lines.size() - 1){
                spaceCountThisLine++;
            }
            
            assert remainingPos >= 0;
            //Amount of remaining positions is smaller than space counts in this line
            if (remainingPos < spaceCountThisLine){
                //Then insert new word here
                
                int insertPosition;
                //If previous line was hyphenized, shifts the insert position, in order to not insert in the first position (half of contiunued word)
                if (i > 0 && this.lines.get(i-1).endsWith("-")){
                    insertPosition = remainingPos + 1;
                }else{
                    insertPosition = remainingPos;
                }
                
                String newLine = this.insertWordInLine(line, insertPosition, word);
                
                List<String> newLines = new ArrayList<>(this.lines);
                newLines.set(i, newLine);
                newText = new Text(newLines);
                inserted = true;
                
                break;
            }else{
                //Other wise, keep searching for the right line to insert it
                remainingPos -= spaceCountThisLine;
            }
            
            i++;
        }
        if (inserted == false){
            throw new IllegalArgumentException("addWordInSpace: Invalid space position");
        }
        return newText;
    }
    
    public String remainingAcrostic(String acrostic) {
        if (acrostic == null)
            throw new IllegalArgumentException("remainingAcrostic: Parameter acrostic must not be null");
        
        int acrosticsLength = acrostic.length();
        
        for (int i=0; i < acrosticsLength; i++) {
            if (this.lines.get(i).charAt(0) != acrostic.charAt(i)) {
                return acrostic.substring(i);
            }
        }
        
        return "";
    }

    public boolean goalTest(String acrostic) {
        if (acrostic == null)
            throw new IllegalArgumentException("goalTest: Parameter acrostic must not be null");
        
        int acrosticsLength = acrostic.length();
        
        if (this.lines.size() < acrosticsLength) {
            return false;
        }
        
        for (int i=0; i < acrosticsLength; i++) {
            if (this.lines.get(i).charAt(0) != acrostic.charAt(i)) {
                return false;
            }
        }
        
        return true;
    }
    
    public Double probabilityLeastFrequentLetterInversed(String remainingAcrostic) {
        if (remainingAcrostic == null)
            throw new IllegalArgumentException("probabilityLeastFrequentLetterInversed: Parameter remainigAcrostic must not be null");
        
        double leastFrequent = Double.NEGATIVE_INFINITY;
        double curentValue;
        for (int i=remainingAcrostic.length(); i>=0; i--) {
            curentValue = LetterFrequency.getFrequencyInversed(remainingAcrostic.charAt(i));
            if (curentValue > leastFrequent)
                leastFrequent = curentValue;
        }
        
        return leastFrequent;
    }
    
    protected String concatenateLines(Integer l1, Integer l2) {
        Integer size = this.lines.size();
        if (l1 == null || l1 < 0 || l1 >= size)
            throw new IllegalArgumentException("concatenateLines: Parameter l1 is invalid");
        if (l2 == null || l2 < 0 || l2 >= size)
            throw new IllegalArgumentException("concatenateLines: Parameter l2 is invalid");
        
        String result;
        
        String line1 = this.lines.get(l1);
        
        if (line1.endsWith("-")) {
            result = line1.substring(0, line1.length()-1) + this.lines.get(l2);
        } else {
            result = line1 + " " + this.lines.get(l2);
        }
        
        return result;
    }
    
    public String toOneLineString() {
        StringBuilder sb = new StringBuilder();
        
        for (String line: this.lines) {
            if (line.endsWith("-")) {
                sb.append(line.substring(0, line.length()-1));
            } else {
                sb.append(line).append(' ');
            }
        }
        
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        return getLines().stream()
            .map(line -> line + "\n")
            .reduce("", String::concat);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.lines);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Text other = (Text) obj;
        return Objects.equals(this.lines, other.lines);
    }
    
    /**
     * @return the lines
     */
    public ArrayList<String> getLines() {
        return lines;
    }
    
}
