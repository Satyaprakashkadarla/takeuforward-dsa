/**
 * Problem: Median of 2 Sorted Arrays
 * Approach: Brute Force (Merge Two Sorted Arrays, Then Find the Middle)
 *
 * Idea:
 *  - Since both arrays are already individually sorted, merge them
 *    using the standard two-pointer merge technique (like the merge
 *    step of merge sort).
 *  - Once merged into a single sorted array of size (m + n), the
 *    median is straightforward:
 *      - If (m + n) is odd, the median is the single middle element.
 *      - If (m + n) is even, the median is the average of the two
 *        middle elements.
 *  - This approach completely ignores any opportunity to avoid
 *    fully merging the arrays, which is why it's not optimal - but
 *    it's simple, correct, and a good baseline.
 *
 * Time Complexity:  O(m + n) -> single pass to merge both arrays
 * Space Complexity: O(m + n) -> to store the merged array (can be
 *                    reduced to O(1) with careful two-pointer tracking
 *                    of just the needed middle elements, but a full
 *                    merge is shown here for clarity)
 */
public class Bruteforce {

    public double median(int[] arr1, int[] arr2) {
        int m = arr1.length;
        int n = arr2.length;
        int total = m + n;

        int[] merged = new int[total];
        int i = 0, j = 0, k = 0;

        while (i < m && j < n) {
            if (arr1[i] <= arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }
        while (i < m) {
            merged[k++] = arr1[i++];
        }
        while (j < n) {
            merged[k++] = arr2[j++];
        }

        if (total % 2 == 1) {
            return merged[total / 2];
        } else {
            return (merged[total / 2 - 1] + merged[total / 2]) / 2.0;
        }
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] arr1a = {2, 4, 6};
        int[] arr1b = {1, 3, 5};
        System.out.println(solution.median(arr1a, arr1b));  // Expected: 3.5

        int[] arr2a = {2, 4, 6};
        int[] arr2b = {1, 3};
        System.out.println(solution.median(arr2a, arr2b));  // Expected: 3.0

        int[] arr3a = {2, 4, 5};
        int[] arr3b = {1, 6};
        System.out.println(solution.median(arr3a, arr3b));  // Expected: 4.0
    }
}
