/*
Problem : Left Rotate Array by One

Approach : In-place Rotation

Time Complexity : O(n)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public void rotateArrayByOne(int[] nums) {

        int first = nums[0];

        for (int i = 0; i < nums.length - 1; i++) {
            nums[i] = nums[i + 1];
        }

        nums[nums.length - 1] = first;
    }
}
