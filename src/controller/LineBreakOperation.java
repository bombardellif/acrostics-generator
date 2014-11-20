package controller;

import java.util.ArrayList;
import java.util.List;

public class LineBreakOperation extends Operation {

    private static final double localQuality = 0.8;
    private static final Double COST = ((double)1)/localQuality;
    //private int lineNo;

    public LineBreakOperation() {
        super(COST);
    }
    
    @Override
    public List<Text> execute(Text text) {
       
   
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
                  

                    //System.out.println("str1:");
                    //System.out.println(str1);
                    //System.out.println("str2:");
                    //System.out.println(str2);

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
        
        for(int i=0; i< (lines.size() ) ; i++){
            String line = lines.get(i);
            
            
            for(int k=0; k< line.length(); k++){
                //I assume only one Dot per line
                if( line.charAt(k) == '.' ){
                    
                    if( (k== (line.length() -1)) && (i == (lines.size()-1)) )
                        break;
                    
                    
                    String untilDot = line.substring(0,k+1);
                    String afterDot = line.substring(k+1);
                    
                    
                    String str2 = afterDot;
                    
                    for(int j= i+1; j< lines.size(); j++){                        
                       
                            str2 += (" " + lines.get(j));                                           
                        
                    }
                    
                    
                    Text text2;
                    text2 = StringToText(str2);
                    
                    
                   
                    ArrayList<String> list3 = new ArrayList();
                    list3.addAll(lines.subList(0, i));
                    list3.add(untilDot);
                    list3.addAll(text2.getLines());
                    
                    
                    Text text4 = new Text(list3);
                    
                    
                    //System.out.println();
                    //System.out.println(text4);
                    
                    if(ecOp.ensureLineLengthConstraints(text4) )
                        LineBreakList.add(text4);
                    
                    
                    
                    
                   
                }
                
            }
            
            
            
            
            
            
            
        }
        
        
        
        //printing out the result
        
        /*
        LineBreakList.stream().map((LineBreakList1) -> {
            System.out.println();
            return LineBreakList1;
        }).map((LineBreakList1) -> {
            System.out.println();
            return LineBreakList1;
        }).forEach((LineBreakList1) -> {
            System.out.println(LineBreakList1);
        });
        */
        
        
        
        //System.out.println("Size of LineBreakList: " + LineBreakList.size());
        return LineBreakList;
    }

  
    
    @Override
    public String toString(){
        return "LineBreakOperation";
        
    }
    
    
        
   

}
