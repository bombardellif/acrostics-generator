package controller;

import java.util.ArrayList;
import java.util.List;

public class WrongHyphenationOperation extends Operation {

    private static final double localQuality = 0.5;
    private static final Double COST = ((double)1)/localQuality;
    
    public WrongHyphenationOperation() {
        super(COST);
    }

    @Override

    public List<Text> execute(Text text) 
    {
        /* Initializing Variables */        
        ArrayList<Text> hyphenationList = new ArrayList();
        ArrayList<String> lines = text.getLines();
        
        
        /* For each line Hyphenate */
        for (int lineNumber = 0; lineNumber < lines.size() -1; lineNumber++)
        {
            /* Find the line to be altered and it's last word to hyphenate */
            String currentLine = lines.get(lineNumber);
            String words[] = currentLine.split(" ");
            String lastWord = words[words.length-1];

            if (currentLine.endsWith("-"))
            {
                /* Line already hyphenated, do nothing */ 
            }
            else
            {
                /* For every letter separate the word */
                for(int letter = 1; letter < lastWord.length(); letter++)
                {
                    /* Create new Text */
                    ArrayList<String> newText = text.getLines();
                    String finishedLine;
                    
                    /* Determine which parts go where */
                    String startOfWord = lastWord.substring(0,letter);
                    String endOfWord = lastWord.substring(letter);

                    /* Current line minus the hyphenated end */
                    if(currentLine.endsWith(" "))
                    {
                        finishedLine = currentLine.substring(0, currentLine.length()-lastWord.length()-1);
                    }   
                    else
                        finishedLine = currentLine.substring(0, currentLine.length()-lastWord.length());

                    finishedLine = finishedLine + startOfWord + "-";
                    
                    /* Next line must be altered as well */
                    String nextLine = lines.get(lineNumber + 1);
                    String alteredNextLine = endOfWord + " " + nextLine;

                    /* Sets the new lines into the new text*/
                    newText.set(lineNumber, finishedLine);
                    newText.set(lineNumber + 1, alteredNextLine);               

                    /* Adds the new text to the list */
                    Text completeNewText = new Text(newText);

                    /* DEBUG */
                //    if(lastWord.equalsIgnoreCase("war"))
               //     {
                //    System.out.println(completeNewText); 
                //    }
                    hyphenationList.add(completeNewText);
                }
            }
        }
        return hyphenationList;
    }

    @Override
    public String toString(){
        return "WrongHyphenationOperation";
    }
}
