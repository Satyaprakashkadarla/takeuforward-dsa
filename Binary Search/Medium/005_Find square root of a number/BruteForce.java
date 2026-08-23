/**
 * Problem: Find Square Root of a Number
 * Approach: Brute Force (Linear Scan)
 *
 * Idea:
 *  - Start checking from 1 upward: 1*1, 2*2, 3*3, ...
 *  - Keep going as long as i*i <= n.
 *  - The moment i*i exceeds n, the PREVIOUS value of i was the
 *    largest one whose square is still <= n - that's our answer
 *    (the floor of the square root).
 *  - Handle n = 0 as a special case (sqrt(0) = 0).
 *
 * Time Complexity:  O(sqrt(n)) -> we stop as soon as i exceeds sqrt(n)
 * Space Complexity: O(1)       -> no extra space used
 */
public class Bruteforce {

    public int floorSqrt(int n) {
        if (n == 0) return 0;

        int i = 1;
        while ((long) i * i <= n) {
            i++;
        }
        return i - 1;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.floorSqrt(36));  // Expected: 6
        System.out.println(solution.floorSqrt(28));  // Expected: 5
        System.out.println(solution.floorSqrt(50));  // Expected: 7
        System.out.println(solution.floorSqrt(0));   // Expected: 0
        System.out.println(solution.floorSqrt(1));   // Expected: 1
    }
}
