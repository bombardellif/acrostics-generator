/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.NetSpeakDAO;

/**
 *
 * @author william
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            /*try {
            NetSpeakDAO.search("hello ? world");
            } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            NetSpeakDAO.searchNewWords("hello world", '*', 1);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
}
