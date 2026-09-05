/**
 * Problem: Find Peak Element - II
 * Approach: Brute Force (Linear Scan of Every Cell)
 *
 * Idea:
 *  - Check every cell in the matrix against its four neighbors
 *    (left, right, top, bottom), treating any out-of-bounds neighbor
 *    as -1 (per the problem's border convention).
 *  - The first cell found where it's strictly greater than all four
 *    neighbors is a valid peak - return its coordinates immediately.
 *
 * Time Complexity:  O(n * m) -> in the worst case, checks every cell
 * Space Complexity: O(1)     -> no extra space used
 */
public class Bruteforce {

    public int[] findPeakGrid(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int current = mat[i][j];

                int up = (i > 0) ? mat[i - 1][j] : -1;
                int down = (i < n - 1) ? mat[i + 1][j] : -1;
                int left = (j > 0) ? mat[i][j - 1] : -1;
                int right = (j < m - 1) ? mat[i][j + 1] : -1;

                if (current > up && current > down && current > left && current > right) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{-1, -1}; // will not actually be reached given problem guarantees
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] mat1 = {{10, 20, 15}, {21, 30, 14}, {7, 16, 32}};
        System.out.println(java.util.Arrays.toString(solution.findPeakGrid(mat1)));  // Expected: [1,1] or [2,2]

        int[][] mat2 = {{10, 7}, {11, 17}};
        System.out.println(java.util.Arrays.toString(solution.findPeakGrid(mat2)));  // Expected: [1,1]

        int[][] mat3 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(java.util.Arrays.toString(solution.findPeakGrid(mat3)));  // Expected: [2,2]
    }
}
