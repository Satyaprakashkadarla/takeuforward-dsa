/**
 * Problem: Search in 2D Matrix - II
 * Approach: Optimal - Staircase Search
 *
 * Idea:
 *  - Because BOTH rows (left-to-right) AND columns (top-to-bottom)
 *    are sorted ascending, starting from the TOP-RIGHT corner gives
 *    a uniquely useful vantage point:
 *      - Everything in the SAME ROW to the left is SMALLER.
 *      - Everything in the SAME COLUMN below is LARGER.
 *
 *  - At each step, compare the current cell to the target:
 *      - If equal: found it, return true.
 *      - If current > target: the target can't be in this column
 *        anywhere below the current row (since column values only
 *        increase going down), and it also can't be at this exact
 *        cell - so eliminate this entire column by moving LEFT
 *        (col--). It COULD still be somewhere in this same row to
 *        the left, or in the columns to the left in other rows.
 *      - If current < target: the target can't be in this row
 *        anywhere to the left (since row values only increase going
 *        right), so eliminate this entire row by moving DOWN
 *        (row++). It could still be in a later row.
 *
 *  - Each move either eliminates a column or a row entirely, so the
 *    total number of moves is bounded by rows + cols, rather than
 *    rows * cols.
 *
 *  - If we ever move off the matrix bounds (row >= rows or col < 0)
 *    without finding the target, it doesn't exist -> return false.
 *
 * Time Complexity:  O(n + m) -> row only ever increases (up to n
 *                    times), col only ever decreases (up to m times)
 * Space Complexity: O(1)     -> no extra space used
 */
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int row = 0;
        int col = cols - 1;

        while (row < rows && col >= 0) {
            int current = matrix[row][col];

            if (current == target) {
                return true;
            } else if (current > target) {
                col--;       // move left
            } else {
                row++;       // move down
            }
        }

        return false;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

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
