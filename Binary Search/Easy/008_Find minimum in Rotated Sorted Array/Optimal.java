/**
 * Problem: Find Minimum in Rotated Sorted Array
 * Approach: Optimal (as provided)
 *
 * Idea:
 *  - Use Java's built-in Collections.min() utility, which internally
 *    scans through the entire collection to find the smallest value.
 *  - This is correct and simple, but note that Collections.min()
 *    does NOT know or care that the input is a rotated sorted array -
 *    it treats it like any other unordered collection and checks
 *    every element, so it runs in O(n) time under the hood.
 *
 * See NOTES.md for a modified binary search approach that exploits
 * the rotated-sorted-array structure to find the minimum in O(log n).
 *
 * Time Complexity:  O(n)  -> Collections.min() scans every element
 * Space Complexity: O(1)  -> no extra space used (beyond input list)
 */
import java.util.*;

class Solution {
    public int findMin(ArrayList<Integer> arr) {
        return Collections.min(arr);
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        ArrayList<Integer> nums1 = new ArrayList<>(Arrays.asList(4, 5, 6, 7, 0, 1, 2, 3));
        System.out.println(solution.findMin(nums1));  // Expected: 0

        ArrayList<Integer> nums2 = new ArrayList<>(Arrays.asList(3, 4, 5, 1, 2));
        System.out.println(solution.findMin(nums2));  // Expected: 1

        ArrayList<Integer> nums3 = new ArrayList<>(Arrays.asList(4, 5, 6, 7, -7, 1, 2, 3));
        System.out.println(solution.findMin(nums3));  // Expected: -7
    }
}
