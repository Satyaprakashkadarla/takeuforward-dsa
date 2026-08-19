/**
 * Problem: Search in Rotated Sorted Array - II
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - Scan every element of the array and compare it against k.
 *  - Since duplicates make it unreliable to always identify the
 *    "sorted half" in O(1) (see NOTES.md), a plain scan is a simple,
 *    always-correct O(n) baseline for this version of the problem.
 *  - If a match is found, return true immediately.
 *  - If we reach the end without a match, return false.
 *
 * Time Complexity:  O(n)  -> in the worst case we check every element
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public boolean search(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == k) {
                return true;
            }
        }
        return false;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {7, 8, 1, 2, 3, 3, 3, 4, 5, 6};
        System.out.println(solution.search(nums1, 3));   // Expected: true

        int[] nums2 = {7, 8, 1, 2, 3, 3, 3, 4, 5, 6};
        System.out.println(solution.search(nums2, 10));  // Expected: false

        int[] nums3 = {7, 8, 1, 2, 3, 3, 3, 4, 5, 6};
        System.out.println(solution.search(nums3, 7));   // Expected: true
    }
}
