package vttp.batch5.ssf.noticeboard.repositories;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;

@Repository
public class NoticeRepository {


	@Autowired @Qualifier("notice")
	RedisTemplate<String,Object> redisTemplate;
	// where key = key, hashKey = id, hashValue = JsonString

	// TODO: Task 4
	// You can change the signature of this method by adding any number of parameters
	// and return any type
	// 
	/*
	 * Write the redis-cli command that you use in this method in the comment. 
	 * For example if this method deletes a field from a hash, then write the following
	 * redis-cli command 
	 * 	hdel myhashmap a_key
	 *
	 *
	 */	
	// (hset "key" "hashKey" "hashValue")
	public void insertNotices(String hash, String hashKey, Object hashValue) {
		redisTemplate.opsForHash().put(hash, hashKey, hashValue);
	}

    // day 15 - slide 37 (hget "key" "hashKey")
    public String getValue(String key, String hashKey){
        Object objValue = redisTemplate.opsForHash().get(key, hashKey);
        return objValue.toString();
    }
    // day 15 - slide 38 (hdel "key" "hashKey")
    public void deleteHashKey(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }
    // day 15 - slide 39 (hexists "key" "hashKey")
    public Boolean hashKeyExist (String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }
    // day 15 - slide 40 (hvals "key")
    public List<Object> getAllValues(String key){
        List<Object> values = redisTemplate.opsForHash().values(key);
        return values;
    }
    // getAllValues but return as List<String>
    public List<String> getAllValues2Strings(String key) {
    return redisTemplate.opsForHash().values(key).stream()
            .map(Object::toString) // Ensure the objects are converted to String
            .collect(Collectors.toList());
    }
    // gets all hashkeys and store in set
    public Set<String> getAllKeys(){
        return redisTemplate.keys("*");
    }
    // day 16 - slide 41 (hlen "key")
    public Long mapSize(String key){
        return redisTemplate.opsForHash().size(key);
    }

}
