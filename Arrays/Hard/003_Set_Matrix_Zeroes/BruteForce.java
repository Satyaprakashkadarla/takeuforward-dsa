/*
Problem : Set Matrix Zeroes

Approach : Extra Row and Column Arrays

Time Complexity : O(m × n)

Space Complexity : O(m + n)

Author : Satya Prakash Kadarla
*/

class Solution {

    public void setZeroes(int[][] matrix) {

        int m = matrix.length;
        int n = matrix[0].length;

        boolean[] rows = new boolean[m];
        boolean[] cols = new boolean[n];

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                if (matrix[i][j] == 0) {

                    rows[i] = true;
                    cols[j] = true;

                }

            }

        }

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                if (rows[i] || cols[j]) {

                    matrix[i][j] = 0;

                }

            }

        }

    }

}
