/**
 * Problem: Box Stacking
 * Approach: Brute Force (Plain Recursion Over All Orientations)
 *
 * Idea:
 *  - Expand every box into its 3 orientations. For each orientation
 *    store (height, a, b), where (a, b) is the base with a <= b.
 *    Normalizing the base this way is safe because a box placed on
 *    top can always be turned in the horizontal plane, so only the
 *    sorted pair of base dimensions matters.
 *  - Define f(prevA, prevB) = the maximum height of a stack that can
 *    be built ON TOP of a base (prevA, prevB). Try every orientation
 *    whose base is strictly smaller in both dimensions, and take
 *        f(prevA, prevB) = max over fitting orientations of
 *                          height + f(a, b)
 *  - Start with an "infinite" base so any orientation can be the
 *    bottom box.
 *  - Bases strictly shrink on every step, so the recursion always
 *    terminates, and an orientation can never be reused in the same
 *    stack. No visited array is needed.
 *  - Without memoization, the same sub-stack (same base) is
 *    recomputed again and again, which is exponential.
 *
 * Time Complexity:  Exponential (each call scans all 3n orientations
 *                    and recurses, with no caching)
 * Space Complexity: O(n) -> a stack uses each orientation at most
 *                    once, so the depth is at most 3n
 */
public class Bruteforce {

    private int[] hs, as, bs;

    public int maxHeight(int[] height, int[] width, int[] length) {
        int n = height.length;
        hs = new int[3 * n];
        as = new int[3 * n];
        bs = new int[3 * n];

        int idx = 0;
        for (int i = 0; i < n; i++) {
            int[] dims = {height[i], width[i], length[i]};
            for (int k = 0; k < 3; k++) {
                int x = dims[(k + 1) % 3];
                int y = dims[(k + 2) % 3];
                hs[idx] = dims[k];
                as[idx] = Math.min(x, y);
                bs[idx] = Math.max(x, y);
                idx++;
            }
        }

        return solve(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    private int solve(int prevA, int prevB) {
        int best = 0;

        for (int i = 0; i < hs.length; i++) {
            if (as[i] < prevA && bs[i] < prevB) {
                best = Math.max(best, hs[i] + solve(as[i], bs[i]));
            }
        }

        return best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.maxHeight(
                new int[]{4, 1, 4, 10}, new int[]{6, 2, 5, 12}, new int[]{7, 3, 6, 32}));  // Expected: 60

        System.out.println(solution.maxHeight(
                new int[]{1, 4, 3}, new int[]{2, 5, 4}, new int[]{3, 6, 1}));              // Expected: 15
    }
}
