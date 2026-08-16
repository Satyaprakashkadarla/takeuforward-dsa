/**
 * Problem: Floor and Ceil in Sorted Array
 * Approach: Optimal - Single-Pass Binary Search
 *
 * Idea:
 *  - Use a classic binary search over [low, high] = [0, nums.length - 1].
 *  - At each step, compute mid = low + (high - low) / 2.
 *      - If nums[mid] == x, then x itself is both the floor AND the
 *        ceiling (an exact match beats any other candidate on both
 *        sides) -> return {x, x} immediately.
 *      - If nums[mid] < x, nums[mid] is a valid FLOOR candidate
 *        (it's <= x). Record it, then search the right half for a
 *        possibly larger floor: low = mid + 1.
 *      - If nums[mid] > x, nums[mid] is a valid CEIL candidate
 *        (it's >= x). Record it, then search the left half for a
 *        possibly smaller ceiling: high = mid - 1.
 *  - Because we keep updating floor/ceil as we go and always move
 *    toward a BETTER candidate (larger floor / smaller ceil), by the
 *    time low > high, floor and ceil hold the correct final answers.
 *  - If no element is ever <= x, floor stays -1.
 *  - If no element is ever >= x, ceil stays -1.
 *
 * This finds BOTH floor and ceil in a SINGLE binary search pass,
 * rather than running two separate O(log n) searches.
 *
 * Time Complexity:  O(log n) -> one binary search pass
 * Space Complexity: O(1)     -> iterative, no extra space
 */
public class Optimal {

    public int[] getFloorAndCeil(int[] nums, int x) {
        int floor = -1;
        int ceil = -1;

        int low = 0, high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == x) {
                return new int[]{x, x};
            } else if (nums[mid] < x) {
                floor = nums[mid];
                low = mid + 1;
            } else {
                ceil = nums[mid];
                high = mid - 1;
            }
        }

        return new int[]{floor, ceil};
    }

    // Simple test driver
    public static void main(String[] args) {
        Optimal solution = new Optimal();

        int[] nums1 = {3, 4, 4, 7, 8, 10};
        System.out.println(java.util.Arrays.toString(solution.getFloorAndCeil(nums1, 5)));  // Expected: [4, 7]

        int[] nums2 = {3, 4, 4, 7, 8, 10};
        System.out.println(java.util.Arrays.toString(solution.getFloorAndCeil(nums2, 8)));  // Expected: [8, 8]

        int[] nums3 = {2, 4, 6, 8, 10, 12, 14};
        System.out.println(java.util.Arrays.toString(solution.getFloorAndCeil(nums3, 1)));  // Expected: [-1, 2]

        int[] nums4 = {2, 4, 6, 8, 10, 12, 14};
        System.out.println(java.util.Arrays.toString(solution.getFloorAndCeil(nums4, 20))); // Expected: [14, -1]
    }
}
