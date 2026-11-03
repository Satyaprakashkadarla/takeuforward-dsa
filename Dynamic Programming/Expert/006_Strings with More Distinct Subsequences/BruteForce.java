/**
 * Problem: Strings with More Distinct Subsequences
 * Approach: Brute Force (Enumerate Every Subsequence, Count Distinct Ones)
 *
 * Idea:
 *  - A string of length n has 2^n subsets of positions. Each non-empty
 *    subset of positions spells one subsequence.
 *  - Loop over every bitmask from 1 to 2^n - 1: bit i set means
 *    "keep character i". Build the string from the kept characters
 *    and add it to a HashSet. The set removes duplicates (for example,
 *    in "ggg" the three single-character subsequences are all "g").
 *  - The size of the set is the number of distinct non-empty
 *    subsequences. Do this for both strings and return s1 unless s2
 *    has strictly more (a tie goes to s1).
 *
 * Time Complexity:  O(2^n * n) -> 2^n subsets, each built in O(n) and hashed
 * Space Complexity: O(2^n * n) -> the set can hold up to 2^n strings
 *
 * For n = 30 that is over a billion subsets and a huge amount of memory,
 * so this is only a correctness baseline for small strings.
 */
import java.util.HashSet;

public class Bruteforce {

    public static String betterString(String s1, String s2) {
        return count(s1) >= count(s2) ? s1 : s2;
    }

    static long count(String s) {
        int n = s.length();
        HashSet<String> seen = new HashSet<>();

        for (long mask = 1; mask < (1L << n); mask++) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if ((mask >> i & 1L) == 1L) {
                    sb.append(s.charAt(i));
                }
            }
            seen.add(sb.toString());
        }

        return seen.size();
    }

    // Simple test driver
    public static void main(String[] args) {
        System.out.println(betterString("gfg", "ggg"));  // Expected: gfg
        System.out.println(betterString("a", "b"));      // Expected: a
    }
}
