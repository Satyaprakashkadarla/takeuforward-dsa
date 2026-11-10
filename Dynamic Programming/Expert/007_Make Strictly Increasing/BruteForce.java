/**
 * Problem: Make Strictly Increasing
 * Approach: Brute Force / Standard DP - O(n^2) Longest Non-Decreasing Subsequence
 *
 * Idea:
 *  - A change costs ONE operation whatever the amount, so the
 *    question is: which elements can stay untouched? The answer is
 *    n minus the largest set of elements that can stay.
 *
 *  - Two elements i < j can both stay only if the j - i - 1 elements
 *    between them can be given strictly increasing integer values in
 *    between, which needs arr[j] - arr[i] >= j - i, that is
 *        arr[j] - j >= arr[i] - i.
 *    So define b[i] = arr[i] - i. The untouched elements must form a
 *    NON-DECREASING subsequence of b. (Elements before the first
 *    kept one or after the last kept one can always be fixed, since
 *    negative values are allowed and there is no upper limit.)
 *
 *  - dp[i] = length of the longest non-decreasing subsequence of b that
 *    ENDS at index i:
 *        dp[i] = 1 + max( dp[j] ) over j < i with b[j] <= b[i]
 *    This scans every earlier element, hence O(n^2).
 *
 *  - Answer: n - max(dp[i]).
 *
 * Time Complexity:  O(n^2) -> every element is compared with every earlier one
 * Space Complexity: O(n)   -> the b and dp arrays
 *
 * Too slow for n = 10^5 (about 5 * 10^9 comparisons); it is a
 * correctness baseline.
 */
public class Bruteforce {

    public int minOperations(int[] arr) {
        int n = arr.length;

        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[i] = arr[i] - i;
        }

        int[] dp = new int[n];
        int longest = 0;

        for (int i = 0; i < n; i++) {
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                if (b[j] <= b[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            longest = Math.max(longest, dp[i]);
        }

        return n - longest;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.minOperations(new int[]{1, 2, 3, 6, 5, 4}));  // Expected: 2
        System.out.println(solution.minOperations(new int[]{1, 1, 1, 1}));        // Expected: 3
    }
}
