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
        Text correctedText;
                
        String currentLine;
        String nextLine;  
        String movedPart;
        String remainingPart;
        int sizeDifference = 0;
        
        Text testText = new Text(lines);
        
        lines = testText.getLines();
        
        for (int lineNumber = 0; lineNumber < lines.size()-1; lineNumber++)
        {
            currentLine = lines.get(lineNumber);
            nextLine = lines.get(lineNumber + 1);
            boolean separateWords = false;
            if(currentLine.length() < lmin)
            {
                sizeDifference = lmin - currentLine.length();
                movedPart = nextLine.substring(0,sizeDifference);
                remainingPart = nextLine.substring(sizeDifference);
      
            
                //If there's a world already hyphenated have to bring it together
                if(currentLine.charAt(currentLine.length()-1)=='-')
                {
                    currentLine = currentLine.substring(0,currentLine.length()-1);
                }
                //Test if the current line would begin with a space
                if(movedPart.startsWith(" ", movedPart.length()))
                {
                    movedPart = nextLine.substring(0, sizeDifference+1);
                    remainingPart = nextLine.substring(sizeDifference+1);
                }
                //Test if the next line would begin with a space
                if(remainingPart.startsWith(" "))
                {
                    remainingPart = remainingPart.substring(1);
                    separateWords = true;
                }
                /* See if a word is broken in two */
                if(separateWords == false)
                {
                    movedPart = movedPart + "-";
                }
                //Sets the values
                currentLine = currentLine + movedPart;
                nextLine = remainingPart;
                lines.set(lineNumber, currentLine);
                lines.set(lineNumber + 1, nextLine);
            }
            
            if(currentLine.length() > lmax)
            {
                sizeDifference = currentLine.length() - lmax;
                movedPart = currentLine.substring(lmax);
                remainingPart = currentLine.substring(0,lmax);

                /* See if a word is broken in two */
                if(movedPart.startsWith(" "))
                {
                    /* If it is remove the blank space */
                    nextLine = movedPart.substring(1) + nextLine;
                    currentLine = remainingPart;
                    
                    lines.set(lineNumber, currentLine);
                    lines.set(lineNumber+1, nextLine);
                }
                else
                {
                    /* Otherwise move one letter less to accomodate the hyphen */
                    if(currentLine.startsWith(" ", lmax-1))
                    {
                        movedPart = currentLine.substring(lmax-1);
                        remainingPart = currentLine.substring(0,lmax-2);
                        currentLine = remainingPart;
                        nextLine = movedPart + nextLine;
                    
                        lines.set(lineNumber, currentLine);
                        lines.set(lineNumber+1, nextLine);
                    }
                    else
                    {
                        movedPart = currentLine.substring(lmax-1);
                        remainingPart = currentLine.substring(0,lmax-1);
                        currentLine = remainingPart + "-";
                        nextLine = movedPart + nextLine;
                    
                        lines.set(lineNumber, currentLine);
                        lines.set(lineNumber+1, nextLine);
                    }
                }
                
            }
        }
        
        String lastLine = lines.get(lines.size()-1);
           
        while(lastLine.length() > lmax)
            {
                currentLine = lines.get(lines.size()-1);
                String createLine;
                movedPart = currentLine.substring(lmax);
                remainingPart = currentLine.substring(0,lmax);

                
                /* See if a word is broken in two */
                if(movedPart.startsWith(" "))
                {
                    /* If it is remove the blank space */
                    nextLine = movedPart.substring(1);
                    currentLine = remainingPart;
                    
                    lines.set(lines.size()-1, currentLine);
                    lines.add(nextLine);
                }
                else
                {
                    /* Otherwise move one letter less to accomodate the hyphen */
                    if(currentLine.startsWith("", lmax-1))
                    {
                        movedPart = currentLine.substring(lmax-1);
                        remainingPart = currentLine.substring(0,lmax-2);
                        currentLine = remainingPart;
                        nextLine = movedPart;
                    
                        lines.set(lines.size()-1, currentLine);
                        lines.add(nextLine);
                    }
                    else
                    {
                        movedPart = currentLine.substring(lmax-1);
                        remainingPart = currentLine.substring(0,lmax-1);
                        currentLine = remainingPart + "-";
                        nextLine = movedPart;
                    
                        lines.set(lines.size()-1, currentLine);
                        lines.add(nextLine);
                    }
                }
                lastLine = lines.get(lines.size()-1);
            }
        
        correctedText = new Text(lines);
        correctedTextinList.add(correctedText);
        
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
