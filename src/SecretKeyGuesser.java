package src;

public class SecretKeyGuesser {
    // private static HashMap<String, Integer> potential = new HashMap<>();
    private static MyHashMap potential = new MyHashMap(20);
    private static boolean found = false;
    private static int[] nums = new int[4];
    private static String chosen = "";
    private static char mid;
    public SecretKeyGuesser() {}
    public void start() {
      // guess the word key guessing
      SecretKey key = new SecretKey();
      // set up
      // potential = new HashMap<>();
      potential = new MyHashMap(20);
      found = false;
      nums = new int[4];
      chosen = "";
      
      setup(key);
      String str = form();
      chosen = str;
      int count = 0;
      int result = 0;
      // main loop
      while (result != 16 && count <= 25) {
        // need to know if the key is guarantee to be 16 words
        System.out.println("Guessing... " + str);
        result = key.guess(str);
        potential.put(str, result);
        str = next(str);
        count++;
      }
      System.out.println("I found the secret key. It is " + str);
    }


    // find the next guess
    public String next(String current) {
      char[] chars = current.toCharArray();
      char[] temp = {'0', '0', '0', '0','0', '0', '0', '0','0', '0', '0', '0','0', '0', '0', '0'};
      boolean[] taken = {false, false, false, false, false, false, false, false,false, false, false, false,false, false, false, false};

      // run through the permutations
      permutate(chars, taken, temp, 0);
      // permutate(chars, 0);
      found = false;
      return chosen;
    }  
    
    // form the first string from frequency
    static String form() {
      String result = "";
      result += "R".repeat(nums[0]);
      result += "M".repeat(nums[1]);
      result += "I".repeat(nums[2]);
      result += "T".repeat(nums[3]);
      return result;
    }
    static void process(String candidate) {
      for (String i : potential.keySet()) {
        if (matches(i, candidate) != potential.get(i)) return;
      }
      chosen = candidate;
      found = true;
      return;
  
    }
    static void permutate(char[] input, boolean[] taken, char[] current, int idx) {
      if (found) return;
      if (idx == input.length) {
        
        process(String.valueOf(current));
        return;
      }
      boolean[] dup = new boolean[4];
      for (int i = 0; i < input.length; i++) {
        if (taken[i]) {
          continue;
        }
        //  ensuring no duplication
        if (dup[order(input[i])]) continue;
        dup[order(input[i])] = true;
        // add character to current
        current[idx] = input[i];
        taken[i] = true;
        permutate(input, taken, current, idx + 1);
        taken[i] = false;
      }
    }
    // check if current permutation have duplication
    static boolean checkSwap(char str[], int start, int curr) {
      for (int i = start; i < curr; i++) {
          if (str[i] == str[curr]) return false;
      }
      return true;
  }

  static void permutate(char str[], int pos) {
      if (found) return;
      // might need to change condition back to if pos >= n
      if (pos == str.length) { 
          process(String.valueOf(str));
          return;
      }

      for (int i = pos; i < str.length; i++) {

          if (!checkSwap(str, pos, i)) continue;
          mid = str[pos];
          str[pos] = str[i];
          str[i] = mid;
          permutate(str, pos + 1);
          mid = str[pos];
          str[pos] = str[i];
          str[i] = mid;
      }
  }


    // check similarity between 2 words
    static int matches(String word, String another) {
      if (word.length() != another.length()) {
          return -1;
      }
      int matched = 0;
      for (int i = 0; i < word.length(); i++) {
        char c = word.charAt(i);
        if (c != 'R' && c != 'M' && c != 'I' && c != 'T') {
          return -1;
        }
        if (c == another.charAt(i)) {
          matched++;
        }
      }
      return matched;
    }
    
    // find frequency of each word in the secret key
    static void setup(SecretKey key) {
      String str = "RRRRRRRRRRRRRRRR";
      nums[0] = key.guess(str);
      str = "MMMMMMMMMMMMMMMM";
      nums[1] = key.guess(str);
      str = "IIIIIIIIIIIIIIII";
      nums[2] = key.guess(str);
      nums[3] = 16 - nums[0] - nums[1] - nums[2];

  }
  static int order(char c) {
    if (c == 'R') {
      return 0;
    } else if (c == 'M') {
      return 1;
    } else if (c == 'I') {
      return 2;
    }
    return 3;
}

}
