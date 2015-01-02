/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Set;
import redis.clients.jedis.Jedis;

/**
 *
 * @author bombardelli.f
 */
public class SynonymDAO {
    
    private static Jedis jedis = null;
    
    public static Set<String> getSynonyms(String word) {
        if (word == null) {
            throw new IllegalArgumentException("SynonymDAO.getSynonyms: Parameter word must not be null");
        }
        
        // Lazy connection to server
        if (jedis == null) {
            jedis = new Jedis("localhost");
        }
        
        return jedis.smembers(word);
    }
}
