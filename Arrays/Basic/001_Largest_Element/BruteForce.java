/*
Problem : Largest Element in an Array

Approach : Sort the Array

Time Complexity : O(n log n)

Space Complexity : O(1) (Ignoring sorting recursion stack)

Author : Satya Prakash Kadarla
*/

import java.util.Arrays;

class Solution {

    public int largestElement(int[] nums) {

        Arrays.sort(nums);

        return nums[nums.length - 1];
    }
}
