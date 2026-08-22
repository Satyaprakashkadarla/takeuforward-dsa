/**
 * Problem: Find Peak Element
 * Approach: Brute Force (Linear Scan)
 *
 * Idea:
 *  - Scan through the array and check each element against its
 *    neighbors.
 *  - Treat out-of-bounds neighbors (before index 0, after index n-1)
 *    as -infinity, as specified by the problem.
 *  - The first index where arr[i] > left neighbor AND arr[i] > right
 *    neighbor is a valid peak - return it immediately.
 *
 * Time Complexity:  O(n)  -> in the worst case we check every element
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int findPeakElement(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            int left = (i == 0) ? Integer.MIN_VALUE : arr[i - 1];
            int right = (i == n - 1) ? Integer.MIN_VALUE : arr[i + 1];

            if (arr[i] > left && arr[i] > right) {
                return i;
            }
        }

        return -1; // will not actually be reached given problem guarantees
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 5, 1};
        System.out.println(solution.findPeakElement(arr1));  // Expected: 7

        int[] arr2 = {1, 2, 1, 3, 5, 6, 4};
        System.out.println(solution.findPeakElement(arr2));  // Expected: 1 (or any valid peak index)

        int[] arr3 = {-2, -1, 3, 4, 5};
        System.out.println(solution.findPeakElement(arr3));  // Expected: 4
    }
}
