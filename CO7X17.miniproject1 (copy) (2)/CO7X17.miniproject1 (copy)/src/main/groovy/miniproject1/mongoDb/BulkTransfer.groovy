package miniproject1.mongoDb

import groovy.json.JsonSlurper

class BulkTransfer {
	static void main(String... args) {

		MongoDB mongo = new MongoDB()
		JsonSlurper slurper = new JsonSlurper()
		def friends = slurper.parseText(new File('./src/main/resources/twitter/friends.json').text)
		def followers = slurper.parseText(new File('./src/main/resources/twitter/followers.json').text)
		def tweets = slurper.parseText(new File('./src/main/resources/twitter/tweets.json').text)
		
		
		mongo.initDb()
		// tag::exercise[1] 
		//global scope variable - Map calling 
		//for loop
		//null values allowed
		//tweet_list call
		//past twts get added to tweetslist
		def Map = [:]
		for (tweet in tweets) {
			if (!tweet.user.equals(null)) {
			def past_twt = [:]
			past_twt.putAt("id_str", tweet.id_str)
			past_twt.putAt("created_at", tweet.created_at)
			past_twt.putAt("entities", tweet.entities)
			past_twt.putAt("favorite_count", tweet.favorite_count)
			past_twt.putAt("favorited", tweet.favorited)
			past_twt.putAt("user_id_str", tweet.user.id_str)
			past_twt.putAt("in_reply_to_screen_name", tweet.in_reply_to_screen_name)
			past_twt.putAt("in_reply_to_status_id", tweet.in_reply_to_status_id)
			past_twt.putAt("in_reply_to_user_id", tweet.in_reply_to_user_id)
			past_twt.putAt("language_code", tweet.language_code)
			past_twt.putAt("retweet_count", tweet.retweet_count)
			past_twt.putAt("retweeted", tweet.retweeted)
			past_twt.putAt("source", tweet.source)
			past_twt.putAt("text", tweet.text)
			past_twt.putAt("lang", tweet.lang)
			//boolean check to see if there is a value
			//create list - get id - add
			if (Map.containsKey(tweet.user.id_str)) {
				def tweets_list = Map.get(tweet.user.id_str)
				tweets_list.add(past_twt)
				} 
				else 
				{
					def tweets_list = []
					tweets_list.add(past_twt)
					Map.put(tweet.user.id_str, tweets_list)
				}
			}
		}		
		
		def follower_lst = []
		for (follower in followers.users) {
				def crrt_follower = [:]
				crrt_follower.putAt("id_str", follower.id_str)
				crrt_follower.putAt("name", follower.name)
				crrt_follower.putAt("description", follower.description)
				crrt_follower.putAt("favorites_count", follower.favorites_count)
				crrt_follower.putAt("followers_count", follower.followers_count)
				crrt_follower.putAt("friends_count", follower.friends_count)
				crrt_follower.putAt("location", follower.location)
				crrt_follower.putAt("screen_name", follower.screen_name)
				crrt_follower.putAt("url", follower.url)
				crrt_follower.putAt("created_at", follower.created_at)
				//tweetlist -> map
				def tweet_list = Map.get(follower.id_str)
				crrt_follower.put("tweet_list", tweet_list)
				follower_lst.add(crrt_follower)
			}
			//test connection
			if (mongo != null) {
			mongo.db.followers << follower_lst
				}
				//if connection problem show msg
					else
						{
							println "problem with connection to db"
						}
						
		def friend_lst = []
		for (friend in friends.users) {
				def crrt_friend = [:]
				crrt_friend.putAt("id_str", friend.id_str)
				crrt_friend.putAt("name", friend.name)
				crrt_friend.putAt("description", friend.description)
				crrt_friend.putAt("favorites_count", friend.favorites_count)
				crrt_friend.putAt("followers_count", friend.followers_count)
				crrt_friend.putAt("friends_count", friend.friends_count)
				crrt_friend.putAt("location", friend.location)
				crrt_friend.putAt("screen_name", friend.screen_name)
				crrt_friend.putAt("url", friend.url)
				crrt_friend.putAt("created_at", friend.created_at)
				//tweet_list -> map
				def tweet_list = Map.get(friend.id_str)
				crrt_friend.put("tweet_list", tweet_list)
				friend_lst.add(crrt_friend)
					}
					//test connection
					if (mongo != null) {
						mongo.db.friends << friend_lst
						}
						//if connection problem show msg
							else
								{
								println "problem with connection to db"
								}
							
		//end exercise
	}
}

