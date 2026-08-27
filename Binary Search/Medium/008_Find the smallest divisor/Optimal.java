/**
 * Problem: Find the Smallest Divisor
 * Approach: Optimal - Binary Search on the Answer
 *
 * Idea:
 *  - The answer (smallest valid divisor) lies somewhere between 1
 *    (smallest possible) and max(nums) (largest useful divisor,
 *    since dividing by anything bigger than the largest element
 *    still yields ceil(x/d) = 1 for every element - no further
 *    benefit from going higher).
 *
 *  - As the candidate divisor increases, the sum of ceiling-divided
 *    results DECREASES (or stays the same) - this monotonic
 *    relationship is what makes binary search applicable.
 *
 *  - For a candidate divisor mid, compute the total sum:
 *      for each num in nums, sum += ceil(num / mid)
 *      implemented as (num + mid - 1) / mid for integer ceiling division
 *
 *  - If sum <= limit, mid is a VALID (but not necessarily minimal)
 *    divisor, so we record it as a candidate and try an even smaller
 *    divisor: right = mid - 1.
 *  - If sum > limit, mid is too small (division results too large),
 *    so we need a bigger divisor: left = mid + 1.
 *
 * Time Complexity:  O(n log(max(nums))) -> binary search does
 *                    O(log(max(nums))) iterations, each doing an O(n)
 *                    pass over nums to compute the sum
 * Space Complexity: O(1) -> iterative, no extra space
 */
class Solution {
    public int smallestDivisor(int[] nums, int limit) {
        int left = 1, right = 0;

        for (int num : nums) {
            right = Math.max(right, num);
        }

        while (left < right) {
            int mid = left + (right - left) / 2;

            long sum = 0;
            for (int num : nums) {
                sum += (num + mid - 1) / mid; // ceil(num / mid)
                if (sum > limit) break;        // early exit if already too big
            }

            if (sum <= limit) {
                right = mid;      // mid works; try an even smaller divisor
            } else {
                left = mid + 1;   // mid too small; need a bigger divisor
            }
        }

        return left;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {1, 2, 3, 4, 5};
        System.out.println(solution.smallestDivisor(nums1, 8));  // Expected: 3

        int[] nums2 = {8, 4, 2, 3};
        System.out.println(solution.smallestDivisor(nums2, 10)); // Expected: 2

        int[] nums3 = {8, 4, 2, 3};
        System.out.println(solution.smallestDivisor(nums3, 4));  // Expected: 8
    }
}
