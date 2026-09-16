/**
 * Problem: Minimum Falling Path Sum
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Define f(i, j) = minimum path sum to reach the BOTTOM (last
 *    row) starting from cell (i, j).
 *  - If we're already in the last row, f(i, j) = matrix[i][j]
 *    (nothing more to add - we've reached the end).
 *  - Otherwise, f(i, j) = matrix[i][j] + min of f(i+1, j-1),
 *    f(i+1, j), f(i+1, j+1) - whichever of the three reachable cells
 *    below gives the cheapest continuation (skipping any that would
 *    be out of column bounds).
 *  - Since we can START at ANY cell in the first row, the overall
 *    answer is the minimum of f(0, j) across all j.
 *  - Without memoization, this recomputes the same (i,j) states
 *    repeatedly, branching up to 3 ways at every level.
 *
 * Time Complexity:  O(3^m) -> up to 3 branches at each of m row
 *                    levels, with no caching of repeated states
 * Space Complexity: O(m)   -> maximum recursion stack depth
 */
public class Bruteforce {

    public int minFallingPathSum(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int ans = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            ans = Math.min(ans, solve(matrix, 0, j, m, n));
        }
        return ans;
    }

    private int solve(int[][] matrix, int i, int j, int m, int n) {
        if (i == m - 1) {
            return matrix[i][j];
        }

        int best = solve(matrix, i + 1, j, m, n); // straight down

        if (j > 0) {
            best = Math.min(best, solve(matrix, i + 1, j - 1, m, n)); // bottom-left
        }
        if (j + 1 < n) {
            best = Math.min(best, solve(matrix, i + 1, j + 1, m, n)); // bottom-right
        }

        return matrix[i][j] + best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] m1 = {{1, 2, 10, 4}, {100, 3, 2, 1}, {1, 1, 20, 2}, {1, 2, 2, 1}};
        System.out.println(solution.minFallingPathSum(m1));  // Expected: 6

        int[][] m2 = {{1, 4, 3, 1}, {2, 3, -1, -1}, {1, 1, -1, 8}};
        System.out.println(solution.minFallingPathSum(m2));  // Expected: -1

        int[][] m3 = {{4, 3, 4}, {4, 5, 1}, {4, 6, 2}, {4, 1, 4}};
        System.out.println(solution.minFallingPathSum(m3));  // Expected: 7
    }
}
