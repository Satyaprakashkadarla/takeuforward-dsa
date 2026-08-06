/*
Problem : Majority Element-II

Approach : Extended Boyer-Moore Voting Algorithm

Time Complexity : O(n)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public List<Integer> majorityElementTwo(int[] nums) {

        int candidate1 = 0;
        int candidate2 = 0;

        int count1 = 0;
        int count2 = 0;

        for (int num : nums) {

            if (candidate1 == num) {

                count1++;

            } else if (candidate2 == num) {

                count2++;

            } else if (count1 == 0) {

                candidate1 = num;
                count1 = 1;

            } else if (count2 == 0) {

                candidate2 = num;
                count2 = 1;

            } else {

                count1--;
                count2--;

            }

        }

        count1 = 0;
        count2 = 0;

        for (int num : nums) {

            if (num == candidate1) {

                count1++;

            } else if (num == candidate2) {

                count2++;

            }

        }

        List<Integer> ans = new ArrayList<>();

        int limit = nums.length / 3;

        if (count1 > limit) {
            ans.add(candidate1);
        }

        if (count2 > limit) {
            ans.add(candidate2);
        }

        Collections.sort(ans);

        return ans;

    }

}
