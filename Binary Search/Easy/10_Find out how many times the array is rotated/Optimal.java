/**
 * Problem: Find Out How Many Times the Array Is Rotated
 * Approach: Optimal - Binary Search
 *
 * Idea:
 *  - The number of rotations equals the INDEX of the minimum element
 *    in the array, so this reduces to a binary search for the
 *    "break point" where ascending order resets.
 *  - Maintain left = 0, right = n - 1.
 *  - At each step, compute mid.
 *      - If nums[mid] < nums[right], the right half (mid..right) is
 *        already sorted, meaning the minimum is at mid or somewhere
 *        to its left -> right = mid (keep mid in the search space,
 *        since it could BE the minimum).
 *      - Otherwise (nums[mid] >= nums[right]), the break point must
 *        be somewhere to the right of mid -> left = mid + 1.
 *  - When left == right, that index holds the minimum element, and
 *    it also equals the number of rotations performed.
 *
 * Time Complexity:  O(log n) -> search space halves every iteration
 * Space Complexity: O(1)     -> iterative, no extra space
 */
import java.util.ArrayList;

class Solution {
    public int findKRotation(ArrayList<Integer> nums) {
        int n = nums.size();
        int left = 0, right = n - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums.get(mid) < nums.get(right)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        // left is the index where minimum element is located
        // This equals the number of rotations
        return left;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        ArrayList<Integer> nums1 = new ArrayList<>(java.util.Arrays.asList(4, 5, 6, 7, 0, 1, 2, 3));
        System.out.println(solution.findKRotation(nums1));  // Expected: 4

        ArrayList<Integer> nums2 = new ArrayList<>(java.util.Arrays.asList(3, 4, 5, 1, 2));
        System.out.println(solution.findKRotation(nums2));  // Expected: 3

        ArrayList<Integer> nums3 = new ArrayList<>(java.util.Arrays.asList(4, 5, 1, 2));
        System.out.println(solution.findKRotation(nums3));  // Expected: 2
    }
}
