/*
Problem : Kadane's Algorithm

Approach : Generate All Subarrays

Time Complexity : O(n²)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int maxSubArray(int[] nums) {

        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {

            int currentSum = 0;

            for (int j = i; j < nums.length; j++) {

                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);

            }

        }

        return maxSum;
    }
}
