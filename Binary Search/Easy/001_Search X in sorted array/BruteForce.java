/**
 * Problem: Search X in Sorted Array
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - Simply scan every element of the array from left to right.
 *  - Compare each element with the target.
 *  - If a match is found, return its index immediately.
 *  - If we reach the end of the array without finding it, return -1.
 *
 * NOTE: This approach does NOT take advantage of the fact that the
 * array is sorted. It works on unsorted arrays too, but it is not
 * the optimal solution for this problem. See Optimal.java for the
 * O(log n) binary search approach.
 *
 * Time Complexity:  O(n)  -> in the worst case we check every element
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int search(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums = {-1, 0, 3, 5, 9, 12};

        System.out.println(solution.search(nums, 9));   // Expected: 4
        System.out.println(solution.search(nums, 2));   // Expected: -1
        System.out.println(solution.search(nums, -1));  // Expected: 0
    }
}
