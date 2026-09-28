/**
 * Problem: Count Ways to Reach the Nth Stair (1, 2 or 3 Steps)
 * Approach: Optimal - Iterative Dynamic Programming (O(1) Space)
 *
 * Idea:
 *  - Same recurrence as the brute force:
 *        ways(n) = ways(n-1) + ways(n-2) + ways(n-3)
 *    (the Tribonacci recurrence), but computed ITERATIVELY from the
 *    bottom up instead of recursively from the top down.
 *
 *  - Each new value only depends on the previous THREE values, so
 *    instead of storing a full dp array (O(n) space), we track just
 *    three rolling variables (a, b, c) and slide the window forward.
 *
 *  - Base cases are handled directly:
 *        n <= 2 -> return n  (ways(1) = 1, ways(2) = 2)
 *        n == 3 -> return 4  (1+1+1, 1+2, 2+1, 3)
 *
 *  - For n >= 4, start with (a, b, c) = (ways(1), ways(2), ways(3))
 *    = (1, 2, 4) and repeat: d = a + b + c, then shift a <- b,
 *    b <- c, c <- d.
 *
 * Time Complexity:  O(n) -> single pass from 4 to n
 * Space Complexity: O(1) -> only three rolling variables
 */
class Solution {
    static int countWays(int n) {
        if (n <= 2) return n;
        if (n == 3) return 4;

        int a = 1; // ways(1)
        int b = 2; // ways(2)
        int c = 4; // ways(3)

        for (int i = 4; i <= n; i++) {
            int d = a + b + c;
            a = b;
            b = c;
            c = d;
        }

        return c;
    }

    // Simple test driver
    public static void main(String[] args) {
        System.out.println(countWays(3));  // Expected: 4
        System.out.println(countWays(4));  // Expected: 7
        System.out.println(countWays(1));  // Expected: 1
    }
}
