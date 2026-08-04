/*
Problem : Grid Unique Paths

Approach : Combinatorial Mathematics

Time Complexity : O(min(m,n))

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int uniquePaths(int m, int n) {

        int N = m + n - 2;
        int r = m - 1;

        long result = 1;

        for (int i = 1; i <= r; i++) {

            result = result * (N - r + i) / i;

        }

        return (int) result;

    }

}
