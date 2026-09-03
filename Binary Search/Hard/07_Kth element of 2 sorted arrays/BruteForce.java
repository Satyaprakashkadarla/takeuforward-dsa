/**
 * Problem: Kth Element of 2 Sorted Arrays
 * Approach: Brute Force (Merge Two Sorted Arrays, Then Index)
 *
 * Idea:
 *  - Merge both sorted arrays using the standard two-pointer merge
 *    technique (as in merge sort's merge step).
 *  - Once merged, the kth element (1-indexed) is simply the value at
 *    index (k - 1) in the merged array.
 *  - We can stop merging early the moment we've produced k elements,
 *    avoiding unnecessary work beyond what's needed.
 *
 * Time Complexity:  O(k) in the best case (stopping early), or
 *                    O(m + n) in the worst case if k is large -
 *                    either way, bounded by O(m + n)
 * Space Complexity: O(1) -> using two pointers and a counter, no
 *                    need to store the full merged array
 */
public class Bruteforce {

    public int kthElement(int[] a, int[] b, int k) {
        int m = a.length;
        int n = b.length;

        int i = 0, j = 0;
        int count = 0;
        int result = -1;

        while (i < m && j < n) {
            int current;
            if (a[i] <= b[j]) {
                current = a[i++];
            } else {
                current = b[j++];
            }
            count++;
            if (count == k) {
                return current;
            }
        }

        while (i < m) {
            count++;
            result = a[i++];
            if (count == k) {
                return result;
            }
        }

        while (j < n) {
            count++;
            result = b[j++];
            if (count == k) {
                return result;
            }
        }

        return -1; // Should never reach here for valid input (k <= m+n)
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] a1 = {2, 3, 6, 7, 9};
        int[] b1 = {1, 4, 8, 10};
        System.out.println(solution.kthElement(a1, b1, 5));  // Expected: 6

        int[] a2 = {100, 112, 256, 349, 770};
        int[] b2 = {72, 86, 113, 119, 265, 445, 892};
        System.out.println(solution.kthElement(a2, b2, 7));  // Expected: 256

        int[] a3 = {2, 3, 6};
        int[] b3 = {7, 9};
        System.out.println(solution.kthElement(a3, b3, 4));  // Expected: 7
    }
}
