package assignment4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.lang.NullPointerException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.*;
/**
 * TweetReader contains method used to return tweets from method
 * Do not change the method header
 */
public class TweetReader {

    /**
     * Find tweets written by a particular user.
     *
     * @param url
     *            url used to query a GET Request from the server
     * @return return list of tweets from the server
     *
     */
    public static List<Tweets> readTweetsFromWeb(String url) throws Exception
    {
        List<Tweets> tweetList = new ArrayList<>();
        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //System.out.println(response.toString());
        Tweets[] tweetArray = toJSON(response.toString());
        for(int i = 0; i < tweetArray.length; i++){
            if(TweetReader.isValid(tweetArray[i])){
                tweetList.add(tweetArray[i]);
            }
        }
        //boolean a = TweetReader.isValid(tweetList.get(0));
        return tweetList;
    }

    //Id must be 1->2^32-1
    //name must be alphanumeric or _
    //Time must past Instant parameters
    //text must be non null and <= 140 chars
    private static boolean isValid(Tweets tweet){
        if(tweet.getId()<1){
            return false;
        }
        //test name
        try{
            for(char c: tweet.getName().toCharArray()){
                if(!Character.isLetter(c)){
                    if(!Character.isDigit(c)){
                        if(c!='_'){
                            return false;
                        }
                    }
                }
            }
        } catch(NullPointerException e) {
            System.out.println("fails name");
            return false;
        }
        //tests time
        try{
            Instant time = Instant.parse(tweet.getDate());
        } catch(DateTimeParseException e) {
            System.out.println("failed time");//DELETE this
            return false;
        } catch(NullPointerException e){
            System.out.println("failed time");//DELETE this
            return false;
        }
        //tests text
        try{
            if(tweet.getText().length() > 140){
                return false;
            }
        } catch(NullPointerException e) {
            System.out.println("failed text");
            return false;
        }
        System.out.println(tweet);
        return true;
    }

    private static Tweets[] toJSON(String users){
        try{
            ObjectMapper mapper = new ObjectMapper();
            return (mapper.readValue(users, Tweets[].class));
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        

    }
}
