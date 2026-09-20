/**
 * Problem: Partition Equal Subset Sum
 * Approach: Brute Force (Plain Recursion - Include/Exclude, Reusing Subset Sum Logic)
 *
 * Idea:
 *  - First, compute the total sum. If it's ODD, an equal partition
 *    is mathematically impossible (you can't split an odd number
 *    into two equal integer halves) -> return false immediately.
 *  - If the sum is even, the problem reduces EXACTLY to Subset Sum:
 *    does some subset of arr sum to exactly (total / 2)? If so, that
 *    subset and everything else (the complement) form two equal-sum
 *    halves.
 *  - Reuse the same include/exclude recursion as Subset Sum:
 *        f(i, remaining) = f(i-1, remaining) OR
 *                           (arr[i] <= remaining AND f(i-1, remaining - arr[i]))
 *  - Without memoization, this suffers the same exponential blowup
 *    as plain Subset Sum recursion.
 *
 * Time Complexity:  O(2^n) -> each call branches into up to 2 more
 *                    calls, with no caching of repeated states
 * Space Complexity: O(n)   -> maximum recursion stack depth
 */
public class Bruteforce {

    public boolean equalPartition(int n, int[] arr) {
        int sum = 0;
        for (int x : arr) {
            sum += x;
        }

        if (sum % 2 != 0) {
            return false;
        }

        int target = sum / 2;
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

        int[] arr1 = {1, 10, 21, 10};
        System.out.println(solution.equalPartition(arr1.length, arr1));  // Expected: true

        int[] arr2 = {1, 2, 3, 5};
        System.out.println(solution.equalPartition(arr2.length, arr2));  // Expected: false

        int[] arr3 = {2, 2, 1, 1};
        System.out.println(solution.equalPartition(arr3.length, arr3));  // Expected: true
    }
}
