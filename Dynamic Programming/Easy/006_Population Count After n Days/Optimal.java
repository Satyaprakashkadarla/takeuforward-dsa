/**
 * Problem: Population Count After N Days
 * Approach: Optimal - Simulation with a Size-6 Circular "Born" Buffer
 *
 * Idea:
 *  - Since every member lives for EXACTLY 6 days, at any point in
 *    time we only ever need to remember how many members were born
 *    on each of the LAST 6 days - anything older has either already
 *    died, or (if still alive) would have been captured within that
 *    6-day window anyway. This means a tiny fixed-size array of
 *    length 6 (indexed by day % 6) can replace what would otherwise
 *    need to be a growing, unbounded history array.
 *
 *  - `born[i]` holds the number of members born on the day that maps
 *    to slot `i` (via `(day - 1) % 6`). `total` tracks the current
 *    living population.
 *
 *  - On each new day:
 *      1. The members born exactly 6 days ago are now removed
 *         (their slot in `born` is about to be overwritten with
 *         TODAY's births, so we subtract their count from `total`
 *         first, since they no longer count as living).
 *      2. All remaining living members (`total`, after removal)
 *         each produce 2 new members - so `newBorn = 2 * total`.
 *      3. Record `newBorn` into today's slot in the `born` array
 *         (overwriting the 6-days-ago value we just subtracted out),
 *         and add it to `total` (the new members are now part of the
 *         living population too).
 *
 *  - The circular indexing `(day - 1) % 6` ensures that exactly 6
 *    days later, the same array slot gets reused - which is exactly
 *    when that slot's occupants are due to be removed, making this
 *    scheme self-consistent.
 *
 * Time Complexity:  O(n) -> a single pass from day 2 to day n, O(1)
 *                    work per day
 * Space Complexity: O(1) -> a fixed-size array of length 6, regardless of n
 */
class Solution {
    public int countPopulation(int n) {
        if (n == 1) return 1;

        // Array to store the number of members born in each of the last 6 days
        long[] born = new long[6];
        born[0] = 1;
        long total = 1;

        for (int day = 2; day <= n; day++) {
            int index = (day - 1) % 6;

            // Members born 6 days ago die before reproduction
            total -= born[index];

            // All remaining living members produce 2 new members each
            long newBorn = 2 * total;

            // Update the tracking array and total population
            born[index] = newBorn;
            total += newBorn;
        }

        return (int) total;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.countPopulation(2));  // Expected: 3
        System.out.println(solution.countPopulation(3));  // Expected: 9
        System.out.println(solution.countPopulation(7));  // Expected: 726
    }
}
