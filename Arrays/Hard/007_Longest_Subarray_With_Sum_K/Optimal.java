/*
Problem : Longest Subarray With Sum K

Approach : Prefix Sum + HashMap

Time Complexity : O(n)

Space Complexity : O(n)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public int longestSubarray(int[] nums, int k) {

        HashMap<Long, Integer> map = new HashMap<>();

        long prefixSum = 0;

        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {

            prefixSum += nums[i];

            if (prefixSum == k) {

                maxLength = i + 1;

            }

            if (map.containsKey(prefixSum - k)) {

                maxLength = Math.max(maxLength,
                        i - map.get(prefixSum - k));

            }

            map.putIfAbsent(prefixSum, i);

        }

        return maxLength;

    }

}
