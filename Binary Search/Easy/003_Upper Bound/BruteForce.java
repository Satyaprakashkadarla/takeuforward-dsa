/**
 * Problem: Upper Bound
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - Walk through the array from left to right.
 *  - The first index where nums[i] > x (strictly greater) is our
 *    answer — since the array is sorted, this is guaranteed to be
 *    the SMALLEST such index.
 *  - If we never find such an index, it means every element is
 *    less than or equal to x, so the upper bound is nums.length.
 *
 * Time Complexity:  O(n)  -> in the worst case we check every element
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int upperBound(int[] nums, int x) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > x) {
                return i;
            }
        }
        return nums.length;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {1, 2, 2, 3};
        System.out.println(solution.upperBound(nums1, 2));  // Expected: 3

        int[] nums2 = {3, 5, 8, 15, 19};
        System.out.println(solution.upperBound(nums2, 9));  // Expected: 3

        int[] nums3 = {3, 5, 8, 15, 19};
        System.out.println(solution.upperBound(nums3, 3));  // Expected: 1

        int[] nums4 = {3, 5, 8, 15, 19};
        System.out.println(solution.upperBound(nums4, 20)); // Expected: 5 (size of array)
    }
}
