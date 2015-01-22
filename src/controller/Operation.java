package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public abstract class Operation {

    /**
     * 	
     */
    private final Double cost;
    protected final int lmin = 50;
    protected final int lmax = 70;
    
    
    public Operation(Double cost) {
        this.cost = cost;
    }

    /**
     * 						
     */
    public abstract List<Text> execute(Text text) throws Exception;

    public Double getCost() {
        return this.cost;
    }
    
    
        /**
    * @param input 
    * A single string representing the input text
    * @return A text with lines of length \le lmax
     * @throws java.io.IOException
    */
    public Text StringToText(String input) throws IOException{
              	
        
        HyphenationOperation hyOp = new HyphenationOperation();
        StringTokenizer st=new StringTokenizer(input);
        String row ="";
        ArrayList<String> lines;
        lines = new ArrayList<>();
        Text text;
        
        
        
        int SpaceLeft= lmax;
	int SpaceWidth=1;
        
        if(!st.hasMoreTokens())
        {
            lines.add("");
            return new Text(lines);
            
            
        }
        
        
        
	
        do
	{
            String word=st.nextToken();
		if((word.length()+SpaceWidth)>SpaceLeft)
		{
                    //long word have to be hyphenated to fulfill the line
                    //length constraints
                    
                        row = row.substring(0, row.length()-1);
                        lines.add(row);
                        row = "";
                        row += word.endsWith("-") ? word.substring(0, word.length()-1) : word + " ";

                        if(!st.hasMoreTokens())
                            lines.add(word);


                        SpaceLeft = lmax - word.length();
                    
		}
		else
		{
                    //if text is small then one single line
                    if (!st.hasMoreElements()){ 
                        row += word;
                        lines.add(row);
                        break;
                        
                    }
                    
                    row += word.endsWith("-") ? word.substring(0, word.length()-1) : word + " ";
                    SpaceLeft-=(word.length()+SpaceWidth);
		}
	}//do
        while(st.hasMoreTokens());
	
      
        
         
        
        text = new Text(lines);
        
        return text;
      
    }
    
    
    
    
    
    
    

}
