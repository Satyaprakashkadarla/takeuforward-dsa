/**
 * Problem: Word Wrap
 * Approach: Optimal - Bottom-Up Dynamic Programming (Partition DP)
 *
 * Idea:
 *  - dp[i] = minimum total cost to lay out words i .. n-1, assuming a
 *    new line starts at word i. dp[n] = 0 (no words left).
 *
 *  - Fill the table from the END of the text backwards (i = n-1 down
 *    to 0). When dp[i] is computed, every dp[j + 1] with j >= i is
 *    already known.
 *
 *  - For a line that starts at word i, try each possible last word j:
 *      lineLength = arr[i] + ... + arr[j]   (built up incrementally)
 *      used       = lineLength + (j - i)    (one space between each
 *                                            pair of consecutive words)
 *    If used > k the line no longer fits, and adding more words only
 *    makes it longer, so `break`.
 *
 *  - Cost of the line:
 *      - the LAST line of the whole text (j == n - 1) costs 0;
 *      - every other line costs (k - used)^2, the squared number of
 *        extra spaces at its end.
 *
 *  - Combine with the best layout of the remaining words:
 *        dp[i] = min over fitting j of ( cost + dp[j + 1] )
 *
 *  - Answer: dp[0].
 *
 *  - Overflow note: dp[i] starts at Integer.MAX_VALUE only as a
 *    "no answer yet" marker. It is never added to; the additions are
 *    cost + dp[j + 1], and dp[j + 1] is always a real value because
 *    every word fits on a line by itself (max(arr) <= k). The optimal
 *    total stays well inside int range for these constraints.
 *
 * Time Complexity:  O(n * w) -> for each start word i, the inner loop
 *                    runs only while the line still fits (w = maximum
 *                    words per line); w <= n, so at most O(n^2)
 *                    = 250,000 steps for n = 500
 * Space Complexity: O(n)     -> the dp array
 */
class Solution {
    public int solveWordWrap(int[] arr, int k) {
        int n = arr.length;
        int[] dp = new int[n + 1];

        dp[n] = 0;

        for (int i = n - 1; i >= 0; i--) {
            int lineLength = 0;
            dp[i] = Integer.MAX_VALUE;

            for (int j = i; j < n; j++) {
                lineLength += arr[j];

                // Add one space between consecutive words.
                int used = lineLength + (j - i);

                if (used > k) {
                    break;
                }

                // The last line has no extra-space cost.
                int cost = (j == n - 1)
                        ? 0
                        : (k - used) * (k - used);

                dp[i] = Math.min(dp[i], cost + dp[j + 1]);
            }
        }

        return dp[0];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.solveWordWrap(new int[]{3, 2, 2, 5}, 6));  // Expected: 10
        System.out.println(solution.solveWordWrap(new int[]{3, 2, 2}, 4));     // Expected: 5
    }
}
