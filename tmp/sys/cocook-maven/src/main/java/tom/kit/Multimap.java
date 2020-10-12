package tom.kit;

import java.util.HashMap;
import java.util.Map;

public class Multimap<K, V> extends HashMap<K, V>{

	private static final long serialVersionUID = 1L;
	
	public static <K ,V> Multimap<K, V> createMap(K k, V v){
		return new Multimap<K, V>(k, v);
	}
	
	public static <K ,V> Multimap<K, V> createMap(Map<K, V> map){
		return new Multimap<K, V>(map);
	}
	
	public Multimap() {
		super();
	}
	
	public Multimap(Map<K, V> map) {
		super(map);
	}
	
	public Multimap(K k, V v) {
		super();
		put(k, v);
	}
	
	public Multimap<K, V> set(K k, V v){
		super.put(k, v);
		return this;
	}	
	
	public static void main(String[] args) {
		System.out.println(Multimap.createMap("hello", "123").set("nihk", "panmg"));;
	}

}
