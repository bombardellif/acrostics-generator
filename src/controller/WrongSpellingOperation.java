package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

/**
 *
 * @author Bruno
 */
public class WrongSpellingOperation extends Operation {
    
    
    private static final double localQuality = 0.7;
    private static final Double COST = ((double)1)/localQuality;
    
    public WrongSpellingOperation() {
        super(COST);
    }

    @Override
    public List<Text> execute(Text text) 
    {
         /* Initializing Variables */        
        ArrayList<Text> returnList = new ArrayList();
        ArrayList<String> lines = text.getLines();
       
       /* For each line look for letters to substitute */
        for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++)
        {
            String currentLine = lines.get(lineNumber);
            String beforeChar;
            String afterChar;
            int index;
            
            EnsureConstraintsOperation ecOp = new EnsureConstraintsOperation();
            
            for (index=0;index<currentLine.length();index++)
            {
                ArrayList<String> newText = text.getLines();
                //System.out.println(currentLine.charAt(index));
                if(currentLine.charAt(index) == 'ß')
                {
                    beforeChar = currentLine.substring(0, index);
                    afterChar = currentLine.substring(index+1);
                    newText.set(lineNumber, beforeChar + "ss" + afterChar);
                    Text completeNewText = new Text(newText);
                    returnList.add(ecOp.execute(completeNewText).get(0));
                }
                else if (currentLine.charAt(index) == 'ü')
                {
                    beforeChar = currentLine.substring(0, index);
                    afterChar = currentLine.substring(index+1);
                    newText.set(lineNumber, beforeChar + "ue" + afterChar);
                    Text completeNewText = new Text(newText);
                    returnList.add(ecOp.execute(completeNewText).get(0));
                }
                else if (currentLine.charAt(index) == 'Ü')
                {
                    beforeChar = currentLine.substring(0, index);
                    afterChar = currentLine.substring(index+1);
                    newText.set(lineNumber, beforeChar + "ue" + afterChar);  
                    Text completeNewText = new Text(newText);
                    returnList.add(ecOp.execute(completeNewText).get(0));
                }
                else if (currentLine.charAt(index) == 'ä')
                {
                    beforeChar = currentLine.substring(0, index);
                    afterChar = currentLine.substring(index+1);
                    newText.set(lineNumber, beforeChar + "ae" + afterChar);  
                    Text completeNewText = new Text(newText);
                    returnList.add(ecOp.execute(completeNewText).get(0));
                    //System.out.println(completeNewText);
                }
                else if (currentLine.charAt(index) == 'Ä')
                {
                    beforeChar = currentLine.substring(0, index);
                    afterChar = currentLine.substring(index+1);
                    newText.set(lineNumber, beforeChar + "Ae" + afterChar);  
                    Text completeNewText = new Text(newText);
                    returnList.add(ecOp.execute(completeNewText).get(0));
                }
                else if (currentLine.charAt(index) == 'ö')
                {
                    beforeChar = currentLine.substring(0, index);
                    afterChar = currentLine.substring(index+1);
                    newText.set(lineNumber, beforeChar + "oe" + afterChar);
                    Text completeNewText = new Text(newText);
                    returnList.add(ecOp.execute(completeNewText).get(0));
                }
                else if (currentLine.charAt(index) == 'Ö')
                {
                    beforeChar = currentLine.substring(0, index);
                    afterChar = currentLine.substring(index+1);
                    newText.set(lineNumber, beforeChar + "Oe" + afterChar);
                    Text completeNewText = new Text(newText);
                    returnList.add(ecOp.execute(completeNewText).get(0));
                }
                
            }
        }
        
        return returnList;
    }
    
    @Override
    public String toString(){
        return "WrongSpellingOperation";
    }
}
    
