/**
 * Problem: Max Subarray Sum for Values Limited by K
 * Approach: Optimal - Single-Pass Running Sum
 *
 * Idea:
 *  - Scan the array once, maintaining a running sum of the CURRENT
 *    valid (all elements <= k) segment.
 *  - If the current element is <= k, it's valid to include: add it
 *    to currentSum, and update maxSum if this is the best segment
 *    sum seen so far.
 *  - If the current element is > k, it breaks any potential
 *    subarray spanning across it (since that element itself could
 *    never be included, and a subarray can't "skip over" it while
 *    staying contiguous) - so reset currentSum to 0, starting fresh
 *    for the next potential valid segment.
 *  - Since all array values are non-negative (0 <= arr[i]), extending
 *    a valid segment by one more valid element never decreases the
 *    sum - so simply accumulating within each valid segment and
 *    tracking the running maximum correctly finds the best valid
 *    subarray sum, without needing to separately verify "all
 *    elements <= k" for candidate ranges after the fact.
 *
 * Time Complexity:  O(n) -> single pass through the array
 * Space Complexity: O(1) -> only two variables tracked
 */
class Solution {
    public int maxSum(int[] arr, int k) {
        int currentSum = 0;
        int maxSum = 0;

        for (int num : arr) {
            if (num <= k) {
                currentSum += num;
                maxSum = Math.max(maxSum, currentSum);
            } else {
                currentSum = 0;
            }
        }

        return maxSum;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] arr1 = {3, 2, 2, 3, 1, 1, 1, 3};
        System.out.println(solution.maxSum(arr1, 1));  // Expected: 3

        int[] arr2 = {3, 2, 2, 3, 1, 1, 1, 3};
        System.out.println(solution.maxSum(arr2, 2));  // Expected: 4
    }
}
