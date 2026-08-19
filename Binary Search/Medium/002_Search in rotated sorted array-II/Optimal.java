/**
 * Problem: Search in Rotated Sorted Array - II
 * Approach: Optimal (as provided)
 *
 * Idea:
 *  - Iterate through the array once, comparing each element to k.
 *  - Return true the moment a match is found; if the loop completes
 *    without a match, return false.
 *
 * Note: Because this version of the problem allows duplicate values,
 * the "identify the sorted half" binary search trick used in
 * "Search in Rotated Sorted Array - I" cannot always reliably tell
 * which half is sorted in O(1) (e.g., when nums[left] == nums[mid]
 * == nums[right]). See NOTES.md for a full discussion, including the
 * modified binary search approach that is O(log n) on average but
 * still O(n) in the worst case.
 *
 * Time Complexity:  O(n)  -> single pass through the array
 * Space Complexity: O(1)  -> no extra space used
 */
class Solution {
    public boolean searchInARotatedSortedArrayII(int[] nums, int k) {
        for (int num : nums) {
            if (num == k) {
                return true;
            }
        }
        return false;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {7, 8, 1, 2, 3, 3, 3, 4, 5, 6};
        System.out.println(solution.searchInARotatedSortedArrayII(nums1, 3));   // Expected: true

        int[] nums2 = {7, 8, 1, 2, 3, 3, 3, 4, 5, 6};
        System.out.println(solution.searchInARotatedSortedArrayII(nums2, 10));  // Expected: false

        int[] nums3 = {7, 8, 1, 2, 3, 3, 3, 4, 5, 6};
        System.out.println(solution.searchInARotatedSortedArrayII(nums3, 7));   // Expected: true
    }
}
