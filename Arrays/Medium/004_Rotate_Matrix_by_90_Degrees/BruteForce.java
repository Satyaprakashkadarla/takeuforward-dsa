/*
Problem : Rotate Matrix by 90 Degrees

Approach : Using Temporary Matrix

Time Complexity : O(n²)

Space Complexity : O(n²)

Author : Satya Prakash Kadarla
*/

class Solution {

    public void rotateMatrix(int[][] matrix) {

        int n = matrix.length;

        int[][] temp = new int[n][n];

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                temp[j][n - 1 - i] = matrix[i][j];

            }

        }

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                matrix[i][j] = temp[i][j];

            }

        }

    }
}
