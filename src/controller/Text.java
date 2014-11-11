package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Text {

    private ArrayList<String> lines;

    public void Text(Text text) {
        Text(text.lines);
    }
    
    public void Text(List<String> lines) {
        this.lines = new ArrayList<>(lines);
    }

    public String remainingAcrostic(String acrostic) {
        return null;
    }

    public boolean goalTest(String acrostic) {
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
