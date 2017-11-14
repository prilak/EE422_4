package assignment4;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Scanner;
/**
 * Social Network consists of methods that filter users matching a
 * condition.
 *
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Get K most followed Users.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @param k
     *            integer of most popular followers to return
     * @return the set of usernames who are most mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getName()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like ethomaz@utexas.edu does NOT
     *         contain a mention of the username.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static List<String> findKMostFollower(List<Tweets> tweets, int k) {
        List<String> mostFollowers = new ArrayList<>();
        HashMap<String, Integer> followerCount = new HashMap<String, Integer>();
        for(Tweets tweet: tweets){
            Scanner in = new Scanner(tweet.getText().toLowerCase());
            while(in.hasNext()){
                String word = in.next();
                if(isName(word)){
                    String name = toName(word);
                    if(followerCount.containsKey(name)){
                        Integer num = followerCount.get(name);
                        followerCount.put(name, new Integer(num.intValue() + 1));
                    } else {
                        followerCount.put(name, new Integer(1));
                    }
                }
            }
            
        }
        Set<String> keys = followerCount.keySet();
        for(int i = 0; i < k; i++){
            if(keys.isEmpty()){
                break;
            }
            int max = 0;                
            String maxName = "";
            for(String key: keys){
                int val = followerCount.get(key).intValue();
                if(val > max){
                    max = val;
                    maxName = key;
                    System.out.println(max + " " + maxName);
                }
            }
            mostFollowers.add(maxName);
            keys.remove(maxName);
        }
        return mostFollowers;
    }
private static boolean isName(String word){
    int i = word.indexOf('@');
    if(i == -1){
        return false;
    }
    if(i > 0){
        if(isValidChar(word.charAt(i-1))){
            return false;
        }
    }
    if(i + 1 < word.length()){
        if(!isValidChar(word.charAt(i+1))){
            return false;
        }
    }
    // is a name
    return true;
    
}
private static String toName(String word){
    int i = word.indexOf('@');
    int j;
    for(j = i + 1; j < word.length(); j++){
        if(!isValidChar(word.charAt(j))){
            break;
        }
    }
    return word.substring(i + 1, j);
}
private static boolean isValidChar(char c){
    if(!Character.isLetter(c)){
        if(!Character.isDigit(c)){
            if(c!='_'){
                return false;
            }
        }
    }
    return true;
}

    /**
     * Find all cliques in the social network.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     *
     * @return list of set of all cliques in the graph
     */
    List<Set<String>> findCliques(List<Tweets> tweets) {
        List<Set<String>> result = new ArrayList<Set<String>>();
        HashMap<String, HashSet<String>> m = baseLayer(tweets);
        Set<String> k = m.keySet();
        for(String name: k){
            List<String> l = new ArrayList<>(s.get(name));
            int iterations = 
            for(int i; i < l.size(); i++)
        }
        return result;
    }
    private static HashMap<String, HashSet<String>> baseLayer(List<Tweets> tweets){
        HashMap<String, HashSet<String>> s = new HashMap<>();
        for(Tweets tweet: tweets){
            String name = tweet.getName();
            HashSet<String> h;
            if(s.containsKey(name)){
                h = s.get(name);
            } else {
                h = new HashSet<String>();
            }
            Scanner in = new Scanner(tweet.getText().toLowerCase());                
            while(in.hasNext()){
                String word = in.next();
                if(isName(word)){
                    String mention = toName(word);
                    if(!mention.equals(name)){
                        h.add(mention);
                    }
                        
                }
            }                //h.add(name);
            s.put(name, h);
            // } else {
            //     s.put(name, new HashSet(n))
            // }
        }
        return s;
    }
    private static powTwo(int n){
        int val = 1;
        for(int i = 0; i < n; i++){
            val *= 2;
        }
        return val;
    }
}


