package controller;

import java.util.ArrayList;
import java.util.List;

public class EnsureConstraintsOperation extends Operation {

    private static final Double COST = 0.0;
    
    public EnsureConstraintsOperation() {
        super(COST);
    }

    @Override
    public List<Text> execute(Text text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
