/**
 * Problem: Form Pyramid (Minimum Cost to Build a Pyramid of Stones)
 * Approach: Optimal - Prefix/Suffix DP
 *
 * Idea:
 *  - A pyramid of peak height h always has a FIXED total sum:
 *        1 + 2 + ... + (h-1) + h + (h-1) + ... + 2 + 1 = h^2
 *    (a classic identity: this is 2*(triangular number up to h-1) + h,
 *    which simplifies exactly to h^2).
 *
 *  - Since every unit of height removed from a stone is "wasted"
 *    (costs 1 unit and contributes nothing to the final pyramid),
 *    minimizing cost is EXACTLY the same as MAXIMIZING the sum of
 *    the pyramid we keep - which, since pyramid sum = h^2, means
 *    maximizing the achievable peak height h.
 *
 *  - left[i] = the longest possible "ramp up" ending at position i,
 *    where each step can increase by at most 1 (limited by the
 *    actual arr[i] value too, since we can only REDUCE heights, never
 *    increase them):
 *        left[i] = min(arr[i], left[i-1] + 1)
 *    This represents: "the tallest a strictly-increasing-by-1 ramp
 *    COULD be at position i," given both the physical stone height
 *    limit and the ramp-continuity constraint from the left neighbor.
 *
 *  - right[i] = the symmetric "ramp down" version, computed from the
 *    right side:
 *        right[i] = min(arr[i], right[i+1] + 1)
 *
 *  - For each position i, if we treat it as the PEAK of a pyramid,
 *    the tallest pyramid centered there is limited by BOTH how far
 *    it can ramp up from the left AND ramp down to the right:
 *        min(left[i], right[i])
 *
 *  - The best (tallest) pyramid achievable ANYWHERE in the array is
 *    the maximum of this quantity over all positions i.
 *
 *  - Final answer: total original sum MINUS (maxHeight^2), since
 *    that's exactly how much "wasted" height we're forced to remove
 *    to carve out the best possible pyramid, with everything else
 *    reduced to 0.
 *
 * Time Complexity:  O(n) -> three linear passes (compute total,
 *                    left[], right[], then the final max scan)
 * Space Complexity: O(n) -> two auxiliary arrays of size n
 */
class Solution {
    public int formPyramid(int[] arr) {
        int n = arr.length;
        long total = 0;

        for (int x : arr) {
            total += x;
        }

        int[] left = new int[n];
        int[] right = new int[n];

        // Maximum possible increasing sequence ending at i
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = Math.min(arr[i], left[i - 1] + 1);
        }

        // Maximum possible decreasing sequence starting at i
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            right[i] = Math.min(arr[i], right[i + 1] + 1);
        }

        int maxHeight = 1;

        for (int i = 0; i < n; i++) {
            maxHeight = Math.max(maxHeight, Math.min(left[i], right[i]));
        }

        return (int) (total - (long) maxHeight * maxHeight);
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] arr1 = {1, 2, 3, 4, 2, 1};
        System.out.println(solution.formPyramid(arr1));  // Expected: 4

        int[] arr2 = {1, 2, 1};
        System.out.println(solution.formPyramid(arr2));  // Expected: 0
    }
}
