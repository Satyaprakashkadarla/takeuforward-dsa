/**
 * Problem: Count Occurrences in a Sorted Array
 * Approach: Brute Force (Linear Search)
 *
 * Idea:
 *  - Scan the entire array once.
 *  - Every time we see an element equal to target, increment a counter.
 *  - Return the counter at the end.
 *
 * Time Complexity:  O(n)  -> single pass through the array
 * Space Complexity: O(1)  -> no extra space used
 */
public class Bruteforce {

    public int countOccurrences(int[] arr, int target) {
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                count++;
            }
        }

        return count;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] arr1 = {0, 0, 1, 1, 1, 2, 3};
        System.out.println(solution.countOccurrences(arr1, 1));  // Expected: 3

        int[] arr2 = {5, 5, 5, 5, 5, 5};
        System.out.println(solution.countOccurrences(arr2, 5));  // Expected: 6

        int[] arr3 = {2, 4, 6, 8, 10};
        System.out.println(solution.countOccurrences(arr3, 3));  // Expected: 0
    }
}
