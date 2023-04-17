package test;
import java.io.FileWriter;


public class Test {
    public static void main(String[] args) {
        
        SecretKeyGuesser guesser = new SecretKeyGuesser();
        permutation(guesser);
        
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
    static void permutation(SecretKeyGuesser guesser) {
        FileWriter myFile = null;
        int max = 0;
        try {
            myFile = new FileWriter("test/result.txt");
        
            myFile.write("Key,guesses\n");
            String current = "RRRRRRRRRRRRRRRR";
            int mid = 0;
            while (current != "TTTTTTTTTTTTTTTT") {
                mid = guesser.start(current);
                // System.out.println(current + "," + mid);
                myFile.write(current + "," + mid + "\n");
                max = Math.max(max, mid);
                char[] curr = current.toCharArray();
                for (int i = curr.length - 1; i >=0; i--) {
                    if (order(curr[i]) < 3) {
                        // increase this one and stop
                        curr[i] = charOf(order(curr[i]) + 1);
                        break;
                    }
                    curr[i] = 'R';
                }
                current = String.valueOf(curr);
            }
            myFile.close();
        }
        catch (Exception e) {
            System.out.println("404");
            return;
        }
        System.out.println(max);
    }
}