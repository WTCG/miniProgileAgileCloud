package miniproject1.reports;

import com.mongodb.DBCollection
import java.util.List
import miniproject1.mongoDb.MongoDB
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

	def mongo = new MongoDB()
	
	@RequestMapping
	public String index(Model model) {
		def connectionStatus = 'PENDING'
		if (mongo.connectionOk()) {
			connectionStatus = 'CONNECTED'
		}
		
		model.addAttribute('connectionStatus', connectionStatus)
		return "main"
		
	}
	

	@RequestMapping(value="followers", method=RequestMethod.GET)
    public String followers(Model model) {
		def followedFollowers = []
		
		// tag::exercise[2]
//		def slurper = new groovy.json.JsonSlurper()
//		def friends = slurper.parseText(new File("./src/main/resources/twitter/friends.json").text)
//		def followers = slurper.parseText(new File("./src/main/resources/twitter/followers.json").text)
//		followers.users.forEach{ follower ->
//			def friend = friends.users.find{ friend ->
//				friend.id==follower.id
//			}
//			if (friend != null) {
//				followedFollowers << follower
//			}
//		}
		//end::exercise[2]
		
		model.addAttribute("followers", followedFollowers);
	    	return "followers";
    }

	
	@RequestMapping(value="friends",method=RequestMethod.GET)
	public String friends(Model model) {
		List<FriendDto> friends = new ArrayList<FriendDto>()
		
		// tag::exercise[3]

		
		//end::exercise[3]
		
		model.addAttribute("friends", friends);
		return "friends";
	}
	
}
