/*
Problem : Majority Element-II

Approach : Count Frequency using Nested Loops

Time Complexity : O(n²)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public List<Integer> majorityElementTwo(int[] nums) {

        List<Integer> ans = new ArrayList<>();

        int limit = nums.length / 3;

        for (int i = 0; i < nums.length; i++) {

            if (ans.contains(nums[i])) {
                continue;
            }

            int count = 0;

            for (int j = 0; j < nums.length; j++) {

                if (nums[i] == nums[j]) {
                    count++;
                }

            }

            if (count > limit) {
                ans.add(nums[i]);
            }

        }

        Collections.sort(ans);

        return ans;

    }

}
