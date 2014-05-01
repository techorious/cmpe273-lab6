package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting Cache Client...");
		int bucket;

		List<CacheServiceInterface> list = new ArrayList<CacheServiceInterface>();
		list.add(new DistributedCacheService("http://localhost:3000"));
		list.add(new DistributedCacheService("http://localhost:3001"));
		list.add(new DistributedCacheService("http://localhost:3002"));

		char value = 'a';
		for(int hashkey=1;hashkey<=10;hashkey++)
		{
			bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(hashkey)), list.size());
			list.get(bucket).put(hashkey, Character.toString(value));

			System.out.println("(" + hashkey +" ==> "+ value + ")");
			value++;
		}
		for(int hashkey=1;hashkey<=10;hashkey++)
		{
			bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(hashkey)), list.size());
			System.out.println("(" + hashkey + "==>" + list.get(bucket).get(hashkey) +")");
		}

		System.out.println("Exiting Cache Client...");
	}

}