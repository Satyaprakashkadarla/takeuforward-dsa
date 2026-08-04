/*
Problem : Grid Unique Paths

Approach : Recursive Backtracking

Time Complexity : O(2^(m+n))

Space Complexity : O(m+n)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int uniquePaths(int m, int n) {

        return countPaths(0, 0, m, n);

    }

    private int countPaths(int row, int col, int m, int n) {

        if (row == m - 1 && col == n - 1) {
            return 1;
        }

        if (row >= m || col >= n) {
            return 0;
        }

        return countPaths(row + 1, col, m, n)
             + countPaths(row, col + 1, m, n);

    }

}
