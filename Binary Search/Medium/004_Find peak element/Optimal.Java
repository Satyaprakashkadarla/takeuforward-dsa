/**
 * Problem: Find Peak Element
 * Approach: Optimal - Binary Search on the Slope
 *
 * Idea:
 *  - We don't need a sorted array for binary search to work here -
 *    we just need a property that lets us discard half the search
 *    space at every step. That property is:
 *
 *      If arr[mid] < arr[mid+1], the array is "climbing" at mid, so
 *      a peak is GUARANTEED to exist somewhere to the right of mid
 *      (worst case, the peak is the last element, since the right
 *      boundary is treated as -infinity).
 *
 *      If arr[mid] > arr[mid+1], the array is "descending" at mid,
 *      so a peak is GUARANTEED to exist at mid itself or somewhere
 *      to its left (worst case, the peak is the first element, since
 *      the left boundary is treated as -infinity).
 *
 *  - Using this, we binary search over [low, high] = [0, n-1]:
 *      - If arr[mid] < arr[mid+1]: climb right -> low = mid + 1.
 *      - Else: peak is at mid or to the left -> high = mid.
 *  - When low == high, that index holds a valid peak.
 *
 *  - Note: since arr[i] != arr[i+1] is guaranteed, we never need to
 *    handle the "equal" case - every comparison is strictly < or >.
 *
 * Time Complexity:  O(log n) -> search space halves every iteration
 * Space Complexity: O(1)     -> iterative, no extra space
 */
class Solution {
    public int findPeakElement(int[] arr) {
        int low = 0, high = arr.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] < arr[mid + 1]) {
                // still climbing - peak must be to the right
                low = mid + 1;
            } else {
                // descending (or at a peak) - peak is at mid or to the left
                high = mid;
            }
        }

        return low; // low == high, pointing at a valid peak index
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 5, 1};
        System.out.println(solution.findPeakElement(arr1));  // Expected: 7

        int[] arr2 = {1, 2, 1, 3, 5, 6, 4};
        System.out.println(solution.findPeakElement(arr2));  // Expected: 1 or 5 (any valid peak)

        int[] arr3 = {-2, -1, 3, 4, 5};
        System.out.println(solution.findPeakElement(arr3));  // Expected: 4
    }
}
