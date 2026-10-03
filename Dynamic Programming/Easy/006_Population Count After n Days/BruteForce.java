/**
 * Problem: Population Count After N Days
 * Approach: Brute Force (Simulation with a Full Birth-History Array)
 *
 * Idea:
 *  - Track exactly how many members were born on EACH day from day
 *    1 up to day n, using a full array indexed by day (rather than
 *    the size-6 circular buffer used in the optimal approach).
 *  - On each new day, the total population equals the sum of births
 *    from all days that are still "alive" - i.e., any birth day d
 *    such that d + 6 > currentDay (the member hasn't reached its
 *    6-day lifespan limit yet).
 *  - Each living member produces 2 new members on the new day, so
 *    newBirths[day] = 2 * (current living population).
 *  - This is less space-efficient than the optimal approach (storing
 *    the FULL history rather than just the last 6 days), but is a
 *    more direct, easy-to-verify translation of the problem
 *    statement.
 *
 * Time Complexity:  O(n) -> for each of n days, summing up to 6
 *                    recent birth-days is O(1) (bounded by the fixed
 *                    6-day lifespan), so this is still linear overall
 * Space Complexity: O(n) -> a full array storing births for every day
 */
public class Bruteforce {

    public int countPopulation(int n) {
        if (n == 1) return 1;

        long[] bornOnDay = new long[n + 1];
        bornOnDay[1] = 1;

        for (int day = 2; day <= n; day++) {
            long livingPopulation = 0;

            // Sum births from all days still within their 6-day lifespan
            for (int d = 1; d < day; d++) {
                if (d + 6 > day) { // still alive on this `day` (hasn't hit day d+6 yet)
                    livingPopulation += bornOnDay[d];
                }
            }

            bornOnDay[day] = 2 * livingPopulation;
        }

        // A member born on day d is still alive (present) on day n as
        // long as it hasn't reached its removal point: d + 6 > n.
        long total = 0;
        for (int d = 1; d <= n; d++) {
            if (d + 6 > n) {
                total += bornOnDay[d];
            }
        }

        return (int) total;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.countPopulation(2));  // Expected: 3
        System.out.println(solution.countPopulation(3));  // Expected: 9
        System.out.println(solution.countPopulation(7));  // Expected: 726
    }
}
