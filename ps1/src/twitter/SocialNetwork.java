package twitter;

import java.util.*;

public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets a list of tweets providing the evidence, not modified by this method.
     * @return a social network (as defined above) where Ernie follows Bert if
     *         there is evidence for it in the given list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        
        for (Tweet tweet : tweets) {
            String author = tweet.getAuthor();
            String tweetText = tweet.getText();
            Set<String> mentions = extractMentions(tweetText);
            
            // Ensure the author is in the graph
            followsGraph.putIfAbsent(author.toLowerCase(), new HashSet<>());
            
            for (String mention : mentions) {
                followsGraph.putIfAbsent(mention.toLowerCase(), new HashSet<>());
                followsGraph.get(author.toLowerCase()).add(mention.toLowerCase());
            }
        }
        
        return followsGraph;
    }

    private static Set<String> extractMentions(String text) {
        Set<String> mentions = new HashSet<>();
        String[] words = text.split("\\s+");
        
        for (String word : words) {
            if (word.startsWith("@") && word.length() > 1) {
                // Extract the username, ignoring the '@'
                String username = word.substring(1);
                // Ensure it's a valid username (no invalid characters)
                if (isValidUsername(username)) {
                    mentions.add(username.toLowerCase());
                }
            }
        }
        return mentions;
    }

    private static boolean isValidUsername(String username) {
        // Username should not contain spaces or special characters (except underscore)
        return username.matches("[A-Za-z0-9_]+");
    }

    /**
     * Find the people in a social network who have the greatest influence, 
     * in the sense that they have the most followers.
     * 
     * @param followsGraph a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, 
     *         in descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Integer> followerCount = new HashMap<>();
        
        // Count followers for each user
        for (String user : followsGraph.keySet()) {
            Set<String> follows = followsGraph.get(user);
            for (String followed : follows) {
                followerCount.put(followed, followerCount.getOrDefault(followed, 0) + 1);
            }
        }
        
        // Create a list of users sorted by follower count
        List<String> influencers = new ArrayList<>(followerCount.keySet());
        influencers.sort((u1, u2) -> followerCount.get(u2).compareTo(followerCount.get(u1))); // Descending order
        
        return influencers;
    }
}
