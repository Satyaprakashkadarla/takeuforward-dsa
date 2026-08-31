/**
 * Problem: Find Row With Maximum 1's
 * Approach: Brute Force (Linear Scan of Every Cell)
 *
 * Idea:
 *  - For each row, count the number of 1s by scanning every cell.
 *  - Track the row with the highest count seen so far, using strict
 *    ">" (not ">=") when updating, so that in the event of a tie,
 *    the FIRST (smallest index) row with that count is kept.
 *  - If no row ever has a count > 0, no 1s exist anywhere -> return -1.
 *
 * Note: since each row is individually sorted, a row-by-row binary
 * search (finding the first index of "1" via lower bound, then
 * computing count = m - index) would bring this down to O(n log m).
 * This brute force version simply scans every cell for maximum
 * simplicity and clarity as a baseline.
 *
 * Time Complexity:  O(n * m) -> scans every cell in the matrix
 * Space Complexity: O(1)     -> no extra space used
 */
public class Bruteforce {

    public int rowWithMax1s(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;

        int maxCount = 0;
        int maxRow = -1;

        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == 1) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                maxRow = i;
            }
        }

        return maxRow;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] mat1 = {{1, 1, 1}, {0, 0, 1}, {0, 0, 0}};
        System.out.println(solution.rowWithMax1s(mat1));  // Expected: 0

        int[][] mat2 = {{0, 0}, {0, 0}};
        System.out.println(solution.rowWithMax1s(mat2));  // Expected: -1

        int[][] mat3 = {{0, 0, 1}, {0, 1, 1}, {0, 1, 1}};
        System.out.println(solution.rowWithMax1s(mat3));  // Expected: 1
    }
}
