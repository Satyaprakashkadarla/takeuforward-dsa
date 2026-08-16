/**
 * Problem: Search Insert Position
 * Approach: Optimal - Binary Search (Lower Bound)
 *
 * Idea:
 *  - This problem is identical to finding the LOWER BOUND of target:
 *    the smallest index i such that nums[i] >= target.
 *      - If nums[i] == target, we've found the exact index.
 *      - If nums[i] > target, target doesn't exist, but this index
 *        is exactly where it should be inserted.
 *  - Maintain a search space [low, high), where:
 *      - low starts at 0
 *      - high starts at nums.length (NOT nums.length - 1, because the
 *        answer could legitimately be "one past the last index" if
 *        target is larger than every element)
 *  - At each step, compute mid = low + (high - low) / 2.
 *      - If nums[mid] >= target, mid is a POSSIBLE answer, but there
 *        might be a smaller valid index to the left, so high = mid.
 *      - If nums[mid] < target, mid can never be the answer, so
 *        low = mid + 1.
 *  - When the loop ends (low == high), that value is the answer.
 *
 * Time Complexity:  O(log n) -> search space halves every iteration
 * Space Complexity: O(1)     -> iterative, no extra space
 */
public class Optimal {

    public int searchInsert(int[] nums, int target) {
        int low = 0, high = nums.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] >= target) {
                high = mid;      // possible answer, search left half
            } else {
                low = mid + 1;   // mid is too small, search right half
            }
        }

        return low;
    }

    // Simple test driver
    public static void main(String[] args) {
        Optimal solution = new Optimal();

        int[] nums1 = {1, 3, 5, 6};
        System.out.println(solution.searchInsert(nums1, 5));  // Expected: 2

        int[] nums2 = {1, 3, 5, 6};
        System.out.println(solution.searchInsert(nums2, 2));  // Expected: 1

        int[] nums3 = {1, 3, 5, 6};
        System.out.println(solution.searchInsert(nums3, 7));  // Expected: 4

        int[] nums4 = {1, 3, 5, 6};
        System.out.println(solution.searchInsert(nums4, 0));  // Expected: 0
    }
}
