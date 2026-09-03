/**
 * Problem: Median of 2 Sorted Arrays
 * Approach: Optimal - Binary Search on the Partition Point
 *
 * Idea:
 *  - Instead of merging the arrays, binary search for the correct
 *    PARTITION POINT that splits the combined logical array into a
 *    left half and a right half of the correct sizes, such that:
 *      1. Every element in the left half is <= every element in the
 *         right half.
 *      2. The left half contains exactly half (or half+1, if odd
 *         total) of the total elements.
 *
 *  - Always binary search over the SMALLER array (swap if needed) -
 *    this keeps the binary search range as small as possible and
 *    guarantees a valid cut2 always exists within bounds.
 *
 *  - `half = (m + n + 1) / 2` is the number of elements that should
 *    end up in the combined LEFT half (this formula correctly
 *    handles both even and odd total lengths).
 *
 *  - For a candidate cut1 (partition point in arr1), the corresponding
 *    cut2 (partition point in arr2) is forced: cut2 = half - cut1,
 *    since together they must account for exactly `half` elements.
 *
 *  - left1/right1 and left2/right2 are the boundary elements around
 *    each cut point, using +-infinity sentinels when a cut is at the
 *    very start or end of an array (so comparisons still work
 *    correctly at the edges).
 *
 *  - If left1 <= right2 AND left2 <= right1, we've found the correct
 *    partition:
 *      - If total length is odd, the median is the larger of the two
 *        left-side boundary elements (max(left1, left2)), since the
 *        "extra" middle element ends up on the left side per the
 *        `half` formula.
 *      - If total length is even, the median is the average of the
 *        largest left-side element and the smallest right-side
 *        element: (max(left1,left2) + min(right1,right2)) / 2.0.
 *
 *  - If left1 > right2, cut1 is too far right (arr1's left side has
 *    a value too large) - shrink the search: high = cut1 - 1.
 *  - Otherwise (left2 > right1), cut1 is too far left - grow the
 *    search: low = cut1 + 1.
 *
 * Time Complexity:  O(log(min(m, n))) -> binary search over the
 *                    smaller array's length
 * Space Complexity: O(1) -> iterative, no extra space (aside from
 *                    the recursive swap call, which is O(1) extra
 *                    stack depth since it happens at most once)
 */
class Solution {
    public double median(int[] arr1, int[] arr2) {
        // Always binary-search the smaller array
        if (arr1.length > arr2.length) {
            return median(arr2, arr1);
        }

        int m = arr1.length;
        int n = arr2.length;

        int low = 0, high = m;
        int half = (m + n + 1) / 2;

        while (low <= high) {
            int cut1 = (low + high) / 2;
            int cut2 = half - cut1;

            int left1  = (cut1 == 0) ? Integer.MIN_VALUE : arr1[cut1 - 1];
            int right1 = (cut1 == m) ? Integer.MAX_VALUE : arr1[cut1];

            int left2  = (cut2 == 0) ? Integer.MIN_VALUE : arr2[cut2 - 1];
            int right2 = (cut2 == n) ? Integer.MAX_VALUE : arr2[cut2];

            if (left1 <= right2 && left2 <= right1) {
                // Correct partition
                if ((m + n) % 2 == 1) {
                    return Math.max(left1, left2);
                }

                return (Math.max(left1, left2)
                        + (long) Math.min(right1, right2)) / 2.0;
            }

            if (left1 > right2) {
                high = cut1 - 1;
            } else {
                low = cut1 + 1;
            }
        }

        return 0.0; // Input arrays are guaranteed sorted
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] arr1a = {2, 4, 6};
        int[] arr1b = {1, 3, 5};
        System.out.println(solution.median(arr1a, arr1b));  // Expected: 3.5

        int[] arr2a = {2, 4, 6};
        int[] arr2b = {1, 3};
        System.out.println(solution.median(arr2a, arr2b));  // Expected: 3.0

        int[] arr3a = {2, 4, 5};
        int[] arr3b = {1, 6};
        System.out.println(solution.median(arr3a, arr3b));  // Expected: 4.0
    }
}
