/*
Problem : Majority Element-I

Approach : Count Frequency using Nested Loops

Time Complexity : O(n²)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int majorityElement(int[] nums) {

        int n = nums.length;

        for (int i = 0; i < n; i++) {

            int count = 0;

            for (int j = 0; j < n; j++) {

                if (nums[i] == nums[j]) {
                    count++;
                }

            }

            if (count > n / 2) {
                return nums[i];
            }

        }

        return -1;

    }

}
