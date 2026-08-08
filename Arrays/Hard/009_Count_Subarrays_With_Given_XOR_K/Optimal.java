/*
Problem : Count Subarrays With Given XOR K

Approach : Prefix XOR + HashMap

Time Complexity : O(n)

Space Complexity : O(n)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public int subarraysWithXorK(int[] nums, int k) {

        HashMap<Integer, Integer> map = new HashMap<>();

        // Prefix XOR 0 occurs once before processing the array
        map.put(0, 1);

        int xor = 0;
        int count = 0;

        for (int num : nums) {

            xor ^= num;

            // Find the number of previous prefix XORs
            // that form XOR equal to k
            count += map.getOrDefault(xor ^ k, 0);

            // Store frequency of current prefix XOR
            map.put(xor, map.getOrDefault(xor, 0) + 1);
        }

        return count;
    }
}
