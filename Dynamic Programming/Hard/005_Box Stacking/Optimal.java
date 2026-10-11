/**
 * Problem: Box Stacking
 * Approach: Optimal - Sort by Base Area + LIS-Style DP
 *
 * NOTE: The code snippet that came with the problem statement was a
 * different problem (mergeTwoParts), so this solution was written
 * for Box Stacking itself.
 *
 * Idea:
 *  1. EXPAND each box into 3 orientations. In each one a different
 *     dimension is the height, and the other two form the base
 *     (a, b), normalized so that a <= b. Normalizing is safe because
 *     a box can always be turned in the horizontal plane. Because
 *     multiple instances of a box are allowed, all 3n orientations
 *     are independent candidates.
 *
 *  2. SORT the orientations by base area in DESCENDING order.
 *     If box X can sit on box Y, then X's base is strictly smaller
 *     in both dimensions, so X's area is strictly smaller than Y's.
 *     Therefore every box that could be BELOW another appears
 *     EARLIER in the sorted order, which gives the DP a valid
 *     processing order. Areas can reach 10^12, so they are compared
 *     as long values.
 *
 *  3. DP (a weighted Longest Increasing Subsequence):
 *        dp[i] = height[i] + max( dp[j] )
 *                over earlier j with a[j] > a[i] and b[j] > b[i]
 *                (or just height[i] if no such j exists)
 *     dp[i] is the tallest stack whose TOP box is orientation i.
 *
 *  4. The answer is the maximum dp[i].
 *
 * Time Complexity:  O(n^2) -> sorting 3n items is O(n log n); the DP
 *                    compares every pair of the 3n orientations
 * Space Complexity: O(n)   -> the orientation list and the dp array
 */
import java.util.Arrays;

class Solution {
    public int maxHeight(int[] height, int[] width, int[] length) {
        int n = height.length;

        // Each row: {height, a, b} with a <= b
        int[][] boxes = new int[3 * n][3];
        int idx = 0;

        for (int i = 0; i < n; i++) {
            int[] dims = {height[i], width[i], length[i]};

            for (int k = 0; k < 3; k++) {
                int x = dims[(k + 1) % 3];
                int y = dims[(k + 2) % 3];

                boxes[idx][0] = dims[k];
                boxes[idx][1] = Math.min(x, y);
                boxes[idx][2] = Math.max(x, y);
                idx++;
            }
        }

        // Sort by base area, largest first (long avoids overflow)
        Arrays.sort(boxes, (p, q) ->
                Long.compare((long) q[1] * q[2], (long) p[1] * p[2]));

        int total = boxes.length;
        int[] dp = new int[total];
        int best = 0;

        for (int i = 0; i < total; i++) {
            dp[i] = boxes[i][0];

            for (int j = 0; j < i; j++) {
                if (boxes[j][1] > boxes[i][1] && boxes[j][2] > boxes[i][2]) {
                    dp[i] = Math.max(dp[i], dp[j] + boxes[i][0]);
                }
            }

            best = Math.max(best, dp[i]);
        }

        return best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.maxHeight(
                new int[]{4, 1, 4, 10}, new int[]{6, 2, 5, 12}, new int[]{7, 3, 6, 32}));  // Expected: 60

        System.out.println(solution.maxHeight(
                new int[]{1, 4, 3}, new int[]{2, 5, 4}, new int[]{3, 6, 1}));              // Expected: 15
    }
}
