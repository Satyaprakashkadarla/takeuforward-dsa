/**
 * Problem: Maximum Height Disc Stack
 * Approach: Optimal - Sort by Radius + Fenwick Tree (Max) Over Heights
 *
 * Idea:
 *  - This is a weighted Longest Increasing Subsequence in 2D. A disc
 *    may go above another only if BOTH radius and height are strictly
 *    smaller, so we need an ordering that handles one dimension and a
 *    fast query for the other.
 *
 *  - Step 1: sort the discs by radius. Now every disc that could sit
 *    above a given disc (smaller radius) has already been processed.
 *
 *  - Step 2: for the current disc with height `height`, the best stack
 *    that has this disc at the BOTTOM is
 *        height + (best stack whose bottom disc has height < `height`)
 *    That "best stack among earlier discs with a smaller height" is a
 *    PREFIX MAXIMUM over heights, which a Fenwick tree (binary
 *    indexed tree) answers in O(log H). Here the tree stores MAXIMUMS
 *    instead of sums:
 *        query(i)       = max value stored for heights <= i
 *        update(i, v)   = record that a stack of total height v has a
 *                         bottom disc of height i
 *    Querying `height - 1` gives "heights strictly smaller".
 *
 *  - Step 3 (the subtle part): discs with the SAME radius must not be
 *    stackable on each other (the radius has to be strictly smaller).
 *    So discs are processed in GROUPS of equal radius:
 *      * first compute dp for every disc in the group using only the
 *        tree built from SMALLER radii,
 *      * then add all of the group's results to the tree.
 *    If updates were applied immediately, a later disc of the same
 *    radius could "see" an earlier one and wrongly stack on it.
 *    Example: r = [5, 5], h = [3, 4]. The right answer is 4. With
 *    immediate updates the second disc would add the first one's 3
 *    and report 7.
 *
 *  - Heights are at most 1000, so the tree has a fixed size (1002),
 *    independent of n.
 *
 *  - The answer is the largest dp value seen.
 *
 * Time Complexity:  O(n log n) -> sorting n discs, plus n queries and
 *                    n updates, each about log2(1000) = 10 steps
 * Space Complexity: O(n) -> the discs array and the temporary dp
 *                    arrays; the Fenwick tree is a constant 1002 ints
 *
 * Overflow note: heights in a valid stack are strictly decreasing and
 * at most 1000, so the largest possible answer is
 * 1 + 2 + ... + 1000 = 500,500, well inside an int.
 */
import java.util.*;

class Solution {
    public int maxStackHeight(int[] r, int[] h) {
        int n = r.length;

        int[][] discs = new int[n][2];
        for (int i = 0; i < n; i++) {
            discs[i][0] = r[i];
            discs[i][1] = h[i];
        }

        // Sort by radius
        Arrays.sort(discs, (a, b) -> Integer.compare(a[0], b[0]));

        // h[i] <= 1000, so Fenwick tree size can be 1001
        int[] bit = new int[1002];
        int ans = 0;

        for (int i = 0; i < n; ) {
            int j = i;

            // Find all discs having the same radius
            while (j < n && discs[j][0] == discs[i][0]) {
                j++;
            }

            // Store updates temporarily so equal radii cannot stack
            int[] dp = new int[j - i];

            for (int k = i; k < j; k++) {
                int height = discs[k][1];

                // Maximum stack height with previous height < current height
                dp[k - i] = height + query(bit, height - 1);
                ans = Math.max(ans, dp[k - i]);
            }

            // Apply updates only after processing this radius group
            for (int k = i; k < j; k++) {
                update(bit, discs[k][1], dp[k - i]);
            }

            i = j;
        }

        return ans;
    }

    // Maximum value for heights <= index
    private int query(int[] bit, int index) {
        int max = 0;

        while (index > 0) {
            max = Math.max(max, bit[index]);
            index -= index & -index;
        }

        return max;
    }

    // Update position with maximum value
    private void update(int[] bit, int index, int value) {
        while (index < bit.length) {
            bit[index] = Math.max(bit[index], value);
            index += index & -index;
        }
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.maxStackHeight(new int[]{5, 7, 3}, new int[]{6, 5, 4}));  // Expected: 10
        System.out.println(solution.maxStackHeight(new int[]{3, 7}, new int[]{7, 4}));         // Expected: 7
        System.out.println(solution.maxStackHeight(new int[]{5, 5}, new int[]{3, 4}));         // Expected: 4 (equal radii cannot stack)
    }
}
