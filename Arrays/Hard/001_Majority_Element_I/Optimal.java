/*
Problem : Majority Element-I

Approach : Boyer-Moore Voting Algorithm

Time Complexity : O(n)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int majorityElement(int[] nums) {

        int candidate = 0;
        int count = 0;

        for (int num : nums) {

            if (count == 0) {
                candidate = num;
            }

            count += (num == candidate) ? 1 : -1;

        }

        return candidate;

    }

}
