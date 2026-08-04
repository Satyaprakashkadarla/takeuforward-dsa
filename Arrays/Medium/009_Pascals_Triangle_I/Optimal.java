/*
Problem : Pascal's Triangle I

Approach : Binomial Coefficient

Time Complexity : O(min(c,r-c))

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int pascalTriangleI(int r, int c) {

        int n = r - 1;
        int k = c - 1;

        k = Math.min(k, n - k);

        long ans = 1;

        for (int i = 0; i < k; i++) {

            ans = ans * (n - i) / (i + 1);

        }

        return (int) ans;

    }

}
