package edu.sjsu.cmpe.cache.client;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

public class Client {	
	public static void main(String[] args) {
        String server = null;
        CacheServiceInterface cache = null;
        String[] values =  {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        List<String> nodes = getNodes();
    	      
        @SuppressWarnings({"unchecked","rawtypes"})
        ConsistentHash<String> consistentHash = new ConsistentHash(
        		Hashing.md5(), nodes);
        System.out.println("Starting Cache Client...");             
        
        for (int i=0; i<values.length; i++) {
			int key = i + 1;
			server = consistentHash.get("" + key);					
			cache = new DistributedCacheService(server);
	        cache.put(key, values[i]);
	        
		}
		
        for (int j=0; j<values.length; j++) {
			int key = j + 1;
			server = consistentHash.get("" + key);					
			cache = new DistributedCacheService(server);
	        System.out.println("get(" + key + ")" + cache.get(key) 
	        		+ " at Server Instance " + getServerName(server));
		}  
		System.out.println("Exiting Cache Client...");             		
	}
	
	private static List<String> getNodes() {
		List<String> nodes = Lists.newArrayList();
		for(int i = 0 ; i < 3; i ++) {
			nodes.add("http://localhost:300"+i);
		}
		return nodes;
	}
	
	private static String getServerName(String server) {
		if (server.contains("3000")) {
			return "0";
		} else if (server.contains("3001")) {
			return "1";
		} else if (server.contains("3002")) {
			return "2";
		}
		return null;
	}
}