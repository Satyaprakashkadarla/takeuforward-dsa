/**
 * Problem: Frog Jump
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Define f(i) = minimum energy needed to reach step i FROM STEP 0.
 *  - To reach step i, the frog's last jump came from either step
 *    (i-1) or step (i-2) (whichever is valid), so:
 *        f(i) = min( f(i-1) + |heights[i]-heights[i-1]|,
 *                    f(i-2) + |heights[i]-heights[i-2]| )   (if i>=2)
 *        f(i) = f(i-1) + |heights[i]-heights[i-1]|           (if i==1)
 *        f(0) = 0
 *  - This recursion, without any memoization, recomputes many
 *    overlapping subproblems repeatedly, leading to exponential
 *    blowup for larger n.
 *
 * Time Complexity:  O(2^n) -> each call branches into up to 2 more
 *                    calls, with no caching of repeated subproblems
 * Space Complexity: O(n)   -> maximum recursion stack depth
 */
public class Bruteforce {

    public int frogJump(int[] heights) {
        return solve(heights, heights.length - 1);
    }

    private int solve(int[] heights, int i) {
        if (i == 0) return 0;
        if (i == 1) return solve(heights, 0) + Math.abs(heights[1] - heights[0]);

        int oneStep = solve(heights, i - 1) + Math.abs(heights[i] - heights[i - 1]);
        int twoStep = solve(heights, i - 2) + Math.abs(heights[i] - heights[i - 2]);

        return Math.min(oneStep, twoStep);
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] h1 = {2, 1, 3, 5, 4};
        System.out.println(solution.frogJump(h1));  // Expected: 2

        int[] h2 = {7, 5, 1, 2, 6};
        System.out.println(solution.frogJump(h2));  // Expected: 9

        int[] h3 = {3, 10, 3, 11, 3};
        System.out.println(solution.frogJump(h3));  // Expected: 0
    }
}
