/**
 * Problem: Find Minimum in Rotated Sorted Array
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - Scan through the array once, keeping track of the smallest
 *    value seen so far.
 *  - Since we're just looking for a minimum, the rotation doesn't
 *    matter at all for correctness - it only matters if we want to
 *    exploit it for a faster algorithm (see Optimal.java / NOTES.md
 *    for the O(log n) binary search version).
 *
 * Time Complexity:  O(n)  -> single pass through the array
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int findMin(int[] nums) {
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < min) {
                min = nums[i];
            }
        }
        return min;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {4, 5, 6, 7, 0, 1, 2, 3};
        System.out.println(solution.findMin(nums1));  // Expected: 0

        int[] nums2 = {3, 4, 5, 1, 2};
        System.out.println(solution.findMin(nums2));  // Expected: 1

        int[] nums3 = {4, 5, 6, 7, -7, 1, 2, 3};
        System.out.println(solution.findMin(nums3));  // Expected: -7
    }
}
