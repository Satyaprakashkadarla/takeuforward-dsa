/**
 * Problem: Find Peak Element - II
 * Approach: Optimal - Binary Search on Columns
 *
 * Idea:
 *  - Generalizes the 1D "Find Peak Element" technique to 2D by
 *    binary searching over COLUMNS instead of individual array
 *    indices.
 *
 *  - For a candidate column `mid`:
 *      1. Find the row with the MAXIMUM value in that column
 *         (an O(n) scan down the column).
 *      2. Compare that maximum value to its immediate left and
 *         right neighbors (in the same row, adjacent columns),
 *         using -1 for out-of-bounds columns per the problem's
 *         border convention.
 *      3. If it's greater than BOTH neighbors, it's a valid peak
 *         (it's already the max in its own column, so it beats its
 *         up/down neighbors trivially too) - return its coordinates.
 *      4. Otherwise, move toward whichever neighboring column has
 *         the LARGER value - a peak is GUARANTEED to exist in that
 *         direction, by the same logic as the 1D "climb toward a
 *         peak" guarantee (bounded by the -1 border at the edges).
 *
 *  - This is analogous to 1D Find Peak Element, but each "step" of
 *    the binary search requires an O(n) column-max scan instead of
 *    an O(1) single comparison, giving O(n log m) overall instead
 *    of O(log n).
 *
 * Time Complexity:  O(n log m) -> O(log m) binary search iterations
 *                    over columns, each doing an O(n) column-max scan
 * Space Complexity: O(1) -> no extra space used
 */
class Solution {
    public int[] findPeakGrid(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;

        int left = 0, right = m - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Find maximum element in column mid
            int maxRow = 0;
            for (int i = 1; i < n; i++) {
                if (mat[i][mid] > mat[maxRow][mid]) {
                    maxRow = i;
                }
            }

            int curr = mat[maxRow][mid];

            int leftVal = (mid > 0) ? mat[maxRow][mid - 1] : -1;
            int rightVal = (mid < m - 1) ? mat[maxRow][mid + 1] : -1;

            // Current cell is a peak
            if (curr > leftVal && curr > rightVal) {
                return new int[]{maxRow, mid};
            }

            // Move toward the larger neighbor
            if (leftVal > curr) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return new int[]{-1, -1};
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[][] mat1 = {{10, 20, 15}, {21, 30, 14}, {7, 16, 32}};
        System.out.println(java.util.Arrays.toString(solution.findPeakGrid(mat1)));  // Expected: [1,1] or [2,2]

        int[][] mat2 = {{10, 7}, {11, 17}};
        System.out.println(java.util.Arrays.toString(solution.findPeakGrid(mat2)));  // Expected: [1,1]

        int[][] mat3 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(java.util.Arrays.toString(solution.findPeakGrid(mat3)));  // Expected: [2,2]
    }
}
