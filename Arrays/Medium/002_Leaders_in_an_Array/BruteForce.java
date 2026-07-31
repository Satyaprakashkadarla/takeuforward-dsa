/*
Problem : Leaders in an Array

Approach : Check Every Element

Time Complexity : O(n²)

Space Complexity : O(1) (excluding output)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public List<Integer> leaders(int[] nums) {

        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {

            boolean leader = true;

            for (int j = i + 1; j < nums.length; j++) {

                if (nums[j] >= nums[i]) {
                    leader = false;
                    break;
                }

            }

            if (leader) {
                ans.add(nums[i]);
            }

        }

        return ans;
    }
}
