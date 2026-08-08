/*
Problem : Count Subarrays With Given Sum

Approach : Prefix Sum + HashMap

Time Complexity : O(n)

Space Complexity : O(n)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public int subarraySum(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();

        // Prefix sum 0 occurs once before processing the array
        map.put(0, 1);

        int prefix = 0;
        int count = 0;

        for (int num : nums) {

            prefix += num;

            // Check how many previous prefix sums
            // can form a subarray with sum k
            count += map.getOrDefault(prefix - k, 0);

            // Store frequency of the current prefix sum
            map.put(prefix, map.getOrDefault(prefix, 0) + 1);
        }

        return count;
    }
}
