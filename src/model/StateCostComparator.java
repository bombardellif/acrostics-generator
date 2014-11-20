/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.State;
import java.util.Comparator;

/**
 *
 * @author standard
 */
class StateCostComparator implements Comparator<State> {

    public StateCostComparator() {
      
        
    }

    @Override
    public int compare(State o1, State o2) {
        
        double diff = (o1.estimatedTotalCost() - o2.estimatedTotalCost());
             
        //choose the state with smaller cost!
        if(diff < 0)
            return -1;
        else if(diff > 0)
            return 1;
        else
            return 0;

    }
    
    
    
    
    
    
}
