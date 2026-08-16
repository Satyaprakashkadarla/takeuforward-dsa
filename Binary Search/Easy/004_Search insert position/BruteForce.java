/**
 * Problem: Search Insert Position
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - Walk through the array from left to right.
 *  - The first index where nums[i] >= target is our answer:
 *      - If nums[i] == target, we found it exactly.
 *      - If nums[i] > target, target isn't in the array, but this
 *        is exactly where it should be inserted to keep the array sorted.
 *  - If we never find such an index, every element is smaller than
 *    target, so it belongs at the very end: return nums.length.
 *
 * Time Complexity:  O(n)  -> in the worst case we check every element
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return nums.length;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

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
