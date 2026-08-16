/**
 * Problem: Floor and Ceil in Sorted Array
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - Floor: the largest element <= x. Scan the array; every time we
 *    see an element <= x, update floor to that element (since the
 *    array is sorted ascending, the LAST such element we see while
 *    scanning left-to-right will be the largest one <= x).
 *  - Ceil: the smallest element >= x. Scan the array; the FIRST
 *    element we encounter that is >= x is the ceiling (since the
 *    array is sorted, nothing earlier could be smaller-but-still->=x).
 *  - Both can be computed in a single linear pass.
 *
 * Time Complexity:  O(n)  -> single pass through the array
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int[] getFloorAndCeil(int[] nums, int x) {
        int floor = -1;
        int ceil = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= x) {
                floor = nums[i];   // keep updating; sorted array means
                                    // the last one we see <= x is the largest
            }
            if (nums[i] >= x && ceil == -1) {
                ceil = nums[i];    // first element >= x is the ceiling
            }
        }

        return new int[]{floor, ceil};
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

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
