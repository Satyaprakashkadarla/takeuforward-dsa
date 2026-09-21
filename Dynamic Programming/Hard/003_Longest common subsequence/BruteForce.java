/**
 * Problem: Longest Common Subsequence
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Define f(i, j) = length of the longest common subsequence
 *    between str1[0..i] and str2[0..j].
 *  - If str1[i] == str2[j], this character can be part of the LCS,
 *    so extend whatever LCS exists between the remaining prefixes:
 *        f(i, j) = 1 + f(i-1, j-1)
 *  - Otherwise, the current characters don't match, so try skipping
 *    one character from EITHER string and take whichever gives a
 *    longer result:
 *        f(i, j) = max(f(i-1, j), f(i, j-1))
 *  - Base case: if either string is exhausted (i<0 or j<0), the
 *    LCS length is 0 (nothing left to compare).
 *  - Without memoization, this recomputes the same (i,j) states
 *    repeatedly across different call paths, causing exponential
 *    blowup.
 *
 * Time Complexity:  O(2^(n+m)) -> each call branches into up to 2
 *                    more calls, with no caching of repeated states
 * Space Complexity: O(n+m)     -> maximum recursion stack depth
 */
public class Bruteforce {

    public int lcs(String str1, String str2) {
        return solve(str1, str2, str1.length() - 1, str2.length() - 1);
    }

    private int solve(String str1, String str2, int i, int j) {
        if (i < 0 || j < 0) {
            return 0;
        }

        if (str1.charAt(i) == str2.charAt(j)) {
            return 1 + solve(str1, str2, i - 1, j - 1);
        }

        int skipStr1 = solve(str1, str2, i - 1, j);
        int skipStr2 = solve(str1, str2, i, j - 1);

        return Math.max(skipStr1, skipStr2);
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.lcs("bdefg", "bfg"));  // Expected: 3
        System.out.println(solution.lcs("mnop", "mnq"));   // Expected: 2
        System.out.println(solution.lcs("abc", "dafb"));   // Expected: 2
    }
}
