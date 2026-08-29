/**
 * Problem: Aggressive Cows
 * Approach: Brute Force (Linear Scan Over Possible Distances)
 *
 * Idea:
 *  - Sort the stall positions first, since placement decisions only
 *    make sense relative to sorted order.
 *  - The smallest possible minimum distance is 1 (or 0, but any
 *    distance below 1 is meaningless once sorted with distinct-ish
 *    positions), and the largest possible is the full span of the
 *    stalls (nums[n-1] - nums[0]) - you can never get a minimum
 *    distance bigger than the total range.
 *  - Try every candidate distance starting from the LARGEST possible
 *    value downward, greedily checking if k cows can be placed with
 *    at least that much distance apart.
 *  - The FIRST (largest) distance for which placement succeeds is
 *    the answer, since larger distances only make placement harder
 *    (monotonically), so scanning downward from the max guarantees
 *    finding the true maximum feasible distance first.
 *
 * Time Complexity:  O(max(nums) * n) -> up to max(nums) candidate
 *                    distances, each requiring an O(n) greedy check
 * Space Complexity: O(1) extra (excluding the O(log n) sort)
 */
import java.util.Arrays;

public class Bruteforce {

    public int aggressiveCows(int[] nums, int k) {
        Arrays.sort(nums);

        int maxDistance = nums[nums.length - 1] - nums[0];

        for (int distance = maxDistance; distance >= 1; distance--) {
            if (canPlace(nums, k, distance)) {
                return distance;
            }
        }

        return 0; // fallback (shouldn't be reached given constraints, k >= 2)
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
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {0, 3, 4, 7, 10, 9};
        System.out.println(solution.aggressiveCows(nums1, 4));  // Expected: 3

        int[] nums2 = {4, 2, 1, 3, 6};
        System.out.println(solution.aggressiveCows(nums2, 2));  // Expected: 5

        int[] nums3 = {10, 1, 2, 7, 5};
        System.out.println(solution.aggressiveCows(nums3, 3));  // Expected: 4
    }
}
