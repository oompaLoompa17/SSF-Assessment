package vttp.batch5.ssf.noticeboard.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;
import vttp.batch5.ssf.noticeboard.utils.Constants;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository nRepo;

	@Value ("${rest.api.url}")
	private String restAPI;

	// TODO: Task 3
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	public String postToNoticeServer(Notice notice) {

		RequestEntity<String> req = RequestEntity
			.post(restAPI + "/notice")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.body(noticeToJson(notice), String.class);
			
		try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> resp = template.exchange(req, String.class);
            String payload = resp.getBody();
			
			// if payload contains key "id", save to redis and display view 2
			// if payload contains key "message", display view 3
			if (checkResult(payload) == true){
				saveToRedis(payload);
				System.out.printf("**** ID: %s\n", getID(payload));
				return getID(payload);
			}else{	
				System.out.printf("**** ERROR: %s\n", getError(payload));
				return getError(payload);
			}
        } catch(Exception ex){
            ex.printStackTrace();
        }		
		return "help";
	}

	// Notice -> jsonObject
	public String noticeToJson(Notice notice){
		List<String> categories = notice.getCategories();
		JsonArrayBuilder jArrBuild = Json.createArrayBuilder();
			for (String s : categories){
				jArrBuild.add(s);
			}
		JsonArray jArr = jArrBuild.build();

		JsonObject jObj = Json.createObjectBuilder()
			.add("title", notice.getTitle())
			.add("poster", notice.getPoster())
			.add("postDate", Constants.dateTolong(notice.getPostDate()))
			.add("categories", jArr)
			.add("text", notice.getText())
			.build();
		return jObj.toString();
	}

	// check status of response
	public boolean checkResult(String response){
      	JsonObject result = Constants.toJson(response);
		if (result.containsKey("message")){
			return false;
		}
		return true;	
	}
	// write json payload into Redis
	public void saveToRedis(String response){
		String id = Constants.toJson(response).getString("id");
		nRepo.insertNotices("IDMap", id, response); 		
	}
	// get id of positive response
	public String getID (String response){
	  	return "id:" + Constants.toJson(response).getString("id");
	}
	// get message of negative response
	public String getError (String response){
	  	return "message:" + Constants.toJson(response).getString("message");
	}
}
