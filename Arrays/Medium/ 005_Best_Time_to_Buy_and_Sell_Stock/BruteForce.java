/*
Problem : Best Time to Buy and Sell Stock

Approach : Check Every Buy-Sell Pair

Time Complexity : O(n²)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int stockBuySell(int[] arr, int n) {

        int maxProfit = 0;

        for (int i = 0; i < n - 1; i++) {

            for (int j = i + 1; j < n; j++) {

                maxProfit = Math.max(maxProfit, arr[j] - arr[i]);

            }

        }

        return maxProfit;
    }
}
