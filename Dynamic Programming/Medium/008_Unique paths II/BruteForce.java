/**
 * Problem: Grid Unique Paths
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Define f(i, j) = number of unique paths from cell (i, j) to the
 *    bottom-right cell (m-1, n-1), moving only right or down.
 *  - From any cell (i, j), the paths to the destination are the sum
 *    of paths going right (f(i, j+1)) and paths going down
 *    (f(i+1, j)), as long as those cells are within bounds.
 *  - Base case: f(m-1, n-1) = 1 (already at destination - exactly
 *    one "path" of zero moves).
 *  - Without memoization, this recomputes the same (i, j) states
 *    repeatedly across different paths, leading to exponential blowup.
 *
 * Time Complexity:  O(2^(m+n)) -> each call branches into up to 2
 *                    more calls, with no caching of repeated (i,j)
 *                    states
 * Space Complexity: O(m + n)   -> maximum recursion depth (the
 *                    longest possible path length)
 */
public class Bruteforce {

    public int uniquePaths(int m, int n) {
        return solve(0, 0, m, n);
    }

    private int solve(int i, int j, int m, int n) {
        if (i == m - 1 && j == n - 1) {
            return 1;
        }
        if (i >= m || j >= n) {
            return 0;
        }

        int goRight = solve(i, j + 1, m, n);
        int goDown = solve(i + 1, j, m, n);

        return goRight + goDown;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.uniquePaths(3, 2));  // Expected: 3
        System.out.println(solution.uniquePaths(2, 4));  // Expected: 4
        System.out.println(solution.uniquePaths(3, 3));  // Expected: 6
    }
}
