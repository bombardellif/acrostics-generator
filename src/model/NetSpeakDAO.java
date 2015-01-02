package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import org.netspeak.application.ErrorCode;
import org.netspeak.application.generated.NetspeakMessages.Phrase;
import org.netspeak.application.generated.NetspeakMessages.Response;
import org.netspeak.client.CommonUtils;
import org.netspeak.client.Netspeak;
import org.netspeak.client.Request;

/**
 *
 * @author bombardelli.f
 */
public class NetSpeakDAO {

    private static final String TOPK = "10";
    private static final Integer MIN_FREQUENCY = 0;
    private static Map<String, List<String>> cache = new HashMap();
    
    private static final List<String> notEmptyList = Arrays.asList("");
    private static final List<String> emptyList = new ArrayList<>();

    public static List<Phrase> search(String query) throws IOException, Exception {
        if (query == null) {
            throw new IllegalArgumentException("NetSpeakDAO.search: Parameter query must not be null");
        }

        Netspeak netspeak = new Netspeak();

        Request request = new Request();
        request.put(Request.QUERY, query);
        request.put(Request.TOPK, TOPK);

        Response response = netspeak.search(request);

        ErrorCode errorCode = ErrorCode.cast(response.getErrorCode());
        if (errorCode != ErrorCode.NO_ERROR) {
            throw new Exception("NetSpeakAPI error. Code: " + errorCode + ". Message: " + response.getErrorMessage());
        }

        /*for (Phrase phrase : response.getPhraseList()) {
            System.out.printf("%d\t%d\t%s\n", phrase.getId(),
            phrase.getFrequency(), CommonUtils.toString(phrase));
        }
        System.out.println("------------");*/
        return response.getPhraseList();
    }

    public static List<String> searchNewWords(List<String> splittedNGram, Character operator, Integer position) throws Exception {
        if (splittedNGram == null) {
            throw new IllegalArgumentException("NetSpeakDAO.searchNewWords: Parameter splitedNGram must not be null");
        }
        if (operator == null) {
            throw new IllegalArgumentException("NetSpeakDAO.searchNewWords: Parameter operator must not be null");
        }
        int sizeNGram = splittedNGram.size();
        if (position == null || position < 0 || position > sizeNGram) {
            throw new IllegalArgumentException("NetSpeakDAO.searchNewWords: Parameter position is invalid");
        }

        // Build the query placing the operator in the especified position
        StringBuilder queryBuilder = new StringBuilder()
                .append(String.join(" ", splittedNGram.subList(0, position)))
                .append(' ').append(operator).append(' ')
                .append(String.join(" ", splittedNGram.subList(position, sizeNGram)));

        String query = queryBuilder.toString().trim();
        
        System.out.println(query);
        // Consult NetSpeak.org webservice on the web (check local cache)
        List<String> result;
        if (cache.containsKey(query)) {
            result = cache.get(query);
        } else {

            List<Phrase> phrasesFromNetspeak = NetSpeakDAO.search(query);

            result = phrasesFromNetspeak.parallelStream()
                // Filter the results removing those without enough frequency or that,
                // instead of words, has comma or dots
                .filter(phrase
                    -> (phrase.getFrequency() >= MIN_FREQUENCY
                    && phrase.getWordCount() > sizeNGram
                    && !CommonUtils.toString(phrase).contains(","))
                )
                // For each possible phrase, return only the new words given by Netspeak
                .map(phrase
                    -> phrase.getWordList() // Get the words
                    .subList(position, position + phrase.getWordCount() - sizeNGram) // Get only the new ones
                    .stream() // Initialize a stream
                    .reduce(new StringBuffer(), // Reduce the list into a StringBuffer
                            (sb, word) -> sb.append(word.getText()) // The reduce function is: concatenate the words
                            .append(' '),
                            StringBuffer::append) // The combine function concatenates the StringBuffers
                    .toString() // Get a string from the Buffer
                    .trim() // Trim to remove extra spaces in the end of it
                )
                .collect(toList());

            cache.put(query, result);
            /*for (Phrase phrase : result) {
                System.out.printf("%d\t%d\t%s\n", phrase.getId(),
                phrase.getFrequency(), CommonUtils.toString(phrase));
            }*/

            /*for (String s : result) {
                System.out.println(s);
            }*/
        }

        return result;
    }
    
    public static boolean isFrequent(final List<String> splittedNGram) throws Exception {
        if (splittedNGram == null) {
            throw new IllegalArgumentException("NetSpeakDAO.isFrequent: Parameter splitedNGram must not be null");
        }
        
        String query = String.join(" ", splittedNGram);
        
        if (cache.containsKey(query)) {
            return ! cache.get(query).isEmpty();
        } else {
            List<Phrase> phrasesFromNetspeak = NetSpeakDAO.search(query);
            
            if (!phrasesFromNetspeak.isEmpty() && phrasesFromNetspeak.get(0).getFrequency() >= MIN_FREQUENCY) {
                
                cache.put(query, notEmptyList);
                return true;
            } else {
                
                cache.put(query, emptyList);
                return false;
            }
        }
    }
}
