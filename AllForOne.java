import java.util.*;
import java.lang.*;
import java.io.*;

interface AllForOne {
  /**
   * Increment the provided key's frequency value by 1.  Or add the key with the frequency 1 if it's not present.
   *
   * Note that the actual frequency value of the key is not necessary to expose to the caller; its value is only kept internally for bookkeeping purposes.
   */
  void incrementKey(String key);
  
  /**
   * Decrement the provided key's frequency value by 1.  Or remove the key if its frequency is 1.  Do nothing if the key is not present.
   *
   * Note that the actual frequency value of the key is not necessary to expose to the caller; its value is only kept internally for bookkeeping purposes.
   */
  void decrementKey(String key);
  
  /**
   * Get one of the keys with the max frequency, or empty string if there are no keys.
   */
  String getMaxKey();
   
  /**
   * Get one of the keys with the min frequency, or empty string if there are no keys.
   */
  String getMinKey();
}

class Codechef implements AllForOne{
    private Hashtable<String, Integer> ht = new Hashtable<>();
    private Hashtable<String, Integer> locations = new Hashtable<>();
    private Hashtable<Integer, List<String>> freqMap = new Hashtable<>();
    int maxFreq = 0;
    int minFreq = 0;
    
    private String removeElem(List<String> list, int idx) {
        String lastElem = list.get(list.size() -1);
        list.set(idx, lastElem);
        list.remove(list.size() -1);
        return lastElem;
    }
    
    @Override
    public void incrementKey(String key) {
        if(ht.get(key) == null) {
            minFreq = 1;
            ht.put(key, 1);
            if(freqMap.get(1) == null) {
                freqMap.put(1, new ArrayList<>());
            }
            freqMap.get(1).add(key);
            locations.put(key, freqMap.get(1).size() -1);
            maxFreq = Math.max(maxFreq, 1);
        } else {
            int curFreq = ht.get(key);
            int newFreq = curFreq +1;
            if(newFreq > maxFreq) {
                maxFreq++;
            }
            
            if(minFreq == curFreq && freqMap.get(curFreq).size() == 1) {
                minFreq++;
            }
            
            String thatElem = removeElem(freqMap.get(curFreq), locations.get(key));
            locations.put(thatElem, locations.get(key));
            
            if(freqMap.get(newFreq) == null) freqMap.put(newFreq, new ArrayList<>());
            freqMap.get(newFreq).add(key);
            locations.put(key, freqMap.get(newFreq).size() -1);
            if(freqMap.get(curFreq).size() == 0) freqMap.remove(key);
            ht.put(key, ht.getOrDefault(key, 0) +1);
        }
        
    }
    
    @Override
    public void decrementKey(String key) {
        if(ht.get(key) == null) {
            return;
        }
        int curFreq = ht.get(key);
        int newFreq = curFreq -1;
        if(newFreq == 0) {
            ht.remove(key);
            int location = locations.get(key);
            String thatElem = removeElem(freqMap.get(curFreq), location);
            locations.put(thatElem, locations.get(key));
            locations.remove(key);
            if(freqMap.get(curFreq).size() == 0) {
                freqMap.remove(key);
                if(maxFreq == curFreq) maxFreq = 0;
                if(minFreq == curFreq) minFreq = 0;
            }
        } else {
            if(minFreq == curFreq) minFreq--;
            if(maxFreq == curFreq && freqMap.get(curFreq).size() == 1) maxFreq--;
            
            String thatElem = removeElem(freqMap.get(curFreq), locations.get(key));
            locations.put(thatElem, locations.get(key));
            
            if(!freqMap.containsKey(newFreq)) freqMap.put(newFreq, new ArrayList<>());
            freqMap.get(newFreq).add(key);
            
            locations.put(key, freqMap.get(newFreq).size() -1);
            
            if(freqMap.get(curFreq).size() == 0) freqMap.remove(curFreq);
            ht.put(key, newFreq);
        }
    }
    
    @Override
    public String getMaxKey() {
        return freqMap.get(maxFreq).get(0);
    }
    
    @Override
    public String getMinKey() {
        return freqMap.get(minFreq).get(0);
    }
    
    
	public static void main (String[] args) throws java.lang.Exception
	{
		Codechef cc = new Codechef();
		cc.incrementKey("foo"); // 1 0
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
		cc.incrementKey("foo"); // 2 0
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
		cc.incrementKey("foo"); // 3 0
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
		cc.incrementKey("foo"); // 4 0
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
		System.out.println();
		cc.incrementKey("bar"); // 4 1 
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
		cc.incrementKey("bar"); // 4 2
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
		cc.incrementKey("bar"); // 4 3
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
		cc.incrementKey("foo"); // 5 3
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
		cc.incrementKey("bar"); // 5 4
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
		cc.incrementKey("bar"); // 5 5
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
        cc.incrementKey("bar"); // 5 6
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
        cc.decrementKey("foo"); // 4 6
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
        cc.decrementKey("bar"); // 4 5
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
        cc.decrementKey("bar"); // 4 4
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
        cc.decrementKey("bar"); // 4 3
        System.out.println(cc.getMaxKey());
        System.out.println(cc.getMinKey());
        System.out.println();
	}
}
