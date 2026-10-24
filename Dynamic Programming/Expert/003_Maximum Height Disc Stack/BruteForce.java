/**
 * Problem: Maximum Height Disc Stack
 * Approach: Brute Force / Standard DP (O(n^2), Weighted LIS)
 *
 * Idea:
 *  - Sort the discs by radius, smallest first. Then any disc that
 *    could sit ABOVE disc i (needs a strictly smaller radius) comes
 *    before it in the sorted order.
 *  - Let dp[i] = the tallest stack whose BOTTOM disc is disc i
 *    (discs above it come from earlier positions in the sorted list):
 *        dp[i] = h[i] + max( dp[j] )
 *                over earlier j with radius[j] < radius[i]
 *                              and height[j] < height[i]
 *        (or just h[i] if no such j exists)
 *  - Both comparisons are STRICT, which is what correctly keeps discs
 *    of equal radius (or equal height) from stacking on each other.
 *  - The answer is the maximum dp[i].
 *  - For every disc this scans all earlier discs, hence O(n^2). That
 *    is fine for tiny inputs but far too slow for n = 10^5.
 *
 * Time Complexity:  O(n^2) -> every disc compared with every earlier disc
 *                    (plus O(n log n) for sorting)
 * Space Complexity: O(n)   -> the dp array and the sorted copy
 */
import java.util.Arrays;

public class Bruteforce {

    public int maxStackHeight(int[] r, int[] h) {
        int n = r.length;

        int[][] discs = new int[n][2];
        for (int i = 0; i < n; i++) {
            discs[i][0] = r[i];
            discs[i][1] = h[i];
        }

        // Sort by radius (smallest first)
        Arrays.sort(discs, (a, b) -> Integer.compare(a[0], b[0]));

        int[] dp = new int[n];
        int best = 0;

        for (int i = 0; i < n; i++) {
            dp[i] = discs[i][1];

            for (int j = 0; j < i; j++) {
                if (discs[j][0] < discs[i][0] && discs[j][1] < discs[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + discs[i][1]);
                }
            }

            best = Math.max(best, dp[i]);
        }

        return best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.maxStackHeight(new int[]{5, 7, 3}, new int[]{6, 5, 4}));  // Expected: 10
        System.out.println(solution.maxStackHeight(new int[]{3, 7}, new int[]{7, 4}));         // Expected: 7
    }
}
