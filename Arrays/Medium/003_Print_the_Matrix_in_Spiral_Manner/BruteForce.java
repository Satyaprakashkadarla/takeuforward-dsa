/*
Problem : Print the Matrix in Spiral Manner

Approach : Visited Matrix Simulation

Time Complexity : O(m × n)

Space Complexity : O(m × n)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public List<Integer> spiralOrder(int[][] matrix) {

        List<Integer> ans = new ArrayList<>();

        int m = matrix.length;
        int n = matrix[0].length;

        boolean[][] visited = new boolean[m][n];

        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};

        int row = 0;
        int col = 0;
        int dir = 0;

        for (int i = 0; i < m * n; i++) {

            ans.add(matrix[row][col]);
            visited[row][col] = true;

            int newRow = row + dr[dir];
            int newCol = col + dc[dir];

            if (newRow >= 0 && newRow < m &&
                newCol >= 0 && newCol < n &&
                !visited[newRow][newCol]) {

                row = newRow;
                col = newCol;

            } else {

                dir = (dir + 1) % 4;

                row += dr[dir];
                col += dc[dir];
            }
        }

        return ans;
    }
}
