/**
 * Problem: Search in 2D Matrix - II
 * Approach: Brute Force (Linear Scan of Every Cell)
 *
 * Idea:
 *  - Simply check every cell in the matrix, row by row, column by
 *    column, comparing it against the target.
 *  - Return true the moment a match is found.
 *  - If we finish scanning without a match, return false.
 *  - This completely ignores the sorted structure of rows and
 *    columns, which is why it's not optimal.
 *
 * Note: Since each ROW is individually sorted, a slightly better
 * brute force would run a binary search on each row (O(log m)
 * per row), giving O(n log m) overall - a nice middle ground
 * between this version and the fully optimal staircase search.
 *
 * Time Complexity:  O(n * m) -> scans every cell in the matrix
 * Space Complexity: O(1)     -> no extra space used
 */
public class Bruteforce {

    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }

        return false;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] matrix = {
            {1, 4, 7, 11, 15},
            {2, 5, 8, 12, 19},
            {3, 6, 9, 16, 22},
            {10, 13, 14, 17, 24},
            {18, 21, 23, 26, 30}
        };

        System.out.println(solution.searchMatrix(matrix, 5));   // Expected: true
        System.out.println(solution.searchMatrix(matrix, 20));  // Expected: false
        System.out.println(solution.searchMatrix(matrix, 1));   // Expected: true
    }
}
