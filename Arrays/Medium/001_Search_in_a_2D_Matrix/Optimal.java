/*
Problem : Search in a 2D Matrix

Approach : Binary Search

Time Complexity : O(log(m × n))

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public boolean searchMatrix(int[][] mat, int target) {

        int m = mat.length;
        int n = mat[0].length;

        int low = 0;
        int high = m * n - 1;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            int row = mid / n;
            int col = mid % n;

            if (mat[row][col] == target) {
                return true;
            } else if (mat[row][col] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }

        }

        return false;
    }
}
