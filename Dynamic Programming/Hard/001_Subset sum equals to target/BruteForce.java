/**
 * Problem: Subset Sum Equals to Target
 * Approach: Brute Force (Plain Recursion - Include/Exclude)
 *
 * Idea:
 *  - Define f(i, remaining) = true if some subset of arr[0..i] sums
 *    to exactly `remaining`.
 *  - At each index i, we have two choices:
 *      1. EXCLUDE arr[i]: check if f(i-1, remaining) is achievable
 *         without this element at all.
 *      2. INCLUDE arr[i] (only if arr[i] <= remaining): check if
 *         f(i-1, remaining - arr[i]) is achievable, i.e., can the
 *         rest of the elements make up the leftover amount.
 *  - f(i, remaining) = f(i-1, remaining) OR (arr[i] <= remaining AND
 *                       f(i-1, remaining - arr[i]))
 *  - Base case: remaining == 0 is always achievable (the empty
 *    subset trivially sums to 0), regardless of which elements
 *    remain to consider.
 *  - Without memoization, this branches into 2 calls at every index,
 *    causing exponential blowup for larger n.
 *
 * Time Complexity:  O(2^n) -> each call branches into up to 2 more
 *                    calls, with no caching of repeated (i,remaining)
 *                    states
 * Space Complexity: O(n)   -> maximum recursion stack depth
 */
public class Bruteforce {

    public boolean isSubsetSum(int[] arr, int target) {
        return solve(arr, arr.length - 1, target);
    }

    private boolean solve(int[] arr, int i, int remaining) {
        if (remaining == 0) {
            return true;
        }
        if (i < 0) {
            return false;
        }

        boolean exclude = solve(arr, i - 1, remaining);
        boolean include = (arr[i] <= remaining) && solve(arr, i - 1, remaining - arr[i]);

        return exclude || include;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] arr1 = {1, 2, 7, 3};
        System.out.println(solution.isSubsetSum(arr1, 6));  // Expected: true

        int[] arr2 = {2, 3, 5};
        System.out.println(solution.isSubsetSum(arr2, 6));  // Expected: false

        int[] arr3 = {7, 54, 4, 12, 15, 5};
        System.out.println(solution.isSubsetSum(arr3, 9));  // Expected: true
    }
}
