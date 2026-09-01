/**
 * Problem: Split Array - Largest Sum
 * Approach: Optimal - Binary Search on the Answer
 *
 * Idea:
 *  - The answer (minimized largest subarray sum) lies somewhere
 *    between:
 *      low  = the largest single element (a subarray must be able
 *             to hold that element alone, so the answer can never
 *             be smaller than this)
 *      high = the sum of the entire array (a single subarray
 *             containing everything, trivially satisfying k=1 or
 *             larger)
 *
 *  - As the candidate maxSum increases, the number of subarrays
 *    NEEDED to keep every subarray's sum <= maxSum DECREASES (or
 *    stays the same) - this monotonic relationship is what makes
 *    binary search applicable. This is the exact same "minimize the
 *    maximum" pattern as Capacity to Ship Packages Within D Days and
 *    Book Allocation Problem - just with different names for the
 *    same underlying structure.
 *
 *  - For a candidate maxSum = mid, isValid() greedily groups elements
 *    IN ORDER: keep adding elements to the current subarray's running
 *    sum as long as it doesn't exceed mid; the moment adding the next
 *    element WOULD exceed mid, start a new subarray with that element.
 *
 *  - If isValid(mid) is true, mid is a VALID candidate maxSum, so we
 *    record it as our best answer so far (ans = mid) and try an even
 *    SMALLER maxSum: high = mid - 1.
 *  - If isValid(mid) is false, mid is too small - we need a larger
 *    maxSum: low = mid + 1.
 *
 * Time Complexity:  O(n log(sum(a))) -> binary search does
 *                    O(log(sum(a))) iterations, each doing an O(n)
 *                    greedy check
 * Space Complexity: O(1) -> iterative, no extra space
 */
class Solution {
    public int largestSubarraySumMinimized(int[] a, int k) {
        int low = 0;
        int high = 0;
        
        for (int num : a) {
            low = Math.max(low, num);
            high += num;
        }
        
        int ans = high;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (isValid(a, k, mid)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return ans;
    }
    
    private boolean isValid(int[] a, int k, int maxSum) {
        int subarrays = 1;
        int currentSum = 0;
        
        for (int num : a) {
            if (currentSum + num <= maxSum) {
                currentSum += num;
            } else {
                subarrays++;
                currentSum = num;
            }
        }
        
        return subarrays <= k;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] a1 = {1, 2, 3, 4, 5};
        System.out.println(solution.largestSubarraySumMinimized(a1, 3));  // Expected: 6

        int[] a2 = {3, 5, 1};
        System.out.println(solution.largestSubarraySumMinimized(a2, 3));  // Expected: 5

        int[] a3 = {1, 2, 3, 4, 5};
        System.out.println(solution.largestSubarraySumMinimized(a3, 2));  // Expected: 9
    }
}
