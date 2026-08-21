/**
 * Problem: Single Element in Sorted Array
 * Approach: Optimal - Binary Search (Even/Odd Index Parity)
 *
 * Idea:
 *  - Before the single element, every duplicate pair lines up so that
 *    the FIRST occurrence sits at an EVEN index and the SECOND at the
 *    next (odd) index: nums[2k] == nums[2k+1].
 *  - Once we pass the single element, this pairing shifts by one:
 *    now pairs start at ODD indices instead: nums[2k+1] == nums[2k+2].
 *  - So we binary search for the index where this even/odd pairing
 *    pattern "breaks" - that's exactly where the single element is.
 *
 *  - At each step, check mid's parity:
 *      - If mid is EVEN:
 *          - If nums[mid] == nums[mid+1], the pairing still holds at
 *            this point, so the single element must be further right
 *            -> left = mid + 2 (skip past this intact pair).
 *          - Otherwise, the pairing has already broken by here, so
 *            the single element is at mid or to its left -> right = mid.
 *      - If mid is ODD:
 *          - If nums[mid] == nums[mid-1], the pairing still holds, so
 *            search right -> left = mid + 1.
 *          - Otherwise, search left -> right = mid - 1.
 *  - When left == right, nums[left] is the single element.
 *
 * Time Complexity:  O(log n) -> search space halves every iteration
 * Space Complexity: O(1)     -> iterative, no extra space
 */
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Check if mid is even or odd
            // If mid is even and nums[mid] == nums[mid+1], 
            // then the single element is on the right side
            // If mid is odd and nums[mid] == nums[mid-1],
            // then the single element is on the right side
            if (mid % 2 == 0) {
                if (nums[mid] == nums[mid + 1]) {
                    left = mid + 2;
                } else {
                    right = mid;
                }
            } else {
                if (nums[mid] == nums[mid - 1]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return nums[left];
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] nums1 = {1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6};
        System.out.println(solution.singleNonDuplicate(nums1));  // Expected: 4

        int[] nums2 = {1, 1, 3, 5, 5};
        System.out.println(solution.singleNonDuplicate(nums2));  // Expected: 3

        int[] nums3 = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7};
        System.out.println(solution.singleNonDuplicate(nums3));  // Expected: 7
    }
}
