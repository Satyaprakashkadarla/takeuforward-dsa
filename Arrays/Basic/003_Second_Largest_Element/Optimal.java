/*
Problem : Second Largest Element

Approach : Single Traversal

Time Complexity : O(n)

Space Complexity : O(1)

Author : Satya Prakash Kadarla
*/

class Solution {

    public int secondLargestElement(int[] nums) {

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int num : nums) {

            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num != largest && num > secondLargest) {
                secondLargest = num;
            }

        }

        return secondLargest == Integer.MIN_VALUE ? -1 : secondLargest;
    }
}
