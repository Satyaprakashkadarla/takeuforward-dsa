/**
 * Problem: Strings with More Distinct Subsequences
 * Approach: Optimal - Linear DP With a Last-Occurrence Correction
 *
 * Idea:
 *  - dp[i] = the number of DISTINCT subsequences (including the empty
 *    one) of the first i characters of the string. dp[0] = 1: the
 *    empty prefix has only the empty subsequence.
 *
 *  - Adding character ch = s[i-1]: every distinct subsequence of the
 *    first i-1 characters can either skip ch or have ch appended, so
 *        dp[i] = 2 * dp[i - 1]
 *    This counts strings built from DIFFERENT choices separately, so
 *    it overcounts when ch has appeared before.
 *
 *  - Correction: suppose ch last appeared at (0-indexed) position
 *    p = last[ch]. Appending ch to any subsequence of the prefix that
 *    ends BEFORE that earlier ch (there are dp[p] of them, since the
 *    prefix of length p is s[0..p-1]) produces strings that were
 *    already created when that earlier ch was processed. Those dp[p]
 *    strings are duplicates, so
 *        dp[i] = 2 * dp[i - 1] - dp[last[ch]]
 *    Storing last[ch] = i - 1 (the 0-indexed position of the current
 *    character) makes dp[last[ch]] exactly that count next time.
 *
 *  - If ch has never appeared (last[ch] == -1), there are no
 *    duplicates, and dp[i] = 2 * dp[i - 1].
 *
 *  - The answer for one string is dp[n] - 1, removing the empty
 *    subsequence. betterString compares the two counts and returns s1
 *    on ties (>=), as the problem requires.
 *
 *  - last[] has 256 entries, so it covers any character with a code
 *    below 256 (ASCII / Latin-1), which includes lowercase letters.
 *
 *  - Range: a string of length n has at most 2^n - 1 distinct
 *    non-empty subsequences, so for n <= 30 the count is below
 *    2^30, which fits comfortably in a long (and even an int).
 *
 * Time Complexity:  O(n) per string, plus O(256) to initialize last[]
 * Space Complexity: O(n + 256) -> the dp array and the last-position table
 */
import java.util.*;

class Solution {
    public static String betterString(String s1, String s2) {
        return count(s1) >= count(s2) ? s1 : s2;
    }

    static long count(String s) {
        int n = s.length();

        long[] dp = new long[n + 1];
        dp[0] = 1;

        int[] last = new int[256];
        Arrays.fill(last, -1);

        for (int i = 1; i <= n; i++) {
            char ch = s.charAt(i - 1);

            dp[i] = 2 * dp[i - 1];

            if (last[ch] != -1) {
                dp[i] -= dp[last[ch]];
            }

            last[ch] = i - 1;
        }

        // Exclude the empty subsequence
        return dp[n] - 1;
    }

    // Simple test driver
    public static void main(String[] args) {
        System.out.println(betterString("gfg", "ggg"));  // Expected: gfg
        System.out.println(betterString("a", "b"));      // Expected: a
    }
}
