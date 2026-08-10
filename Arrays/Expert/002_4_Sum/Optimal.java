/*
 * Problem : 4 Sum
 *
 * Approach : Sorting + Two Pointers
 *
 * Time Complexity : O(n^3)
 * Space Complexity : O(1) auxiliary space
 *
 * Author : Satya Prakash Kadarla
 */

import java.util.*;

class Solution {

    public List<List<Integer>> fourSum(int[] nums, int target) {

        List<List<Integer>> ans = new ArrayList<>();

        int n = nums.length;

        // Sort the array
        Arrays.sort(nums);

        // Fix the first element
        for (int i = 0; i < n - 3; i++) {

            // Skip duplicate first elements
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // Fix the second element
            for (int j = i + 1; j < n - 2; j++) {

                // Skip duplicate second elements
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }

                int left = j + 1;
                int right = n - 1;

                // Two-pointer search
                while (left < right) {

                    long sum = (long) nums[i]
                            + nums[j]
                            + nums[left]
                            + nums[right];

                    if (sum == target) {

                        ans.add(Arrays.asList(
                                nums[i],
                                nums[j],
                                nums[left],
                                nums[right]
                        ));

                        left++;
                        right--;

                        // Skip duplicate third elements
                        while (left < right &&
                               nums[left] == nums[left - 1]) {
                            left++;
                        }

                        // Skip duplicate fourth elements
                        while (left < right &&
                               nums[right] == nums[right + 1]) {
                            right--;
                        }

                    } else if (sum < target) {

                        // Need a larger sum
                        left++;

                    } else {

                        // Need a smaller sum
                        right--;
                    }
                }
            }
        }

        return ans;
    }
}
