/*
Problem : Best Time to Buy and Sell Stock

Approach : Single Traversal

Time Complexity : O(n)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int stockBuySell(int[] arr, int n) {

        int minPrice = arr[0];
        int maxProfit = 0;

        for (int i = 1; i < n; i++) {

            if (arr[i] < minPrice) {

                minPrice = arr[i];

            } else {

                maxProfit = Math.max(maxProfit, arr[i] - minPrice);

            }

        }

        return maxProfit;
    }
}
