import java.util.PriorityQueue;

public class Runner {
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);

        System.out.println("get : " + lruCache.get(3));
        lruCache.put(1 , 1);
        System.out.println("print: " + lruCache.getHashMap());
        lruCache.put(2 , 2);
        System.out.println("print: " + lruCache.getHashMap());
        lruCache.put(4, 4);
        System.out.println("print: " + lruCache.getHashMap());
        System.out.println("get : " + lruCache.get(1));
        System.out.println("print: " + lruCache.getHashMap());
    }
}
