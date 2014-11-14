package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.LetterFrequency;

public class Text {

    private ArrayList<String> lines;

    public void Text(Text text) {
        if (text == null)
            throw new IllegalArgumentException("Text: Parameter text must not be null");
        
        Text(text.lines);
    }
    
    public void Text(List<String> lines) {
        if (lines == null)
            throw new IllegalArgumentException("Text: Parameter lines must not be null");
        
        this.lines = new ArrayList<>(lines);
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
        if (!Objects.equals(this.lines, other.lines)) {
            return false;
        }
        return true;
    }

    
}
