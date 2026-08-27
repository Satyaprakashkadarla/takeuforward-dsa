/**
 * Problem: Find the Smallest Divisor
 * Approach: Brute Force (Linear Scan Over Possible Divisors)
 *
 * Idea:
 *  - The smallest possible divisor is 1, and the largest useful
 *    divisor is the maximum value in nums (dividing by anything
 *    larger than the biggest element still just gives ceil(x/d) = 1
 *    for every element, so there's no benefit going higher).
 *  - Try every candidate divisor starting from 1 upward.
 *  - For each candidate, compute the sum of ceil(num / divisor) for
 *    every element.
 *  - The FIRST divisor for which this sum is <= limit is the answer,
 *    since increasing the divisor only ever decreases (or maintains)
 *    the sum, so the first one we find while scanning upward is
 *    guaranteed to be the smallest valid divisor.
 *
 * Time Complexity:  O(max(nums) * n) -> up to max(nums) candidate
 *                    divisors, each requiring an O(n) pass over nums
 * Space Complexity: O(1) -> no extra space used
 */
public class Bruteforce {

    public int smallestDivisor(int[] nums, int limit) {
        int maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
        }

        for (int divisor = 1; divisor <= maxVal; divisor++) {
            long sum = 0;
            for (int num : nums) {
                sum += (num + divisor - 1) / divisor; // ceil(num / divisor)
            }
            if (sum <= limit) {
                return divisor;
            }
        }

        return maxVal; // fallback (won't actually be reached given constraints)
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] nums1 = {1, 2, 3, 4, 5};
        System.out.println(solution.smallestDivisor(nums1, 8));  // Expected: 3

        int[] nums2 = {8, 4, 2, 3};
        System.out.println(solution.smallestDivisor(nums2, 10)); // Expected: 2

        int[] nums3 = {8, 4, 2, 3};
        System.out.println(solution.smallestDivisor(nums3, 4));  // Expected: 8
    }
}
