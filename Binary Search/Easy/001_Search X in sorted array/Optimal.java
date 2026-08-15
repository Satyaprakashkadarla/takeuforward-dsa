/**
 * Problem: Search X in Sorted Array
 * Approach: Optimal - Binary Search
 *
 * Idea:
 *  - Since nums is sorted in ascending order, we can repeatedly
 *    divide the search space in half instead of scanning linearly.
 *  - Maintain two pointers: left and right, representing the
 *    current search boundaries.
 *  - Compute mid = left + (right - left) / 2  (avoids integer overflow
 *    compared to (left + right) / 2).
 *  - If nums[mid] == target        -> found it, return mid.
 *  - If nums[mid] < target         -> target must be in the right half,
 *                                      move left = mid + 1.
 *  - If nums[mid] > target         -> target must be in the left half,
 *                                      move right = mid - 1.
 *  - If left > right, the search space is exhausted and target
 *    does not exist in the array -> return -1.
 *
 * Time Complexity:  O(log n) -> search space halves every iteration
 * Space Complexity: O(1)     -> iterative, no extra space
 */
public class Optimal {

    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    // Simple test driver
    public static void main(String[] args) {
        Optimal solution = new Optimal();

        int[] nums = {-1, 0, 3, 5, 9, 12};

        System.out.println(solution.search(nums, 9));   // Expected: 4
        System.out.println(solution.search(nums, 2));   // Expected: -1
        System.out.println(solution.search(nums, -1));  // Expected: 0
    }
}
