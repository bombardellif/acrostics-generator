package controller;

import java.util.ArrayList;
import java.util.List;

public class EnsureConstraintsOperation extends Operation {

    private static final Double COST = 0.0;
    
    public EnsureConstraintsOperation() {
        super(COST);
    }

    @Override
    public List<Text> execute(Text text) 
    {
        /*Initializing Variables*/
        ArrayList<Text> correctedTextinList = new ArrayList();
        ArrayList<String> lines = text.getLines();

        Text correctedText = new Text(text);
        correctedTextinList.add(correctedText);
        
        for (int lineNumber = 0; lineNumber < lines.size() -1; lineNumber++)
        {
            int sizeDifference = 0;
            String currentLine = lines.get(lineNumber);
            String nextLine = lines.get(lineNumber + 1);
            String movedPart;
            String remainingPart;
            
            if(currentLine.length() < lmin)
            {
                sizeDifference = lmin - currentLine.length();
                movedPart = nextLine.substring(0,sizeDifference);
                remainingPart = nextLine.substring(sizeDifference);
                
                /* See if a word is broken in two */
                if(remainingPart.startsWith(" "))
                {
                    /* If it is remove the blank space */
                    nextLine = remainingPart.substring(1);
                    currentLine = currentLine + movedPart;
                    
                    lines.set(lineNumber, currentLine);
                    lines.set(lineNumber + 1, nextLine);
                }
                else
                {
                    /* Otherwise move one letter less to accomodate the hyphen */
                    movedPart = nextLine.substring(0, sizeDifference-1);
                    currentLine = currentLine + movedPart + "-";
                    nextLine = remainingPart;
                    
                    lines.set(lineNumber, currentLine);
                    lines.set(lineNumber + 1, nextLine);
                }
            }
            
            if(currentLine.length() > lmax)
            {
                sizeDifference = currentLine.length() - lmax;
                movedPart = currentLine.substring(sizeDifference);
                remainingPart = currentLine.substring(0,sizeDifference);
                
                /* See if a word is broken in two */
                if(movedPart.startsWith(" "))
                {
                    /* If it is remove the blank space */
                    nextLine = movedPart.substring(1) + nextLine;
                    currentLine = remainingPart;
                    
                    lines.set(lineNumber, currentLine);
                    lines.set(lineNumber + 1, nextLine);
                }
                else
                {
                    /* Otherwise move one letter more to accomodate the hyphen */
                    movedPart = currentLine.substring(sizeDifference -1);
                    nextLine =  movedPart + nextLine;
                    currentLine = remainingPart + "-";
                    
                    lines.set(lineNumber, currentLine);
                    lines.set(lineNumber + 1, nextLine);
                }
            }
        }
        
        
        return correctedTextinList;
    }
    
    
    
        //ensures that linelength values are between lmin and lmax
    public boolean ensureLineLengthConstraints(Text text){
        ArrayList<String> lines = text.getLines();
        for(int i=0; i<lines.size(); i++){
            
            //last line has no length restriction
            if(i== (lines.size()-1) )
                return true;
            
            
            
            if(lines.get(i).length()< lmin){ 
                
                //System.err.println("Length of line " + i + " is too small, " + "Length: " + lines.get(i).length() 
                //+ ", Line: " + lines.get(i));
                
                return lines.get(i).charAt(lines.get(i).length()-1) == '.';
               
            }
            
            if(lines.get(i).length()> lmax){               
                
                //System.err.println("Length of line " + i + " is too large, " + "Length: " + lines.get(i).length()
                //+ ", Line: " + lines.get(i));
                
                return false;
            }
          
        }
        
        //System.out.println("ecOp successfull");
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
