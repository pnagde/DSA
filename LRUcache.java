import java.net.Inet4Address;
import java.util.*;

class LRUCache {

    protected Cache<Integer, Integer> maps;
    protected int capacity = 0;

    public LRUCache(int capacity) {
        maps = new Cache<>(capacity);
        this.capacity = capacity;
    }

    public int get(int key) {
        return maps.get(key) != null ? maps.get(key) : -1;
    }

    public void put(int key, int value) {
        maps.put(key, value);
    }

    public Cache<Integer, Integer> getHashMap() {
        return this.maps;
    }
}

class Cache<K, V> {

    HashMap<K, V> hashMap = new HashMap<>();
    Deque<K> list = new ArrayDeque<>();
    private final int capacity;

    public Cache(int capacity) {
        this.capacity = capacity;
    }

    public void put(K key, V value) {
        if (hashMap.containsKey(key)) {
            list.remove(key);
            hashMap.put(key, value);
            list.addFirst(key);
        } else if (capacity == hashMap.size()) {
            K v = list.pollLast();
            hashMap.remove(v);

            hashMap.put(key, value);
            list.addFirst(key);
        } else {
            hashMap.put(key, value);
            list.addFirst(key);
        }
    }

    public V get(K key) {
        if(hashMap.containsKey(key)){
            list.remove(key);
            list.addFirst(key);
            return hashMap.get(key);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Cache{" +
                "hashMap=" + hashMap +
                ", list=" + list +
                ", capacity=" + capacity +
                '}';
    }
}