/**
 * Problem: Maximum Score Using Neighbor Products (Burst Balloons)
 * Approach: Optimal - Bottom-Up Interval DP
 *
 * Idea:
 *  - Padding: copy arr into a[1..n] and set a[0] = a[n+1] = 1. The
 *    problem says a missing neighbor counts as 1, so the padding turns
 *    the "end of the array" cases into ordinary cases.
 *
 *  - Define dp[l][r] = the maximum score from removing ALL elements
 *    a[l..r], assuming the elements just outside the interval,
 *    a[l-1] and a[r+1], stay in place the whole time.
 *
 *  - KEY TRICK: instead of asking which element is removed FIRST
 *    (after which the neighbors change and the left and right parts
 *    become tangled), ask which element k in [l, r] is removed LAST.
 *      * When k is removed, every other element of [l, r] is already
 *        gone, so k's neighbors are exactly a[l-1] and a[r+1]:
 *            gain = a[l-1] * a[k] * a[r+1]
 *      * Before that, the two sides [l, k-1] and [k+1, r] were
 *        cleared independently. The element k separates them, and
 *        a[l-1]/a[k] and a[k]/a[r+1] are their fixed outside
 *        neighbors, so they contribute dp[l][k-1] and dp[k+1][r].
 *
 *    dp[l][r] = max over k in [l, r] of
 *               ( a[l-1] * a[k] * a[r+1] + dp[l][k-1] + dp[k+1][r] )
 *
 *  - Empty intervals (k = l gives dp[l][l-1], k = r gives dp[r+1][r])
 *    are 0, which Java's default array initialization already provides.
 *
 *  - Fill order: by increasing interval length (len = 1..n), so that
 *    both smaller intervals dp[l][k-1] and dp[k+1][r] are already
 *    computed when dp[l][r] needs them.
 *
 *  - Answer: dp[1][n], the best score for the whole array.
 *
 * Time Complexity:  O(n^3) -> O(n^2) intervals, each trying up to n
 *                    choices of the last element k
 * Space Complexity: O(n^2) -> the dp table of size (n+2) x (n+2)
 *
 * Overflow note: each removal gains at most 100*100*100 = 10^6 and
 * there are at most 100 removals, so the total is at most 10^8, which
 * fits comfortably in an int.
 */
class Solution {
    public int maxProductSum(int[] arr) {
        int n = arr.length;
        int[] a = new int[n + 2];

        a[0] = a[n + 1] = 1;
        System.arraycopy(arr, 0, a, 1, n);

        int[][] dp = new int[n + 2][n + 2];

        // len = length of the interval
        for (int len = 1; len <= n; len++) {
            for (int l = 1; l + len - 1 <= n; l++) {
                int r = l + len - 1;

                for (int k = l; k <= r; k++) {
                    int score = a[l - 1] * a[k] * a[r + 1]
                              + dp[l][k - 1]
                              + dp[k + 1][r];

                    dp[l][r] = Math.max(dp[l][r], score);
                }
            }
        }

        return dp[1][n];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.maxProductSum(new int[]{5, 10}));          // Expected: 60
        System.out.println(solution.maxProductSum(new int[]{1, 2, 3, 4, 5}));  // Expected: 110
    }
}
