/**
 * Problem: Kth Element of 2 Sorted Arrays
 * Approach: Optimal - Binary Search on the Partition Point
 *
 * Idea:
 *  - Generalizes the "Median of 2 Sorted Arrays" technique: instead
 *    of always splitting at the halfway point, we split such that
 *    the combined LEFT side has EXACTLY k elements total. The kth
 *    element is then simply the largest value on that left side.
 *
 *  - Always binary search over the SMALLER array (swap if needed) -
 *    keeps the search range small and guarantees cutB stays in bounds.
 *
 *  - The search range for cutA (number of elements taken from `a`)
 *    is bounded more tightly than a naive [0, m]:
 *      low  = max(0, k - n)   -> we can't take fewer from `a` than
 *              would force taking more than n from `b` (b only has
 *              n elements total)
 *      high = min(k, m)       -> we can't take more from `a` than
 *              either k allows or a's own length allows
 *
 *  - For a candidate cutA, the forced cutB = k - cutA, since
 *    together they must account for exactly k elements.
 *
 *  - left/right boundary values use +-infinity sentinels when a cut
 *    is at an array's edge, so comparisons still work correctly.
 *
 *  - If leftA <= rightB AND leftB <= rightA, this is the correct
 *    partition: the kth element is max(leftA, leftB) - the largest
 *    value among the k elements collected on the combined left side.
 *
 *  - If leftA > rightB, we've taken too many elements from `a` -
 *    shrink: high = cutA - 1.
 *  - Otherwise (leftB > rightA), we've taken too few from `a` -
 *    grow: low = cutA + 1.
 *
 * Time Complexity:  O(log(min(m, n))) -> binary search over the
 *                    smaller array's (bounded) range
 * Space Complexity: O(1) -> iterative, no extra space (aside from
 *                    the one-time recursive swap call)
 */
class Solution {
    public int kthElement(int[] a, int[] b, int k) {
        int m = a.length;
        int n = b.length;

        // Binary search on the smaller array
        if (m > n) {
            return kthElement(b, a, k);
        }

        // Number of elements taken from a
        int low = Math.max(0, k - n);
        int high = Math.min(k, m);

        while (low <= high) {
            int cutA = low + (high - low) / 2;
            int cutB = k - cutA;

            int leftA  = (cutA == 0) ? Integer.MIN_VALUE : a[cutA - 1];
            int rightA = (cutA == m) ? Integer.MAX_VALUE : a[cutA];

            int leftB  = (cutB == 0) ? Integer.MIN_VALUE : b[cutB - 1];
            int rightB = (cutB == n) ? Integer.MAX_VALUE : b[cutB];

            // Correct partition
            if (leftA <= rightB && leftB <= rightA) {
                return Math.max(leftA, leftB);
            }

            // Took too many elements from a
            if (leftA > rightB) {
                high = cutA - 1;
            } 
            // Took too few elements from a
            else {
                low = cutA + 1;
            }
        }

        return -1; // Should never reach here for valid input
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] a1 = {2, 3, 6, 7, 9};
        int[] b1 = {1, 4, 8, 10};
        System.out.println(solution.kthElement(a1, b1, 5));  // Expected: 6

        int[] a2 = {100, 112, 256, 349, 770};
        int[] b2 = {72, 86, 113, 119, 265, 445, 892};
        System.out.println(solution.kthElement(a2, b2, 7));  // Expected: 256

        int[] a3 = {2, 3, 6};
        int[] b3 = {7, 9};
        System.out.println(solution.kthElement(a3, b3, 4));  // Expected: 7
    }
}
