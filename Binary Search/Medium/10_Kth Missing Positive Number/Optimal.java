/**
 * Problem: Kth Missing Positive Number
 * Approach: Optimal - Binary Search
 *
 * Idea:
 *  - For any index i in a strictly increasing array of positive
 *    integers, the number of missing positives up to and including
 *    arr[i] is exactly:
 *
 *        missing = arr[i] - (i + 1)
 *
 *    This works because if NO numbers were missing, arr[i] would
 *    have to equal (i + 1) exactly (1-indexed position). Any extra
 *    "gap" between arr[i] and (i + 1) represents missing numbers
 *    that were skipped before this point.
 *
 *  - This "missing" quantity is MONOTONICALLY NON-DECREASING as i
 *    increases (since arr is strictly increasing, the gaps can only
 *    grow or stay the same, never shrink) - this is what makes
 *    binary search applicable.
 *
 *  - Binary search for the smallest index where missing >= k:
 *      - If missing < k, we haven't found enough missing numbers
 *        yet by this point - search right: left = mid + 1.
 *      - Otherwise (missing >= k), this index has at least k missing
 *        numbers before it - search left: right = mid - 1.
 *
 *  - When the loop ends, `left` represents the number of array
 *    elements that come BEFORE the kth missing number. Since exactly
 *    `left` array elements are "used up" ahead of our target, the
 *    kth missing positive number is simply:
 *
 *        answer = left + k
 *
 *    (i.e., start counting from 1, skip past the `left` array
 *    elements that occupy some of those positions, and land on the
 *    kth missing one).
 *
 * Time Complexity:  O(log n) -> search space halves every iteration
 * Space Complexity: O(1)     -> iterative, no extra space
 */
class Solution {
    public static int findKthPositive(int[] arr, int k) {
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Missing numbers before/at arr[mid]
            int missing = arr[mid] - (mid + 1);

            if (missing < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // left = number of array elements before the kth missing number
        return left + k;
    }

    // Simple test driver
    public static void main(String[] args) {
        int[] arr1 = {3, 5, 7, 10};
        System.out.println(findKthPositive(arr1, 6));  // Expected: 9

        int[] arr2 = {1, 4, 6, 8, 9};
        System.out.println(findKthPositive(arr2, 3));  // Expected: 5

        int[] arr3 = {2, 3, 7, 11, 15};
        System.out.println(findKthPositive(arr3, 5));  // Expected: 8
    }
}
