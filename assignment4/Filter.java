package assignment4;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.time.Instant;


/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweets> writtenBy(List<Tweets> tweets, String username) {
        List<Tweets> filteredList = new ArrayList<Tweets>();
        String find = username.toLowerCase();
        for(Tweets tweet: tweets){
            String author = tweet.getName().toLowerCase();
            if(find.equals(author)){
                filteredList.add(tweet);
            } 
        }
        return filteredList;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweets> inTimespan(List<Tweets> tweets, Timespan timespan) {
        List<Tweets> filteredList = new ArrayList<Tweets>();
        
        Instant start = timespan.getStart();
        Instant end = timespan.getEnd();
        for(Tweets t : tweets){
            Instant time = Instant.parse(t.getDate());
            if(time.compareTo(start) > 0){
                if(time.compareTo(end) < 0){
                    filteredList.add(t);
                }
            }
        }
        return filteredList;
    }
    // private static boolean isValidTimespan(Instant beg, Instant end) throws InvalidTimespan {
    //     if(beg.compareTo(end) > 0){//should be -1   
    //         throw new InvalidTimespan("End Date before Start Date");
    //     }
    //     return true;    
    // }
    /**
     * Find tweets that contain certain words.
     *
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets.
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when
     *         represented as a sequence of nonempty words bounded by space characters
     *         and the ends of the string) includes *at least one* of the words
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweets> containing(List<Tweets> tweets, List<String> words) {
        List<Tweets> filteredList = new ArrayList<Tweets>();
        Set<String> wordSet = new HashSet<>();
        for(String word : words){
            wordSet.add(word.toLowerCase());
        }
        for(Tweets tweet : tweets){
            Scanner in = new Scanner(tweet.getText());
            while(in.hasNext()){
                if(wordSet.contains(in.next().toLowerCase())){
                    filteredList.add(tweet);
                    break;
                }
            }
        }
        return filteredList;
    }
}