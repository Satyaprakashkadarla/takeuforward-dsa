/**
 * Problem: Unique Paths II
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Same recursive structure as the obstacle-free "Grid Unique
 *    Paths" problem, but with an added check: if the current cell
 *    is blocked (value 1), there are 0 paths through it - return 0
 *    immediately without exploring further.
 *  - f(i, j) = 0 if matrix[i][j] == 1
 *  - f(m-1, n-1) = 1 if it's not blocked (destination reached)
 *  - f(i, j) = f(i, j+1) + f(i+1, j) otherwise (sum of paths going
 *    right and going down)
 *  - Without memoization, this suffers the same exponential blowup
 *    as the obstacle-free version, from massively repeated (i,j)
 *    subproblem computation.
 *
 * Time Complexity:  O(2^(m+n)) -> exponential branching, no caching
 * Space Complexity: O(m + n)   -> maximum recursion stack depth
 */
public class Bruteforce {

    public int uniquePathsWithObstacles(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        return solve(matrix, 0, 0, m, n);
    }

    private int solve(int[][] matrix, int i, int j, int m, int n) {
        if (i >= m || j >= n || matrix[i][j] == 1) {
            return 0;
        }
        if (i == m - 1 && j == n - 1) {
            return 1;
        }

        int goRight = solve(matrix, i, j + 1, m, n);
        int goDown = solve(matrix, i + 1, j, m, n);

        return goRight + goDown;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] m1 = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        System.out.println(solution.uniquePathsWithObstacles(m1));  // Expected: 2

        int[][] m2 = {{0, 0, 0}, {0, 0, 1}, {0, 1, 0}};
        System.out.println(solution.uniquePathsWithObstacles(m2));  // Expected: 0

        int[][] m3 = {{0, 0, 0, 0}, {0, 0, 1, 0}};
        System.out.println(solution.uniquePathsWithObstacles(m3));  // Expected: 1
    }
}
