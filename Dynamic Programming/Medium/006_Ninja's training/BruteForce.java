/**
 * Problem: Ninja's Training
 * Approach: Brute Force (Plain Recursion)
 *
 * Idea:
 *  - Define f(day, lastActivity) = maximum points achievable from
 *    day 0 through `day`, given that `lastActivity` was used on the
 *    PREVIOUS day (so it cannot be repeated on `day`). Use lastActivity
 *    = 3 (a sentinel meaning "no restriction") for day 0.
 *  - On each day, try every activity EXCEPT the one used the
 *    previous day, adding that activity's points to the best result
 *    from the day before (with the newly chosen activity now
 *    becoming the "last activity" for the next recursive call).
 *  - f(day, last) = max over all activities `act != last` of
 *        matrix[day][act] + f(day-1, act)
 *  - Base case: f(0, last) = max points among activities != last on
 *    day 0 (or just the max of all 3 if last is the "none" sentinel).
 *  - Without memoization, this branches up to 3 ways at every level,
 *    with a huge amount of repeated (day, lastActivity) state
 *    recomputation across different call paths.
 *
 * Time Complexity:  O(3^n) -> up to 3 branches at each of n levels,
 *                    with no caching of repeated (day,last) states
 * Space Complexity: O(n)   -> maximum recursion stack depth
 */
public class Bruteforce {

    public int ninjaTraining(int[][] matrix) {
        int n = matrix.length;
        return solve(matrix, n - 1, 3); // 3 = "no activity done yet" sentinel
    }

    private int solve(int[][] matrix, int day, int last) {
        if (day == 0) {
            int best = 0;
            for (int act = 0; act < 3; act++) {
                if (act != last) {
                    best = Math.max(best, matrix[0][act]);
                }
            }
            return best;
        }

        int best = 0;
        for (int act = 0; act < 3; act++) {
            if (act != last) {
                int points = matrix[day][act] + solve(matrix, day - 1, act);
                best = Math.max(best, points);
            }
        }
        return best;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[][] m1 = {{10, 40, 70}, {20, 50, 80}, {30, 60, 90}};
        System.out.println(solution.ninjaTraining(m1));  // Expected: 210

        int[][] m2 = {{70, 40, 10}, {180, 20, 5}, {200, 60, 30}};
        System.out.println(solution.ninjaTraining(m2));  // Expected: 290

        int[][] m3 = {{20, 10, 10}, {20, 10, 10}, {20, 30, 10}};
        System.out.println(solution.ninjaTraining(m3));  // Expected: 60
    }
}
