package model;

import java.util.HashMap;
import java.util.Map;

public class LetterFrequency {

    private static final char letters[] = {'e','n','s','r','i','a','t','d','h','u','l','g','c','o','m','w','b','f','k','z','ü','v','p','ö','ä','ß','j','y','x','q'};
    private static final double[] inverseFrequence = {6.0990485484,10.2291325696,13.7494843943,14.2795944595,15.2671755725,15.3468385513,16.2495937602,19.7005516154,21.8483722963,24.0038406145,29.0951411114,33.233632436,36.6032210835,38.5505011565,39.4632991318,52.0562207184,53.0222693531,60.38647343,70.5716302047,88.1834215168,100.5025125628,118.2033096927,149.2537313433,174.520069808,223.7136465324,325.7328990228,373.1343283582,2564.1025641026,2941.1764705882,5555.5555555556};

    private static Map<Character, Double> frequencyMap = null;
    
    public static double getFrequencyInversed(char c) {
        // Lazy initialization of map
        if (frequencyMap == null) {
            frequencyMap = new HashMap<>();
            for (int i=0; i<letters.length; i++)
                frequencyMap.put(letters[i], inverseFrequence[i]);
        }
        
        return frequencyMap.get(c);
    }
}
