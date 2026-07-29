/*
Problem : Linear Search

Approach : Single Traversal

Time Complexity : O(n)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int linearSearch(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {

            if (nums[i] == target) {
                return i;
            }

        }

        return -1;
    }
}
