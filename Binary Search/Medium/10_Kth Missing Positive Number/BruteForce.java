/**
 * Problem: Kth Missing Positive Number
 * Approach: Brute Force (Linear Scan)
 *
 * Idea:
 *  - Walk through the array while also tracking the "next expected"
 *    positive integer (starting from 1), and a counter for missing
 *    numbers found so far.
 *  - At each array element, keep incrementing our "expected" pointer
 *    and counting it as missing until we either hit the actual array
 *    value or we've counted k missing numbers.
 *  - This effectively walks through the full sequence 1, 2, 3, ...
 *    alongside the array, identifying gaps as we go.
 *
 * Time Complexity:  O(n + k) in general, but bounded by O(n) here
 *                    since k <= 1000 and arr.length <= 1000 per
 *                    constraints - effectively O(n) for this problem's scale
 * Space Complexity: O(1) -> no extra space used
 */
public class Bruteforce {

    public int findKthPositive(int[] arr, int k) {
        int missingCount = 0;
        int current = 1;
        int i = 0;

        while (missingCount < k) {
            if (i < arr.length && arr[i] == current) {
                // current number IS in the array - not missing, move both forward
                i++;
            } else {
                // current number is missing
                missingCount++;
                if (missingCount == k) {
                    return current;
                }
            }
            current++;
        }

        return current - 1; // not actually reached given the loop structure above
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] arr1 = {3, 5, 7, 10};
        System.out.println(solution.findKthPositive(arr1, 6));  // Expected: 9

        int[] arr2 = {1, 4, 6, 8, 9};
        System.out.println(solution.findKthPositive(arr2, 3));  // Expected: 5

        int[] arr3 = {2, 3, 7, 11, 15};
        System.out.println(solution.findKthPositive(arr3, 5));  // Expected: 8
    }
}
