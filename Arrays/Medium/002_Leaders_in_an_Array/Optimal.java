/*
Problem : Leaders in an Array

Approach : Traverse from Right

Time Complexity : O(n)

Space Complexity : O(n)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public List<Integer> leaders(int[] nums) {

        List<Integer> ans = new ArrayList<>();

        int maxRight = Integer.MIN_VALUE;

        for (int i = nums.length - 1; i >= 0; i--) {

            if (nums[i] > maxRight) {

                ans.add(nums[i]);
                maxRight = nums[i];

            }

        }

        Collections.reverse(ans);

        return ans;
    }
}
