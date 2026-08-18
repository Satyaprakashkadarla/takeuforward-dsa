/**
 * Problem: Search in Rotated Sorted Array - I
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - A rotated sorted array is still just an array of numbers - it
 *    doesn't matter that it's "rotated" if we're willing to check
 *    every element one by one.
 *  - Simply scan the array from left to right and compare each
 *    element with k. Return the index on a match, or -1 if we reach
 *    the end without finding it.
 *  - This approach completely ignores the sorted/rotated structure,
 *    which is why it's not optimal - but it's a correct baseline.
 *
 * Time Complexity:  O(n)  -> in the worst case we check every element
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int search(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == k) {
                return i;
            }
        }
        return -1;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(solution.search(nums1, 0));  // Expected: 4

        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(solution.search(nums2, 3));  // Expected: -1

        int[] nums3 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println(solution.search(nums3, 5));  // Expected: 1
    }
}
