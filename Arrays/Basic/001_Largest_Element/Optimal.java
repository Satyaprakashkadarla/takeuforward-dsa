/*
Problem : Largest Element in an Array

Approach : Linear Traversal

Time Complexity : O(n)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {
    public int largestElement(int[] nums) {
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }

        return max;
    }
}
