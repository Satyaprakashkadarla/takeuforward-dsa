/*
Problem : Find the Duplicate Number

Approach : Floyd's Cycle Detection Algorithm

Time Complexity : O(n)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int findDuplicate(int[] nums) {

        int tortoise = nums[0];
        int hare = nums[0];

        // Phase 1: Find the intersection point

        do {

            tortoise = nums[tortoise];
            hare = nums[nums[hare]];

        } while (tortoise != hare);

        // Phase 2: Find the entrance of the cycle

        tortoise = nums[0];

        while (tortoise != hare) {

            tortoise = nums[tortoise];
            hare = nums[hare];

        }

        return tortoise;
    }
}
