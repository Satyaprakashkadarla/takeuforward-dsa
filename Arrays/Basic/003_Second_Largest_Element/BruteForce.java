/*
Problem : Second Largest Element

Approach : Sort the Array

Time Complexity : O(n log n)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

import java.util.Arrays;

class Solution {

    public int secondLargestElement(int[] nums) {

        Arrays.sort(nums);

        int largest = nums[nums.length - 1];

        for (int i = nums.length - 2; i >= 0; i--) {

            if (nums[i] != largest) {
                return nums[i];
            }

        }

        return -1;
    }
}
