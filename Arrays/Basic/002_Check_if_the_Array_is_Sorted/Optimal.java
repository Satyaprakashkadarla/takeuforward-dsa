/*
Problem : Check if the Array is Sorted

Approach : Single Traversal

Time Complexity : O(n)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public boolean isSorted(int[] nums) {

        for (int i = 0; i < nums.length - 1; i++) {

            if (nums[i] > nums[i + 1]) {
                return false;
            }

        }

        return true;
    }
}
