/*
Problem : Pascal's Triangle I

Approach : Build Pascal Triangle

Time Complexity : O(r²)

Space Complexity : O(r²)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public int pascalTriangleI(int r, int c) {

        List<List<Integer>> triangle = new ArrayList<>();

        for (int i = 0; i < r; i++) {

            List<Integer> row = new ArrayList<>();

            for (int j = 0; j <= i; j++) {

                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(triangle.get(i - 1).get(j - 1)
                          + triangle.get(i - 1).get(j));
                }

            }

            triangle.add(row);

        }

        return triangle.get(r - 1).get(c - 1);

    }
}
