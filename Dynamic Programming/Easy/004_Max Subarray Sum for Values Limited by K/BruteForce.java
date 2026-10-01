/**
 * Problem: Max Subarray Sum for Values Limited by K
 * Approach: Brute Force (Check Every Subarray)
 *
 * Idea:
 *  - Try every possible contiguous subarray (every pair of start
 *    and end indices).
 *  - For each candidate subarray, verify that EVERY element in it
 *    is <= k, and if so, compute its sum and track the maximum.
 *  - This is the most literal translation of the problem statement
 *    into code, but it repeats a lot of work (re-summing overlapping
 *    ranges, re-checking the same elements many times).
 *
 * Time Complexity:  O(n^2) -> O(n) choices for the start index, each
 *                    paired with an O(n) scan to extend the end
 *                    index and validate + sum along the way
 * Space Complexity: O(1)   -> no extra space used
 */
public class Bruteforce {

    public int maxSum(int[] arr, int k) {
        int n = arr.length;
        int best = 0;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                if (arr[j] > k) {
                    break; // this element (and anything further right) breaks the subarray
                }
                sum += arr[j];
                best = Math.max(best, sum);
            }
        }

        return best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] arr1 = {3, 2, 2, 3, 1, 1, 1, 3};
        System.out.println(solution.maxSum(arr1, 1));  // Expected: 3

        int[] arr2 = {3, 2, 2, 3, 1, 1, 1, 3};
        System.out.println(solution.maxSum(arr2, 2));  // Expected: 4
    }
}
