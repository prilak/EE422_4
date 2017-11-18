package assignment4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;
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
                    // adjust to name being an ArrayList

                    List<String> names = toName(word);
                    for(String name: names){
                        if(followerCount.containsKey(name)){
                           Integer num = followerCount.get(name);
                            followerCount.put(name, new Integer(num.intValue() + 1));
                        } else {
                            followerCount.put(name, new Integer(1));
                        }
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
                    //System.out.println(max + " " + maxName);
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
    // if(i > 0){
    //     if(isValidChar(word.charAt(i-1))){
    //         return false;
    //     }
    // }
    // if(i + 1 < word.length()){
    //     if(!isValidChar(word.charAt(i+1))){
    //         return false;
    //     }
    // }
    // // is a name
    // return true;

    // ???
    for(int j = 0; j < word.length() - 1; j++){
        if(word.charAt(j) == '@'){
            if(i > 0){
                if(!isValidChar(word.charAt(i - 1))){                    
                    if(isValidChar(word.charAt(i + 1))){
                        return true;
                    }                    
                }
            } else {
                if(i + 1 < word.length()){
                    if(isValidChar(word.charAt(i + 1))){
                        return true;
                    }
                }
                
            }
            
        }
    }
    return false;
}
private static List<String> toName(String word){
    // int i = word.indexOf('@');
    // int j;
    // for(j = i + 1; j < word.length(); j++){
    //     if(!isValidChar(word.charAt(j))){
    //         break;
    //     }
    // }
    // return word.substring(i + 1, j);
    List<String> names = new ArrayList<>();
    for (int i = 0; i < word.length(); i++) {
        if(word.charAt(i) == '@'){
            if(i > 0){
                if(!isValidChar(word.charAt(i - 1))){
                    if(i + 1 < word.length()){
                        if(isValidChar(word.charAt(i + 1))){
                            i = extractName(names, i, word);
                        } 
                    }
                }
            } else {
                if(i + 1 < word.length()){
                    if(isValidChar(word.charAt(i + 1))){
                        i = extractName(names, i, word);
                    }
                }
            }
        }
    }
    return names;
}

private static int extractName(List<String> names, int i, String word){
    int beg;
    int end;
    i++;
    beg = i;
    while(i < word.length()){
        if(!isValidChar(word.charAt(i))){
            break;
        }
        i++;
    }
    end = i;
    names.add(word.substring(beg, end));
    return i;
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
    public static List<Set<String>> findCliques(List<Tweets> tweets) {
        List<Set<String>> result = new ArrayList<Set<String>>();
        HashMap<String, HashSet<String>> m = baseLayer(tweets);
        Set<String> k = m.keySet();
        for(String name: k){
            Set<String> soloName = new HashSet<>();
            soloName.add(name);
            result.add(soloName);
            name = name.toLowerCase();
            List<String> l = new ArrayList<>(m.get(name));// m[name].values
            int iterations = powTwo(l.size());
            for(int i = 1; i < iterations; i++){
                ArrayList<Integer> binaryPositions = binaryToPosition(Integer.toBinaryString(i));
                //System.out.println(binary);
                ArrayList<String> nameList = positionToNames(binaryPositions, l, name);
                //System.out.println(nameList);
                // will now need to parse through each name in nameList to verify that the all other names are mentioned
                // if not, do not add
                // if so, add the set
                int j;
                for(j = 0; j < nameList.size(); j++){
                    String key = nameList.get(j);
                    Set<String> copy = copySet(nameList, j);
                    // check if source contains all Strings in copy
                    if(!checkList(m, copy, key)){
                        break;
                    }

                }
                Set<String> namesMatch;
                if(j==nameList.size()){ // all Strings were found
                    namesMatch = new HashSet<String>(nameList);
                    //System.out.println(namesMatch);
                    result.add(namesMatch);
                } else {
                    namesMatch = null;
                }

            }
        }
        result = filterSet(result);
        return result;
    }
    private static HashMap<String, HashSet<String>> baseLayer(List<Tweets> tweets){
        HashMap<String, HashSet<String>> s = new HashMap<>();
        for(Tweets tweet: tweets){
            String name = tweet.getName().toLowerCase();
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
                    // adjust to ArrayList

                    List<String> mentions = toName(word);

                    for(String mention: mentions){
                        if(!mention.equals(name)){
                           h.add(mention);
                        }
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
    // creates a list copy of l
    // with the ith position removed
    private static Set<String> copySet(List<String> l, int i){
        Set<String> newSet = new HashSet<>();
        for(int j = 0; j < l.size(); j++){
            if(j != i){
                newSet.add(l.get(j));
            }
        }
        //System.out.println("new set is " + newSet);
        //System.out.println("orginally: " + l);  
        return newSet; 
    }

    // deletes sets that are subsets of other sets
    private static List<Set<String>> filterSet(List<Set<String>> unfilterList){
        for(int i = 0; i < unfilterList.size(); i++){
            if(unfilterList.get(i)==null){
                continue;
            }
            for(int j = 0; j < unfilterList.size(); j++){
                if(unfilterList.get(j)==null){
                    continue;
                }
                if(i!=j){
                    Iterator it = unfilterList.get(i).iterator();
                    int count = 0;
                    while(it.hasNext()){
                        String name = (String) it.next();

                        if(!unfilterList.get(j).contains(name)){
                            break;
                        }
                        count++;
                    }
                    if(count==unfilterList.get(i).size()){// everything in i was in j, so i is a subset of j
                        unfilterList.set(i, null);
                        break;
                    }
                }
            }
        }
        List<Set<String>> filterList = new ArrayList<Set<String>>();
        for(Set<String> nameSet: unfilterList){
            if(nameSet!=null){
                filterList.add(nameSet);
            }
        }
        return filterList;
    }
    private static boolean checkList(HashMap<String, HashSet<String>> m, Set<String> check, String key){
        // System.out.println(check + " checking for " + key);
        // System.out.println("for key: " + key + " set is " + m.get(key));

        for(String name: check){
            try {
                if(!m.get(key).contains(name)){
                    return false;
                }
            } catch(NullPointerException e) {
                return false;
            }
            
        }
        return true;
    }

    // 2^n
    private static int powTwo(int n){
        int val = 1;
        for(int i = 0; i < n; i++){
            val *= 2;
        }
        return val;
    }

    // converts "1101"
    // to {0, 2, 3}
    private static ArrayList<Integer> binaryToPosition(String binary){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i = 0; i < binary.length(); i++){
            if(binary.charAt(binary.length() - 1 - i)=='1'){
                list.add(new Integer(i));
            }
        }
        return list;
    }

    //convert {0, 1, 3}
    // to {"todd", "mary", "jon"}
    private static ArrayList<String> positionToNames(ArrayList<Integer> positions, List<String> mentioned, String head){
        ArrayList<String> nameSubset = new ArrayList<>();
        nameSubset.add(head);
        for(Integer val : positions){
            nameSubset.add(mentioned.get(val.intValue()));
        }
        return nameSubset;
    }
}


