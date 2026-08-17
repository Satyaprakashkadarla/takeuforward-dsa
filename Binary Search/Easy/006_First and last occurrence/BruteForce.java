/**
 * Problem: First and Last Occurrence
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - Scan the array once from left to right.
 *  - The FIRST time we encounter target, record it as the starting
 *    position (only record it once).
 *  - Every time we encounter target after that, keep updating the
 *    ending position, so by the end it holds the LAST occurrence.
 *  - If target is never found, both positions remain -1.
 *
 * Time Complexity:  O(n)  -> single pass through the array
 * Space Complexity: O(1)  -> no extra space used (aside from the
 *                             output array)
 */
public class Bruteforce {

    public int[] searchRange(int[] nums, int target) {
        int first = -1, last = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                if (first == -1) {
                    first = i;
                }
                last = i;
            }
        }

        return new int[]{first, last};
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {5, 7, 7, 8, 8, 10};
        System.out.println(java.util.Arrays.toString(solution.searchRange(nums1, 8)));  // Expected: [3, 4]

        int[] nums2 = {5, 7, 7, 8, 8, 10};
        System.out.println(java.util.Arrays.toString(solution.searchRange(nums2, 6)));  // Expected: [-1, -1]

        int[] nums3 = {5, 7, 7, 8, 8, 10};
        System.out.println(java.util.Arrays.toString(solution.searchRange(nums3, 5)));  // Expected: [0, 0]

        int[] nums4 = {};
        System.out.println(java.util.Arrays.toString(solution.searchRange(nums4, 5)));  // Expected: [-1, -1]
    }
}
