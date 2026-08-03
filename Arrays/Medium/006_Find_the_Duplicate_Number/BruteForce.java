/*
Problem : Find the Duplicate Number

Approach : Compare Every Pair

Time Complexity : O(n²)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int findDuplicate(int[] nums) {

        for (int i = 0; i < nums.length; i++) {

            for (int j = i + 1; j < nums.length; j++) {

                if (nums[i] == nums[j]) {
                    return nums[i];
                }

            }

        }

        return -1;
    }
}
