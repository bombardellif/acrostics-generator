package controller;

import de.denkselbst.sentrick.sbd.SentenceBoundaryDetector;
import de.denkselbst.sentrick.tokeniser.token.Token;
import de.denkselbst.sentrick.util.SbdProvider;
import de.denkselbst.sentrick.util.SbdProviderException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LineBreakOperation extends Operation {

    private static final double localQuality = 1.0;
    private static final Double COST = ((double)1)/localQuality;
    //private int lineNo;

    public LineBreakOperation() {
        super(COST);
    }
    
    @Override
    public List<Text> execute(Text text) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SbdProviderException {
       
   
        ArrayList<Text> LineBreakList = new ArrayList();
        EnsureConstraintsOperation ecOp = new EnsureConstraintsOperation();
        ArrayList<String> lines = text.getLines();
    
        
        //linebreaks when lines lenghts is in between the [lmin,lmax]!
        //(The ŕemaining linebreaks appear after a fullstop.) 
        
        for(int i=0; i< (lines.size()); i++){
            
        
            String line = lines.get(i);
            String[] lta; //line token array     
            //construct an array of tokens from line
            lta = line.split(" "); 



            int sum = 0;
           

             //find index of the first possible break
            for(int k=0; k< lta.length-1; k++){

                    sum += (lta[k].length()+1);


                if(sum >= lmin && sum <= lmax){
                   
                    String str1 = line.substring(0,sum);
                    String str2 = line.substring(sum);


                    String str3= (str2 + " ");
                    
                    for(int j=i+1; j< lines.size(); j++){

                        if(j == (lines.size() -1)){
                            str3 += lines.get(j);
                        }                   
                        else{
                            str3 += lines.get(j) + " ";
                        }

                    }


                    //System.out.println();
                    //System.out.println("str3:");
                    //System.out.println(str3);

                    Text text2 = StringToText(str3);
                    ArrayList<String> lines3 = new ArrayList<>();

                    lines3.addAll(lines.subList(0, i));
                    lines3.add(str1);
                    lines3.addAll(text2.getLines());

                    Text textResult = new Text(lines3);
                    
                    
                    
                    if(ecOp.ensureLineLengthConstraints(textResult) )
                        LineBreakList.add(textResult);
                    

                    //System.out.println();
                    //System.out.println(textResult);
                 
                }
            
            }

        }
        
        

        //Now perform the line break associated with a fullstop.
        
            
        String provider = "de.denkselbst.sentrick.classifiers.german.GermanSbdProvider";
        SbdProvider sbdProvider = (SbdProvider) Class.forName(provider).newInstance(); 
        
        //concatenate the lines to a single string
        String str="";
        
        for(int i=0; i< lines.size(); i++){
            
            if(i != (lines.size() -1) )
                str += (lines.get(i) + " ");
            else
                str += lines.get(i);
            
            
        }
        
 
	//convert String into InputStream
	InputStream is = new ByteArrayInputStream(str.getBytes());
 
	//read it with Reader
        Reader in = new InputStreamReader(is);
        

        
        SentenceBoundaryDetector sbd = sbdProvider.getPlainTextSentenceBoundaryDetector(in);
        
        Token t;
        
        //list of full stop positions
        ArrayList<Integer> dotIndexList = new ArrayList<>();
        
        //ith-list entry
        int i=0;
        
        while( (t=sbd.readToken()) != null){
            
            if(dotIndexList.isEmpty())
                dotIndexList.add(t.getLength());
            
            else
                dotIndexList.set(i, dotIndexList.get(i)+t.getLength());
            
            //System.out.print(t.getText());
            
            if(t.isSentenceBoundary()){
		
                i++;               
                dotIndexList.add(i,dotIndexList.get(i-1));
                
                //System.out.print("</s>");
                
                
            }
        }
        
        
        
        sbd.close();
        
        //last entry is inserted twice, remove one copy
        dotIndexList.remove((dotIndexList.size()-1));
       
        //System.out.println(dotIndexList);

	//System.err.println("\n\nDone.");
        
        
        
        for(i=0; i< dotIndexList.size(); i++){
            
            //last dot is excluded
            if(i != (dotIndexList.size()-1)){
                
                String untilDot = str.substring(0,dotIndexList.get(i)+1);

                String afterDot = str.substring(dotIndexList.get(i)+1);


                Text text1 = StringToText(untilDot);
                Text text2 = StringToText(afterDot);

                ArrayList<String> list3 = new ArrayList();
                list3.addAll(text1.getLines());          
                list3.addAll(text2.getLines());


                Text text3 = new Text(list3);

                //System.out.println(text3);
                //System.out.println();

                LineBreakList.add(text3);
            }
            
            
        }
         
       
        return LineBreakList;
    }

  
    
    @Override
    public String toString(){
        return "LineBreakOperation";
        
    }
    
    
        
   

}
