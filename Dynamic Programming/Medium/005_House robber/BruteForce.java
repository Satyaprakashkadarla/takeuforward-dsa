/**
 * Problem: House Robber (Circular)
 * Approach: Brute Force (Plain Recursion, Twice Over)
 *
 * Idea:
 *  - Since houses 0 and n-1 are circularly adjacent, they can never
 *    BOTH be robbed. This means the optimal solution either:
 *      (a) excludes house (n-1) entirely, reducing to a LINEAR
 *          "max sum, no two adjacent" problem over houses [0, n-2], or
 *      (b) excludes house 0 entirely, reducing to the same linear
 *          problem over houses [1, n-1].
 *  - The overall answer is the max of these two linear sub-results.
 *  - Each linear sub-problem is solved with the same plain recursion
 *    as "Maximum Sum of Non-Adjacent Elements":
 *        f(i) = max( f(i-1), money[i] + f(i-2) )
 *  - Without memoization, each linear recursion is O(2^n) on its own.
 *
 * Time Complexity:  O(2^n) -> two separate exponential recursions,
 *                    still O(2^n) overall (a constant factor of 2
 *                    doesn't change the complexity class)
 * Space Complexity: O(n)   -> maximum recursion stack depth
 */
public class Bruteforce {

    public int houseRobber(int[] money) {
        int n = money.length;

        if (n == 1) return money[0];

        return Math.max(
            robLinear(money, 0, n - 2),
            robLinear(money, 1, n - 1)
        );
    }

    private int robLinear(int[] a, int l, int r) {
        return solve(a, l, r, r);
    }

    private int solve(int[] a, int l, int r, int i) {
        if (i < l) return 0;
        if (i == l) return a[l];

        int skip = solve(a, l, r, i - 1);
        int take = a[i] + solve(a, l, r, i - 2);

        return Math.max(skip, take);
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] money1 = {2, 1, 4, 9};
        System.out.println(solution.houseRobber(money1));  // Expected: 10

        int[] money2 = {1, 5, 2, 1, 6};
        System.out.println(solution.houseRobber(money2));  // Expected: 11

        int[] money3 = {9, 4, 1, 8};
        System.out.println(solution.houseRobber(money3));  // Expected: 12
    }
}
