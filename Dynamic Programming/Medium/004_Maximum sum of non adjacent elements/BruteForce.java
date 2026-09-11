/**
 * Problem: Maximum Sum of Non-Adjacent Elements
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Define f(i) = maximum sum achievable using elements from
 *    nums[0..i], with no two chosen elements adjacent.
 *  - At each index i, we have two choices:
 *      1. SKIP nums[i]: best sum is f(i-1) (whatever the best was
 *         without considering this element at all).
 *      2. TAKE nums[i]: since we can't also take nums[i-1], the
 *         best sum is nums[i] + f(i-2).
 *  - f(i) = max( f(i-1), nums[i] + f(i-2) )
 *  - Base cases: f(-1) = 0 (no elements), f(0) = nums[0] (only one
 *    element available, must take it since there's nothing to
 *    conflict with).
 *  - Without memoization, this recomputes many overlapping
 *    subproblems repeatedly, leading to exponential blowup.
 *
 * Time Complexity:  O(2^n) -> each call branches into 2 more calls,
 *                    with no caching of repeated subproblems
 * Space Complexity: O(n)   -> maximum recursion stack depth
 */
public class Bruteforce {

    public int nonAdjacent(int[] nums) {
        return solve(nums, nums.length - 1);
    }

    private int solve(int[] nums, int i) {
        if (i < 0) return 0;
        if (i == 0) return nums[0];

        int skip = solve(nums, i - 1);
        int take = nums[i] + solve(nums, i - 2);

        return Math.max(skip, take);
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {1, 2, 4};
        System.out.println(solution.nonAdjacent(nums1));  // Expected: 5

        int[] nums2 = {2, 1, 4, 9};
        System.out.println(solution.nonAdjacent(nums2));  // Expected: 11

        int[] nums3 = {1, 7, 16, 8};
        System.out.println(solution.nonAdjacent(nums3));  // Expected: 17
    }
}
