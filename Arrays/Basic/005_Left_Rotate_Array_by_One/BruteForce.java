/*
Problem : Left Rotate Array by One

Approach : Using Temporary Array

Time Complexity : O(n)

Space Complexity : O(n)

Author : Satya Prakash Kadarla
*/

class Solution {

    public void rotateArrayByOne(int[] nums) {

        int n = nums.length;

        int[] temp = new int[n];

        for (int i = 1; i < n; i++) {
            temp[i - 1] = nums[i];
        }

        temp[n - 1] = nums[0];

        for (int i = 0; i < n; i++) {
            nums[i] = temp[i];
        }
    }
}
