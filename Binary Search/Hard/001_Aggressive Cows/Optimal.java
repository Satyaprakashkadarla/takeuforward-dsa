/**
 * Problem: Aggressive Cows
 * Approach: Optimal - Binary Search on the Answer (Maximize the Minimum)
 *
 * Idea:
 *  - Sort the stall positions first, since the greedy placement
 *    check requires processing stalls in increasing order.
 *  - The answer (largest possible minimum distance) lies somewhere
 *    between 0 and (max - min) - the full span of the stalls, since
 *    the minimum distance can never exceed the total range available.
 *
 *  - This is a "MAXIMIZE the minimum" variant of binary search on
 *    the answer: as the candidate distance INCREASES, it becomes
 *    HARDER to fit k cows (fewer cows can be placed while
 *    maintaining that spacing) - so feasibility flips from true to
 *    false monotonically as distance grows. This is the mirror image
 *    of "minimize the maximum" problems like Koko Eating Bananas.
 *
 *  - For a candidate distance mid, canPlace() greedily places cows:
 *      - Always place the first cow at the first (smallest) stall.
 *      - Walk through the remaining stalls in order; place the next
 *        cow at the first stall whose distance from the LAST placed
 *        cow is >= mid.
 *      - If we manage to place k cows this way, mid is a feasible
 *        (achievable) minimum distance.
 *
 *  - If canPlace(mid) is true, mid is achievable - record it as our
 *    best answer so far (ans = mid) and try an even LARGER distance:
 *    low = mid + 1.
 *  - If canPlace(mid) is false, mid is too large - try a smaller
 *    distance: high = mid - 1.
 *
 * Time Complexity:  O(n log n) for the sort, plus O(n log(max-min))
 *                    for the binary search itself -> overall
 *                    O(n log n + n log(max(nums)))
 * Space Complexity: O(1) extra (excluding the sort)
 */
import java.util.Arrays;

class Solution {
    public int aggressiveCows(int[] nums, int k) {
        Arrays.sort(nums);

        int low = 0;
        int high = nums[nums.length - 1] - nums[0];
        int ans = 0;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canPlace(nums, k, mid)) {
                ans = mid;
                low = mid + 1;   // Try a larger distance
            } else {
                high = mid - 1;  // Need a smaller distance
            }
        }

        return ans;
    }

    private boolean canPlace(int[] nums, int k, int distance) {
        int count = 1;
        int lastPosition = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - lastPosition >= distance) {
                count++;
                lastPosition = nums[i];

                if (count >= k) {
                    return true;
                }
            }
        }

        return false;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {0, 3, 4, 7, 10, 9};
        System.out.println(solution.aggressiveCows(nums1, 4));  // Expected: 3

        int[] nums2 = {4, 2, 1, 3, 6};
        System.out.println(solution.aggressiveCows(nums2, 2));  // Expected: 5

        int[] nums3 = {10, 1, 2, 7, 5};
        System.out.println(solution.aggressiveCows(nums3, 3));  // Expected: 4
    }
}
