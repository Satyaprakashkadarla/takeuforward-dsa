/**
 * Problem: Count Occurrences in a Sorted Array
 * Approach: Optimal - Binary Search (Two Passes)
 *
 * Idea:
 *  - Find the FIRST occurrence of target using a binary search that,
 *    whenever arr[mid] >= target, records the index (only if it's an
 *    exact match) and keeps narrowing left (right = mid - 1) to look
 *    for an even earlier occurrence.
 *  - If no first occurrence is found, target isn't in the array at
 *    all, so we can return 0 immediately.
 *  - Otherwise, find the LAST occurrence using a binary search that,
 *    whenever arr[mid] <= target, records the index (only if it's an
 *    exact match) and keeps narrowing right (left = mid + 1) to look
 *    for a later occurrence.
 *  - The total count of occurrences is simply (last - first + 1),
 *    since all occurrences of target form a contiguous block in a
 *    sorted array.
 *
 * Time Complexity:  O(log n) -> two independent binary searches, each O(log n)
 * Space Complexity: O(1)     -> iterative, no extra space
 */
class Solution {
    public int countOccurrences(int[] arr, int target) {
        // Find first occurrence
        int left = 0, right = arr.length - 1;
        int first = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] >= target) {
                if (arr[mid] == target) first = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        if (first == -1) return 0;
        
        // Find last occurrence
        left = 0;
        right = arr.length - 1;
        int last = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                if (arr[mid] == target) last = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return last - first + 1;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] arr1 = {0, 0, 1, 1, 1, 2, 3};
        System.out.println(solution.countOccurrences(arr1, 1));  // Expected: 3

        int[] arr2 = {5, 5, 5, 5, 5, 5};
        System.out.println(solution.countOccurrences(arr2, 5));  // Expected: 6

        int[] arr3 = {2, 4, 6, 8, 10};
        System.out.println(solution.countOccurrences(arr3, 3));  // Expected: 0
    }
}
