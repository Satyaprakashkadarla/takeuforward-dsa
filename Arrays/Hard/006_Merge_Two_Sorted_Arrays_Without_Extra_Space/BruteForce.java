/*
Problem : Merge Two Sorted Arrays Without Extra Space

Approach : Extra Temporary Array

Time Complexity : O(m+n)

Space Complexity : O(m+n)

Author : Satya Prakash Kadarla
*/

class Solution {

    public void merge(int[] nums1, int m, int[] nums2, int n) {

        int[] temp = new int[m + n];

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < m && j < n) {

            if (nums1[i] <= nums2[j]) {

                temp[k++] = nums1[i++];

            } else {

                temp[k++] = nums2[j++];

            }

        }

        while (i < m) {

            temp[k++] = nums1[i++];

        }

        while (j < n) {

            temp[k++] = nums2[j++];

        }

        for (int index = 0; index < m + n; index++) {

            nums1[index] = temp[index];

        }

    }

}
