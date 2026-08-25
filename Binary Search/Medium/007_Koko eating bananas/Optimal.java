/**
 * Problem: Koko Eating Bananas
 * Approach: Optimal - Binary Search on the Answer
 *
 * Idea:
 *  - The answer (minimum valid eating speed) lies somewhere between
 *    1 (slowest possible) and the size of the largest pile (fastest
 *    speed that could ever be useful - going faster than the biggest
 *    pile provides no benefit, since finishing a pile in "less than
 *    1 hour" isn't possible: it just finishes in exactly 1 hour).
 *
 *  - As the candidate speed mid increases, the total hours needed to
 *    finish all piles DECREASES (or stays the same) - this monotonic
 *    relationship is what makes binary search applicable here.
 *
 *  - For a candidate speed mid, compute the total hours needed:
 *      for each pile, hours += ceil(pile / mid)
 *      implemented as (num + mid - 1) / mid for integer ceiling division
 *
 *  - If hours <= h, mid is a VALID (but not necessarily minimal) speed,
 *    so we record it as a candidate and try an even smaller speed:
 *    right = mid.
 *  - If hours > h, mid is too slow (Koko can't finish in time), so we
 *    need a faster speed: left = mid + 1.
 *
 *  - An early-exit optimization inside the hours-computation loop
 *    breaks out as soon as the running total exceeds h, since we
 *    already know this candidate speed is invalid - no need to keep
 *    summing.
 *
 * Time Complexity:  O(n log(max(nums))) -> binary search does
 *                    O(log(max(nums))) iterations, each doing an O(n)
 *                    pass over the piles to compute total hours
 * Space Complexity: O(1) -> iterative, no extra space
 */
class Solution {
    public int minimumRateToEatBananas(int[] nums, int h) {
        int left = 1;
        int right = 0;
        
        for (int num : nums) {
            right = Math.max(right, num);
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            long hours = 0;
            for (int num : nums) {
                hours += (num + mid - 1) / mid;
                if (hours > h) break; // Early exit if exceeds h
            }
            
            if (hours <= h) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {7, 15, 6, 3};
        System.out.println(solution.minimumRateToEatBananas(nums1, 8));  // Expected: 5

        int[] nums2 = {25, 12, 8, 14, 19};
        System.out.println(solution.minimumRateToEatBananas(nums2, 5));  // Expected: 25

        int[] nums3 = {3, 7, 6, 11};
        System.out.println(solution.minimumRateToEatBananas(nums3, 8));  // Expected: 4
    }
}
