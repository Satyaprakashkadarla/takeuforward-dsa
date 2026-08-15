/**
 * Problem: Lower Bound
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - Walk through the array from left to right.
 *  - The first index where nums[i] >= x is our answer (since the
 *    array is sorted, once we find such an index we don't need to
 *    look further — everything after it will also be >= x, but we
 *    want the SMALLEST such index).
 *  - If we never find such an index, it means every element is
 *    smaller than x, so the lower bound is nums.length (i.e., x
 *    would be inserted at the very end).
 *
 * Time Complexity:  O(n)  -> in the worst case we check every element
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int lowerBound(int[] nums, int x) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= x) {
                return i;
            }
        }
        return nums.length;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

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
