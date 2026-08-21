/**
 * Problem: Single Element in Sorted Array
 * Approach: Brute Force (XOR of All Elements)
 *
 * Idea:
 *  - XOR-ing a number with itself gives 0 (x ^ x = 0), and XOR-ing
 *    any number with 0 gives that number back (x ^ 0 = x).
 *  - So if we XOR every element in the array together, all the
 *    PAIRED numbers cancel each other out (since each appears
 *    exactly twice), leaving only the single unpaired number.
 *  - This works regardless of the array being sorted - it doesn't
 *    exploit the sorted order at all, which is why it's O(n) instead
 *    of the O(log n) binary search approach.
 *
 * Time Complexity:  O(n)  -> single pass through the array
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int singleNonDuplicate(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {1, 1, 2, 2, 3, 3, 4, 5, 5, 6, 6};
        System.out.println(solution.singleNonDuplicate(nums1));  // Expected: 4

        int[] nums2 = {1, 1, 3, 5, 5};
        System.out.println(solution.singleNonDuplicate(nums2));  // Expected: 3

        int[] nums3 = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7};
        System.out.println(solution.singleNonDuplicate(nums3));  // Expected: 7
    }
}
