package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HyphenationOperation extends Operation {

    private static final double localQuality = 0.8;
    private static final Double COST = ((double)1)/localQuality;
    
    
    private final net.davidashen.text.Hyphenator h;
    private static final char SOFT_HYPHEN = '\u00AD';
    //private int lineNo;

    
    
    public HyphenationOperation() throws FileNotFoundException, IOException {
        super(COST);
          
        //initial the hyphenator object
        h=new net.davidashen.text.Hyphenator();
        //hyphenation for german language    
        h.loadTable(new java.io.BufferedInputStream(new java.io.FileInputStream("dehyphx.tex")));
 
        
    }
    
    
    private String deleteCharAt(String strValue, int index) {
        return strValue.substring(0, index) + strValue.substring(index + 1)  ;
        
    }
    
    //corrects the hyphenation algorithm when hyphen is produced
    //at the start or at the end

    /**
     *
     * @param str
     * @return a hyphenated string, where obvious wrong
     * hyphens (at 2nd position or 2nd last position) do not appear
     */
        public  String correctHyphenation(String str) {
        
        String hyphenated_str = "";
        String pre_hyphenated_str = h.hyphenate(str);
        
        String temp = pre_hyphenated_str;
        pre_hyphenated_str = temp.replace('\u002D', SOFT_HYPHEN); 
        
        //ZERO WIDTH SPACE character
        String[] preSplit = pre_hyphenated_str.split( "\u200B\\s+"  );
        
        //concatenate again
        for(int i=0; i< preSplit.length; i++){
            
            if(i < preSplit.length-1)
               hyphenated_str += preSplit[i] + " ";
            else
               hyphenated_str += preSplit[i]; 
        }
        
        
        String[] tokens = hyphenated_str.split(" ");
        
        for(int i=0; i< tokens.length; i++){
 
            
           
                
            //eleminate hyphen after first character
            if(tokens[i].length() >=3){
                if(tokens[i].charAt(1) == SOFT_HYPHEN){
                    tokens[i] = deleteCharAt(tokens[i],1);
                }
            }
            //eleminate hyphen before last character
            if(tokens[i].length() >=3){
            
                
                //when token ends with a dot, we examine the 3rd last position,
                //if there is a hyphen
                if(tokens[i].charAt(tokens[i].length()-2) == SOFT_HYPHEN ||
                        (tokens[i].charAt(tokens[i].length()-3) == SOFT_HYPHEN && 
                            tokens[i].charAt(tokens[i].length()-1)=='.' ) )
                {
                    
                 
                    if(tokens[i].charAt(tokens[i].length()-2) == SOFT_HYPHEN)
                        tokens[i] = deleteCharAt(tokens[i],tokens[i].length()-2);
                    else
                        tokens[i] = deleteCharAt(tokens[i],tokens[i].length()-3);
                    
                }
            }
           
        }//for
        
        String res = "";
        //concatenate tokens again
        for(int i=0; i<tokens.length; i++){
            

            
            if(i < tokens.length-1)
                res += (tokens[i] + " ");
            else
                res += tokens[i];
            
        }
        
       
        return res;
 
    }//correctHyphenation
    
    
    
    
    @Override
    public List<Text> execute(Text text) throws IOException  {
        
      
        
        
       
   
        ArrayList<Text> resultList = new ArrayList();
        EnsureConstraintsOperation ecOp = new EnsureConstraintsOperation();
        ArrayList<String> lines = text.getLines();
    
        
        //hyphenations when line length (up to the hyphen) is in the [lmin,lmax]-window!
         
        
        for(int i=0; i< lines.size(); i++){
            
        
            String line = lines.get(i);
            String[] lta; //line token array     
            //construct an array of tokens from line
            

            //String hyphenated_word=h.hyphenate(line);
            
            
            //split line whitespace separated tokens
            lta = line.split(" "); 


            //added up length of tokens (+ whitespace)    
            int sum = 0;
               

            int k;
             //find index for the first possible hyphen
            for(k=0; k< lta.length-1; k++){
                    
                    
                    sum += (lta[k].length()+1);
                    if(sum>=lmin){
                    
                        sum -= (lta[k].length()+1);
                        break;
                    }
                        
                    
                        
            }


                
                    
                    //the value before the sum gets greater then lmin=50
                   
                    
                  
                    String str1 = line.substring(0,sum);
                    String str2 = line.substring(sum);
                    
                    //hyphenate the 2nd part of the line
                    String hyphenated_str2 = correctHyphenation(str2);
                    
                    //tokenise hyphenated_str2 according to the SOFT_HYPHENs
                    String[] hyphenated_str2_tokenised;
                    hyphenated_str2_tokenised = hyphenated_str2.split("\u00AD");
                    
                
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                   
                    int sum_allowed = sum;
                    
                    //test out space on the line
                    for (int j=0; j < hyphenated_str2_tokenised.length -1; j++) {
                        
                        sum_allowed += hyphenated_str2_tokenised[j].length();
                        
                        String str2_new;
                        String str1_new;
                        
                        //one space for hyphen is needed
                        if( (sum_allowed < (lmax-1))  &&   (sum_allowed >= (lmin -1))  ){
                            
                            //construct str1_new
                            str1_new = line.substring(0,sum_allowed);
                             
                            //append hyphenation
                            str1_new += '-';
                            

                            //construct str2_new
                            if(line.charAt(sum_allowed) != '\u002D')
                                str2_new = line.substring(sum_allowed);
                            else
                                str2_new = line.substring(sum_allowed+1);
                            
                            
                            
                            //add the remaining lines (below line)
                            for(int l=i+1; l< lines.size(); l++){
                                
                                str2_new += (" " + lines.get(l));    
                                
                            }
                            
     
                            
                        
                    
                        
                    String str3=  str2_new;
                    
                    Text text2 = StringToText(str3);
                    ArrayList<String> lines3 = new ArrayList<>();

                    lines3.addAll(lines.subList(0, i));
                    lines3.add(str1_new);
                    lines3.addAll(text2.getLines());

                    Text textResult = new Text(lines3);
                    
                    
                    
                    //if(ecOp.ensureLineLengthConstraints(textResult) )
                        resultList.add(
                                ecOp.execute(textResult).get(0));
                    //else
                        //System.out.println("ENSURE CONSTRAINTS FAILED!");
                        
                        
                        
                        
                        
                        
                        
                        
                        
                    }//for j
                    
                                        
                    
                }//if lmin

                
            
            
            
        }//for i
                
        return resultList;
            
    }

        
        
        
        
        
        
      
                    
                    
          
            
            
            
            
            
            
            
    
        
 

  
    
    @Override
    public String toString(){
        return "HyphenationOperation";
        
    }
    
    
        
   

}
