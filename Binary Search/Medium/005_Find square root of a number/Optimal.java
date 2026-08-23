/**
 * Problem: Find Square Root of a Number
 * Approach: Optimal - Binary Search on the Answer
 *
 * Idea:
 *  - Instead of searching within an array, we binary search over the
 *    RANGE OF POSSIBLE ANSWERS: any candidate square root must lie
 *    somewhere between 1 and n (inclusive).
 *  - For a candidate mid, check whether mid*mid <= n:
 *      - If true, mid is a valid (possibly not the largest) square
 *        root candidate, so we record it as a potential answer and
 *        search further right (left = mid + 1) to see if there's an
 *        even larger valid candidate.
 *      - If false, mid is too large, so we search left (right = mid - 1).
 *  - We use "mid <= n / mid" instead of "mid * mid <= n" specifically
 *    to AVOID INTEGER OVERFLOW, since mid*mid could exceed the range
 *    of a 32-bit int when n is close to its maximum value (2^31 - 1).
 *  - When the loop ends (left > right), "right" holds the floor of
 *    the square root - because right is decremented past the last
 *    valid candidate, right always lands on the correct answer.
 *
 * Time Complexity:  O(log n) -> search space halves every iteration
 * Space Complexity: O(1)     -> iterative, no extra space
 */
class Solution {
    public int floorSqrt(int n) {
        if (n == 0) return 0;
        
        int left = 1, right = n;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Use division to avoid overflow
            if (mid <= n / mid) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return right;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.floorSqrt(36));  // Expected: 6
        System.out.println(solution.floorSqrt(28));  // Expected: 5
        System.out.println(solution.floorSqrt(50));  // Expected: 7
        System.out.println(solution.floorSqrt(0));   // Expected: 0
        System.out.println(solution.floorSqrt(1));   // Expected: 1
    }
}
