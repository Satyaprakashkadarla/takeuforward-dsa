/*
Problem : Next Permutation

Approach : Generate All Permutations

Time Complexity : O(n! × n)

Space Complexity : O(n!)

Author : Satya Prakash Kadarla
*/

import java.util.*;

class Solution {

    public void nextPermutation(int[] nums) {

        List<int[]> permutations = new ArrayList<>();

        generate(nums, 0, permutations);

        permutations.sort((a, b) -> {
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                    return a[i] - b[i];
                }
            }
            return 0;
        });

        int index = 0;

        for (int i = 0; i < permutations.size(); i++) {

            if (Arrays.equals(nums, permutations.get(i))) {
                index = (i + 1) % permutations.size();
                break;
            }

        }

        System.arraycopy(permutations.get(index), 0, nums, 0, nums.length);
    }

    private void generate(int[] nums, int start, List<int[]> list) {

        if (start == nums.length) {
            list.add(nums.clone());
            return;
        }

        for (int i = start; i < nums.length; i++) {

            swap(nums, start, i);
            generate(nums, start + 1, list);
            swap(nums, start, i);

        }

    }

    private void swap(int[] nums, int i, int j) {

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;

    }

}
