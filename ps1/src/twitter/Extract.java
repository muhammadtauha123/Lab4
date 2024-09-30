package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
	public static Timespan getTimespan(List<Tweet> tweets) {
	    if (tweets == null || tweets.isEmpty()) {
	        return null;
	    }

	    Instant start = tweets.get(0).getTimestamp();
	    Instant end = start;

	    for (Tweet tweet : tweets) {
	        if (tweet == null) {
	            throw new IllegalArgumentException("Tweet must not be null");
	        }

	        Instant timestamp = tweet.getTimestamp();
	        if (timestamp.isBefore(start)) {
	            start = timestamp;
	        }
	        if (timestamp.isAfter(end)) {
	            end = timestamp;
	        }
	    }

	    return new Timespan(start, end);
	}


    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();

        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            for (String word : text.split("\\s+")) {
                if (word.startsWith("@") && word.length() > 1) {
                    String username = word.substring(1);
                    // Normalize to lower case
                    mentionedUsers.add(username.toLowerCase());
                }
            }
        }

        return mentionedUsers;
    }
}
