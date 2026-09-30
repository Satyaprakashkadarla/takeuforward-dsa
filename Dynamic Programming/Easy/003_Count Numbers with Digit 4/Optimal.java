/**
 * Problem: Count Numbers with Digit 4
 * Approach: Optimal (as provided) - Digit Extraction via Modulo/Division
 *
 * Idea:
 *  - For every number i from 1 to n, repeatedly extract its last
 *    digit using `num % 10`, and strip that digit off using
 *    `num /= 10`, continuing until num becomes 0 (all digits checked).
 *  - The moment a digit equal to 4 is found, increment the count and
 *    break out of the inner loop early - no need to keep checking
 *    the remaining digits of this particular number.
 *  - This avoids the overhead of string conversion/allocation that
 *    the brute-force approach incurs, working with pure integer
 *    arithmetic instead.
 *
 * Note: This still checks every number from 1 to n individually -
 * it's "optimal" in the sense of avoiding unnecessary object
 * allocation per number, but it is NOT asymptotically better than
 * the brute force in terms of Big-O time complexity (both are
 * O(n * d)). See NOTES.md for a true sub-linear "Digit DP" approach
 * that computes the answer in O(d) time, independent of how large
 * n is.
 *
 * Time Complexity:  O(n * d) -> n numbers, each requiring up to d
 *                    digit extractions (d = number of digits in n)
 * Space Complexity: O(1)     -> no extra space used (pure arithmetic,
 *                    no string allocation)
 */
class Solution {
    public static int countNumberswith4(int n) {
        int count = 0;

        for (int i = 1; i <= n; i++) {
            int num = i;

            while (num > 0) {
                if (num % 10 == 4) {
                    count++;
                    break;
                }
                num /= 10;
            }
        }

        return count;
    }

    // Simple test driver
    public static void main(String[] args) {
        System.out.println(countNumberswith4(9));   // Expected: 1
        System.out.println(countNumberswith4(44));  // Expected: 9
    }
}
