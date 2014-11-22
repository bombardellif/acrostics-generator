package model;

import java.util.HashMap;
import java.util.Map;

public class FirstLetterFrequency {
    
//first letter frequencies as reference    
/*    
d B 1 14.2230834 1 0
s B 1 9.0591486 2 1
a B 1 7.4010755 3 1
e B 1 6.5050288 4 1
u B 1 5.5523877 5 1
w B 1 5.1836791 6 1
i B 1 5.1066940 7 1
b B 1 4.9622993 8 2
m B 1 4.6157948 9 2
g B 1 4.3318578 10 2
v B 1 4.2298511 11 2
f B 1 3.4081180 12 2
k B 1 3.3830796 13 2
z B 1 3.2983991 14 2
h B 1 3.2035460 15 2
n B 1 3.1750416 16 2
p B 1 2.1747798 17 3
l B 1 2.0749004 18 3
t B 1 1.8975942 19 3
r B 1 1.8942404 20 3
j B 1 1.3590882 21 3
o B 1 1.0063024 22 4
c B 1 0.6404879 23 4
ü B 1 0.5541393 24 5
q B 1 0.3882631 25 5
ö B 1 0.2068955 26 6
ä B 1 0.1048784 27 7
y B 1 0.0387478 28 9
x B 1 0.0201545 29 9
ß B 1 0.0004435 30 15
*/



//below we divide the values by 100
private final static double firstLetterFrequencies[]={14.2230834,9.0591486,7.4010755,6.5050288,5.5523877
        ,5.1836791,5.1066940,4.9622993,4.6157948,4.3318578,4.2298511
        ,3.4081180,3.3830796,3.2983991,3.2035460,3.1750416 ,2.1747798
        ,2.0749004,1.8975942,1.8942404,1.3590882,1.0063024,0.6404879
        ,0.5541393,0.3882631,0.2068955,0.1048784,0.0387478,0.0201545,0.0004435}; 
    
  
private final static char firstletters[]= {'d','s','a','e','u','w','i','b','m','g','v',
    'f','k','z','h','n','p','l','t','r','j','o','c','ü','q','ö','ä','y','x','ß'}; 










   
    private static Map<Character, Double> frequencyMap = null;
    
    public static double getFrequencyInversed(char c) {
        // Lazy initialization of map
        if (frequencyMap == null) {
            frequencyMap = new HashMap<>();
            for (int i=0; i<firstletters.length; i++)
                frequencyMap.put(firstletters[i], ((double)1/(firstLetterFrequencies[i]/100.0)));
        }
        
        return frequencyMap.get(Character.toLowerCase(c));
    }
}
