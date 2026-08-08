/*
Problem : Count Subarrays With Given Sum

Approach : Brute Force

Time Complexity : O(n²)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int subarraySum(int[] nums, int k) {

        int count = 0;

        for (int i = 0; i < nums.length; i++) {

            int sum = 0;

            for (int j = i; j < nums.length; j++) {

                sum += nums[j];

                if (sum == k) {
                    count++;
                }
            }
        }

        return count;
    }
}
