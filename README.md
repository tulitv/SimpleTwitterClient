# SimpleTwitterClient
CodePath - Week3 Assignment

This is a simple program to show download and display Twitter home timeline, mentions, user timeline in a list in tabulated format, also show tweets in details, post on timeline and reply on tweets.

The following items were accomplished for Week 3 assignment:
 * [X] Required: User can sign in Twitter using OAuth login
 * [X] Required: User can view the tweets from their home timeline
 * [X] Required:  - User should be displayed the username, name, and body for each tweet.
 * [X] Required:  - User should be displayed the relative timestamp for each tweet.
 * [X] Required:  - User can view more tweets as they scroll with infinite pagination.
 * [X] Optional: Links in tweets are clickable and will launch the web browser.
 * [x] Required: User can compose compose a tweet
 * [x] Required:  - User can click a “Compose” icon on the Action Bar on the top right.
 * [x] Required:  - User can then enter a new tweet and post this to twitter.
 * [x] Required:  - User is taken back to home timeline with new tweet visible in timeline.
 * [x] Optional: User can see a counter with total number of characters left for tweet.
 * [X] Advanced: User can refresh tweets timeline by pulling down to refresh
 * [X] Advanced: User can open the twitter app offline and see last loaded tweets.
 * [X] Advanced:  - Tweets are persisted into sqlite and can be displayed from the local DB.
 * [X] Advanced: User can tap a tweet to display a “detailed” view of that tweet.
 * [X] Advanced: User can select “reply” from detail view to respond to a tweet.
 * [X] Advanced: Tried to improve the user interface and theme the app to feel “twitter branded”
 * [X] Bonus:    User can see embedded image media within the tweet detail view.
 * [X] Bonus:    Compose activity is replaced with modal overlay.

The following items were accomplished additionally for Week 4 assignment:
 * [X] Required: User can switch between Timeline and Mention views using tabs.
 * [X] Required:  - User can view their home timeline tweets.
 * [X] Required:  - User can view the recent mentions of their username.
 * [X] Required: User can navigate to view their own profile.
 * [X] Required:  - User can see picture, tagline, # of followers, # of following, and tweets on their profile.
 * [X] Required: User can click on the profile image in any tweets to see another user’s profile.
 * [X] Required:  - User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
 * [X] Required:  - Profile view should include that user’s timeline
 * [X] Required: User can infinitely paginate any of these timelines (home, mentions, user) by scrolling to the bottom.
 * [X] Advanced: Robust error handling, check if internet is available, handle error cases, network failures. (Added internet check for each async http request, and for any other fragment/activity starts).
 * [X] Advanced: When a network request is sent, user sees an indeterminate progress indicator. I implemented for the HomeTimeline, but cannot really see anything, even if I switched the preferred network to Edge. Please advise on this item.
 * [X] Advanced: User can “reply” to any tweet on their home timeline. User can reply on their home timeline, mentions and user timeline, as long as the user first click on the tweet to go in detailed view.
 * [X] Advanced:  - The user that wrote the original tweet is automatically “@“ replied in compose.
 * [X] Advanced: User can click on a tweet to be taken to a “detail view” of that tweet. 
 
 Walkthrough of all user stories:

 ![Video Walkthrough](SimpleTwitter.gif)
 
 Gif created with [LiceCap](http://www.cockos.com/licecap/). 

