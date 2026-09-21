/**
 * Problem: Longest Common Subsequence
 * Approach: Optimal - 1D DP (Space-Optimized, Using a "Diagonal" Tracker)
 *
 * Idea:
 *  - The standard 2D DP recurrence is:
 *        dp[i][j] = dp[i-1][j-1] + 1                  if str1[i-1]==str2[j-1]
 *        dp[i][j] = max(dp[i-1][j], dp[i][j-1])        otherwise
 *
 *  - Since each cell only depends on the row above (dp[i-1][*]) and
 *    the current row's already-computed values (dp[i][j-1]), we can
 *    collapse the 2D table down to a SINGLE 1D array `dp`, reused
 *    across rows - similar in spirit to other space-optimized DP
 *    problems in this series.
 *
 *  - The tricky part: when we're about to overwrite dp[j] with the
 *    current row's value, we still need dp[i-1][j-1] (the OLD
 *    diagonal value) for the match case. But by the time we reach
 *    column j in the current row, dp[j-1] has ALREADY been updated
 *    to the CURRENT row's value - we've lost the old dp[i-1][j-1].
 *
 *  - The fix: track a separate `diagonal` variable that holds
 *    dp[i-1][j-1] BEFORE it gets overwritten. At the start of each
 *    column j, save the CURRENT (not-yet-overwritten) dp[j] into
 *    `above` - this is exactly dp[i-1][j] at this point. After
 *    computing the new dp[j] for this row, set `diagonal = above`
 *    for the NEXT column's use (since dp[i-1][j] becomes the
 *    "diagonal" reference point for column j+1).
 *
 *  - Using the SHORTER string as the "inner" dimension (the one the
 *    dp array is sized to) minimizes the space used, since the dp
 *    array's size is bounded by the shorter string's length.
 *
 * Time Complexity:  O(n * m) -> visits every (i,j) pair once
 * Space Complexity: O(min(n,m)) -> a single 1D array sized to the
 *                    shorter string's length
 */
class Solution {
    public int lcs(String str1, String str2) {
        // Use the shorter string for the DP array
        if (str1.length() < str2.length()) {
            String temp = str1;
            str1 = str2;
            str2 = temp;
        }

        int m = str2.length();
        int[] dp = new int[m + 1];

        for (int i = 1; i <= str1.length(); i++) {
            int diagonal = 0; // dp[i-1][j-1]

            for (int j = 1; j <= m; j++) {
                int above = dp[j];

                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[j] = diagonal + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }

                diagonal = above;
            }
        }

        return dp[m];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.lcs("bdefg", "bfg"));  // Expected: 3
        System.out.println(solution.lcs("mnop", "mnq"));   // Expected: 2
        System.out.println(solution.lcs("abc", "dafb"));   // Expected: 2
    }
}
