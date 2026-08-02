/*
Problem : Rearrange Array Elements by Sign

Approach : Separate Positive and Negative Lists

Time Complexity : O(n)

Space Complexity : O(n)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public int[] rearrangeArray(int[] nums) {

        int n = nums.length;

        List<Integer> positive = new ArrayList<>();
        List<Integer> negative = new ArrayList<>();

        for (int num : nums) {

            if (num > 0) {
                positive.add(num);
            } else {
                negative.add(num);
            }

        }

        int[] ans = new int[n];

        for (int i = 0; i < n / 2; i++) {

            ans[2 * i] = positive.get(i);
            ans[2 * i + 1] = negative.get(i);

        }

        return ans;
    }
}
