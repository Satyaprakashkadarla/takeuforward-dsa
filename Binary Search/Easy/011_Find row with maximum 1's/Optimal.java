/**
 * Problem: Find Row With Maximum 1's
 * Approach: Optimal - Staircase Search
 *
 * Idea:
 *  - Since each row is individually sorted in ascending order (0s
 *    then 1s), the boundary between 0s and 1s across the whole
 *    matrix forms a "staircase" shape when viewed from the top-right
 *    corner.
 *
 *  - Start at the TOP-RIGHT corner of the matrix (row = 0, col = m-1).
 *      - If the current cell is 1: this row has AT LEAST this many
 *        1s up to this column, so record this row as our current
 *        best answer (it has more 1s than any row we've fully
 *        explored so far, or ties but comes first). Then move LEFT
 *        (col--) to check if this same row has even MORE 1s further
 *        left.
 *      - If the current cell is 0: this row can't have any more 1s
 *        at or before this column (since the row is sorted, 0s come
 *        before 1s), so move DOWN to the next row (row++) to check it.
 *
 *  - This traces the 0/1 boundary in a single sweep, visiting each
 *    row and column index at most once combined, rather than
 *    scanning the entire matrix.
 *
 *  - The strict "ans = row" update (not "if count > ans's count")
 *    naturally handles ties correctly: since we scan rows top to
 *    bottom and only move down when the current row is exhausted,
 *    the FIRST row (smallest index) that reaches the maximum count
 *    of 1s is the one left standing as `ans`, because a later row
 *    can only overwrite `ans` if it moves the col pointer further
 *    left than any previous row did (i.e., strictly more 1s).
 *
 *  - If no cell is ever 1, `ans` remains -1 (its initial value).
 *
 * Time Complexity:  O(n + m) -> row only increases, col only decreases,
 *                    so the total number of pointer movements is
 *                    bounded by n + m
 * Space Complexity: O(1) -> no extra space used
 */
class Solution {
    public int rowWithMax1s(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;

        int row = 0;
        int col = m - 1;
        int ans = -1;

        while (row < n && col >= 0) {
            if (mat[row][col] == 1) {
                ans = row;   // current row has more 1s
                col--;       // move left to find even more 1s
            } else {
                row++;       // move to next row
            }
        }

        return ans;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] mat1 = {{1, 1, 1}, {0, 0, 1}, {0, 0, 0}};
        System.out.println(solution.rowWithMax1s(mat1));  // Expected: 0

        int[][] mat2 = {{0, 0}, {0, 0}};
        System.out.println(solution.rowWithMax1s(mat2));  // Expected: -1

        int[][] mat3 = {{0, 0, 1}, {0, 1, 1}, {0, 1, 1}};
        System.out.println(solution.rowWithMax1s(mat3));  // Expected: 1
    }
}
