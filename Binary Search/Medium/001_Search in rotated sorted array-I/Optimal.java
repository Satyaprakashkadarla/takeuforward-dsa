/**
 * Problem: Search in Rotated Sorted Array - I
 * Approach: Optimal - Modified Binary Search
 *
 * Idea:
 *  - A rotated sorted array always splits into two parts at any mid
 *    point: one half is GUARANTEED to be properly sorted (ascending),
 *    while the other half contains the "rotation point" and is not.
 *
 *  - At each step:
 *      1. If nums[mid] == k, we found it - return mid.
 *      2. Determine which half is sorted by comparing nums[left] and
 *         nums[mid]:
 *           - If nums[left] <= nums[mid], the LEFT half [left..mid] is sorted.
 *           - Otherwise, the RIGHT half [mid..right] is sorted.
 *      3. Once we know which half is sorted, check if k falls within
 *         that sorted half's value range:
 *           - If yes, we can safely discard the OTHER half and search
 *             within the sorted half.
 *           - If no, k must be in the other (unsorted) half, so we
 *             search there instead.
 *  - This lets us eliminate half the search space every iteration,
 *    just like a normal binary search, despite the array being rotated.
 *
 * Time Complexity:  O(log n) -> search space halves every iteration
 * Space Complexity: O(1)     -> iterative, no extra space
 */
class Solution {
    public int search(int[] nums, int k) {
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == k) {
                return mid;
            }
            
            // Check if left half is sorted
            if (nums[left] <= nums[mid]) {
                // Left half is sorted
                if (k >= nums[left] && k < nums[mid]) {
                    right = mid - 1; // Search in left half
                } else {
                    left = mid + 1; // Search in right half
                }
            } else {
                // Right half is sorted
                if (k > nums[mid] && k <= nums[right]) {
                    left = mid + 1; // Search in right half
                } else {
                    right = mid - 1; // Search in left half
                }
            }
        }
        
        return -1;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(solution.search(nums1, 0));  // Expected: 4

        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(solution.search(nums2, 3));  // Expected: -1

        int[] nums3 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(solution.search(nums3, 5));  // Expected: 1
    }
}
