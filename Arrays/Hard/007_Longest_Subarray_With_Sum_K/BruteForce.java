/*
Problem : Longest Subarray With Sum K

Approach : Generate All Subarrays

Time Complexity : O(n²)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int longestSubarray(int[] nums, int k) {

        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {

            long sum = 0;

            for (int j = i; j < nums.length; j++) {

                sum += nums[j];

                if (sum == k) {

                    maxLength = Math.max(maxLength, j - i + 1);

                }

            }

        }

        return maxLength;

    }

}
