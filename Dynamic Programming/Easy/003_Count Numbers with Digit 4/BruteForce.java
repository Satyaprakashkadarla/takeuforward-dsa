/**
 * Problem: Count Numbers with Digit 4
 * Approach: Brute Force (String Conversion + Scan)
 *
 * Idea:
 *  - For every number i from 1 to n, convert i to its string
 *    representation and check whether the character '4' appears
 *    anywhere in it.
 *  - This is a simple, very readable way to check "does this number
 *    contain the digit 4 anywhere?", at the cost of allocating a new
 *    String object for every single number checked.
 *
 * Time Complexity:  O(n * d) -> n numbers, each requiring O(d) work
 *                    to convert to a string and scan it (d = number
 *                    of digits, i.e. O(log10(n)))
 * Space Complexity: O(d) per number checked (temporary String
 *                    allocation) -> O(1) amortized/auxiliary overall,
 *                    since each string is discarded after use
 */
public class Bruteforce {

    public int countNumberswith4(int n) {
        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (String.valueOf(i).indexOf('4') != -1) {
                count++;
            }
        }

        return count;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.countNumberswith4(9));   // Expected: 1
        System.out.println(solution.countNumberswith4(44));  // Expected: 9
    }
}
