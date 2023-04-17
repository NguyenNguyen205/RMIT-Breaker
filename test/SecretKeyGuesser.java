package test;
import java.util.*;

public class SecretKeyGuesser {
    private static HashMap<String, Integer> potential = new HashMap<>();
    private static boolean found = false;
    private static int[] nums = new int[4];
    private static String chosen = "";
    public SecretKeyGuesser() {}
    public int start(String correct) {
      // System.out.println(correct);
      // guess the word key guessing
      // initialize
      potential = new HashMap<>();
      found = false;
      nums = new int[4];
      chosen = "";
      //
      SecretKey key = new SecretKey(correct);
      // set up
      setup(key);
      String str = form();
      chosen = str;
      int count = 0;
      int result = 0;
      // main loop
      while (result != 16 && count <= 50) {
        result = key.guess(str);
        potential.put(str, result);
        str = next(str);
        count++;
      }
      count += 3;
      return key.getCounter();
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

    static char charOf(int order) {
      if (order == 0) {
        return 'R';
      } else if (order == 1) {
        return 'M';
      } else if (order == 2) {
        return 'I';
      }
      return 'T';
    }
    // find the next guess
    public String next(String current) {
      char[] chars = current.toCharArray();
      findPermutations(chars, 0, 16);
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


    // create permutation
    static boolean shouldSwap(char str[], int start, int curr) {
      for (int i = start; i < curr; i++) {
          if (str[i] == str[curr]) {
              return false;
          }
      }
      return true;
  }

// Prints all distinct permutations in str[0..n-1]
  static void findPermutations(char str[], int index, int n) {
      if (found) return;
      if (index >= n) {
          // check if suitable for next chosen
          String candidate = String.valueOf(str);
          for (String i : potential.keySet()) {
            if (matches(i, candidate) != potential.get(i)) return;
          }
          chosen = candidate;
          found = true;
          return;
      }

      for (int i = index; i < n; i++) {

          // Proceed further for str[i] only if it
          // doesn't match with any of the characters
          // after str[index]
          boolean check = shouldSwap(str, index, i);
          if (check) {
              swap(str, index, i);
              findPermutations(str, index + 1, n);
              swap(str, index, i);
          }
      }
  }

  static void swap(char[] str, int i, int j) {
      char c = str[i];
      str[i] = str[j];
      str[j] = c;
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
}
