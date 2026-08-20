/**
 * Problem: Find Out How Many Times the Array Is Rotated
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - The number of rotations equals the INDEX of the minimum element
 *    in the array. So we just need to find where the minimum sits.
 *  - Scan through the array once, tracking both the smallest value
 *    seen so far and the index at which it occurs.
 *  - Return that index at the end.
 *
 * Time Complexity:  O(n)  -> single pass through the array
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int findKRotation(int[] nums) {
        int minIndex = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }
        }

        return minIndex;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {4, 5, 6, 7, 0, 1, 2, 3};
        System.out.println(solution.findKRotation(nums1));  // Expected: 4

        int[] nums2 = {3, 4, 5, 1, 2};
        System.out.println(solution.findKRotation(nums2));  // Expected: 3

        int[] nums3 = {4, 5, 1, 2};
        System.out.println(solution.findKRotation(nums3));  // Expected: 2
    }
}
