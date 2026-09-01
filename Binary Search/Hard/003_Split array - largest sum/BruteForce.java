/**
 * Problem: Split Array - Largest Sum
 * Approach: Brute Force (Linear Scan Over Possible Sums)
 *
 * Idea:
 *  - The smallest possible "largest subarray sum" worth trying is
 *    the largest single element (a subarray must be able to hold
 *    that element alone, at minimum).
 *  - The largest sum worth trying is the sum of the entire array
 *    (a single subarray containing everything, i.e., k=1 case).
 *  - Try every candidate maxSum starting from the largest element
 *    upward, greedily checking how many subarrays would be needed
 *    to keep every subarray's sum at or below that candidate.
 *  - The FIRST candidate for which the required subarray count is
 *    <= k is the answer, since increasing the candidate sum only
 *    ever reduces (or maintains) the number of subarrays needed.
 *
 * Time Complexity:  O(sum(a) * n) -> up to sum(a) candidate sums,
 *                    each requiring an O(n) greedy check
 * Space Complexity: O(1) -> no extra space used
 */
public class Bruteforce {

    public int largestSubarraySumMinimized(int[] a, int k) {
        int maxElement = 0;
        int totalSum = 0;
        for (int num : a) {
            maxElement = Math.max(maxElement, num);
            totalSum += num;
        }

        for (int candidate = maxElement; candidate <= totalSum; candidate++) {
            if (isValid(a, k, candidate)) {
                return candidate;
            }
        }

        return totalSum; // fallback (won't actually be reached given constraints)
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
        Bruteforce solution = new Bruteforce();

        int[] a1 = {1, 2, 3, 4, 5};
        System.out.println(solution.largestSubarraySumMinimized(a1, 3));  // Expected: 6

        int[] a2 = {3, 5, 1};
        System.out.println(solution.largestSubarraySumMinimized(a2, 3));  // Expected: 5

        int[] a3 = {1, 2, 3, 4, 5};
        System.out.println(solution.largestSubarraySumMinimized(a3, 2));  // Expected: 9
    }
}
