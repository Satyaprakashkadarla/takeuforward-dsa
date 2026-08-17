/**
 * Problem: First and Last Occurrence
 * Approach: Optimal - Binary Search (Two Passes)
 *
 * Idea:
 *  - Run two independent binary searches:
 *
 *    1. FIRST OCCURRENCE:
 *       Standard binary search, but when nums[mid] == target, instead
 *       of returning immediately, record the index and keep searching
 *       the LEFT half (right = mid - 1) to see if an even earlier
 *       occurrence exists.
 *
 *    2. LAST OCCURRENCE:
 *       Standard binary search, but when nums[mid] == target, record
 *       the index and keep searching the RIGHT half (left = mid + 1)
 *       to see if a later occurrence exists.
 *
 *  - If the first search never finds target, we short-circuit and
 *    return {-1, -1} immediately (no point running the second search).
 *
 * Time Complexity:  O(log n) -> two independent binary searches, each O(log n)
 * Space Complexity: O(1)     -> iterative, no extra space (aside from result array)
 */
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] result = {-1, -1};
        
        // Find first occurrence
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                result[0] = mid;
                right = mid - 1; // Continue searching left side
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // If target not found
        if (result[0] == -1) {
            return result;
        }
        
        // Find last occurrence
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                result[1] = mid;
                left = mid + 1; // Continue searching right side
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

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
