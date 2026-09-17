/**
 * Problem: Triangle
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Define f(i, j) = minimum path sum from cell (i, j) down to the
 *    bottom (last) row of the triangle.
 *  - If we're already at the last row, f(i, j) = triangle[i][j]
 *    (nothing more to add).
 *  - Otherwise, from (i, j) we can move to (i+1, j) [bottom] or
 *    (i+1, j+1) [bottom-right], so:
 *        f(i, j) = triangle[i][j] + min(f(i+1, j), f(i+1, j+1))
 *  - The overall answer is f(0, 0), since the triangle always starts
 *    with a single element at the top.
 *  - Without memoization, this recomputes the same (i,j) states
 *    repeatedly across different paths, leading to exponential blowup.
 *
 * Time Complexity:  O(2^n) -> each call branches into 2 more calls,
 *                    with no caching of repeated (i,j) states
 * Space Complexity: O(n)   -> maximum recursion stack depth (n rows)
 */
public class Bruteforce {

    public int minTriangleSum(int[][] triangle) {
        return solve(triangle, 0, 0);
    }

    private int solve(int[][] triangle, int i, int j) {
        if (i == triangle.length - 1) {
            return triangle[i][j];
        }

        int down = solve(triangle, i + 1, j);
        int downRight = solve(triangle, i + 1, j + 1);

        return triangle[i][j] + Math.min(down, downRight);
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] t1 = {{1}, {1, 2}, {1, 2, 4}};
        System.out.println(solution.minTriangleSum(t1));  // Expected: 3

        int[][] t2 = {{1}, {4, 7}, {4, 10, 50}, {-50, 5, 6, -100}};
        System.out.println(solution.minTriangleSum(t2));  // Expected: -42

        int[][] t3 = {{3}, {-1, 3}, {-3, 2, 4}, {8, 8, 1, -4}};
        System.out.println(solution.minTriangleSum(t3));  // Expected: 5
    }
}
