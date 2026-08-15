/**
 * Problem: Lower Bound
 * Approach: Optimal - Binary Search
 *
 * Idea:
 *  - We are searching for the smallest index such that nums[index] >= x.
 *  - Maintain a search space [low, high), where:
 *      - low starts at 0
 *      - high starts at nums.length (NOT nums.length - 1, because the
 *        answer could legitimately be "one past the last index" if
 *        every element is smaller than x)
 *  - At each step, compute mid = low + (high - low) / 2.
 *      - If nums[mid] >= x, mid is a POSSIBLE answer, but there might
 *        be a smaller valid index to the left, so we record high = mid
 *        and continue searching the left half.
 *      - If nums[mid] < x, mid can never be the answer (it doesn't
 *        satisfy the condition), so we discard it and everything to
 *        its left: low = mid + 1.
 *  - The loop continues while low < high. When it ends, low == high,
 *    and that value is exactly the lower bound.
 *
 * Time Complexity:  O(log n) -> search space halves every iteration
 * Space Complexity: O(1)     -> iterative, no extra space
 */
public class Optimal {

    public int lowerBound(int[] nums, int x) {
        int low = 0, high = nums.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] >= x) {
                high = mid;      // possible answer, search left half
            } else {
                low = mid + 1;   // mid is not valid, search right half
            }
        }

        return low;
    }

    // Simple test driver
    public static void main(String[] args) {
        Optimal solution = new Optimal();

        int[] nums1 = {1, 2, 2, 3};
        System.out.println(solution.lowerBound(nums1, 2));  // Expected: 1

        int[] nums2 = {3, 5, 8, 15, 19};
        System.out.println(solution.lowerBound(nums2, 9));  // Expected: 3

        int[] nums3 = {3, 5, 8, 15, 19};
        System.out.println(solution.lowerBound(nums3, 3));  // Expected: 0

        int[] nums4 = {3, 5, 8, 15, 19};
        System.out.println(solution.lowerBound(nums4, 20)); // Expected: 5 (size of array)
    }
}
