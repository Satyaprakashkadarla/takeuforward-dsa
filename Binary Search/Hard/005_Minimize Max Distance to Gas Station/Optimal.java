/**
 * Problem: Minimize Max Distance to Gas Station
 * Approach: Optimal - Binary Search on Real Numbers (Fixed Iteration Count)
 *
 * Idea:
 *  - Unlike integer binary-search-on-the-answer problems, the answer
 *    here can be ANY real number, so there's no natural "low == high,
 *    stop" integer convergence point. Instead, we run a FIXED number
 *    of iterations (100), which is more than enough to shrink the
 *    search interval to well below the required 1e-6 precision
 *    (each iteration halves the interval, so after 100 iterations
 *    the remaining interval width is astronomically smaller than
 *    any value that matters here).
 *
 *  - Search range:
 *      low  = 0.0 (theoretically, distances can be pushed arbitrarily
 *             close to 0 given enough new stations)
 *      high = the largest EXISTING gap between adjacent original
 *             stations (with 0 new stations, this gap can't be
 *             reduced; it also serves as a safe upper bound since
 *             adding stations only ever helps, never hurts)
 *
 *  - For a candidate maxDist = mid, canPlace() checks whether we can
 *    achieve this max distance using at most k new stations:
 *      - For each existing gap, compute how many NEW stations would
 *        be needed to split that gap so no resulting sub-gap exceeds
 *        maxDist: ceil(gap / maxDist) - 1 (since splitting a gap
 *        into `p` equal pieces requires `p - 1` new stations, and we
 *        need the smallest `p` such that gap/p <= maxDist, i.e.,
 *        p = ceil(gap / maxDist)).
 *      - Sum this across all gaps; if the total ever exceeds k,
 *        this maxDist is infeasible (early exit optimization).
 *
 *  - If canPlace(mid) is true, mid is achievable, so try an even
 *    SMALLER max distance: high = mid.
 *  - If canPlace(mid) is false, mid is too small (not enough
 *    stations to achieve it) - try a LARGER distance: low = mid.
 *
 *  - After 100 iterations, `high` (equivalently `low`, since they've
 *    converged to within 1e-6-scale of each other) is the answer.
 *
 * Time Complexity:  O(n * 100) -> effectively O(n log(1/precision)),
 *                    since 100 iterations is the fixed-point analog
 *                    of binary search depth for the required precision
 * Space Complexity: O(1) -> iterative, no extra space
 */
class Solution {
    public double minimiseMaxDistance(int[] arr, int k) {
        int n = arr.length;

        // Find the maximum existing gap
        double low = 0.0;
        double high = 0.0;

        for (int i = 1; i < n; i++) {
            high = Math.max(high, arr[i] - arr[i - 1]);
        }

        // 100 iterations are more than enough for 1e-6 precision
        for (int iteration = 0; iteration < 100; iteration++) {
            double mid = low + (high - low) / 2.0;

            if (canPlace(arr, k, mid)) {
                high = mid;   // Try for an even smaller maximum distance
            } else {
                low = mid;    // Need a larger distance
            }
        }

        return high;
    }

    private boolean canPlace(int[] arr, int k, double maxDist) {
        int required = 0;

        for (int i = 1; i < arr.length; i++) {
            double gap = arr[i] - arr[i - 1];

            // Number of stations required inside this gap
            required += (int) Math.ceil(gap / maxDist) - 1;

            if (required > k) {
                return false; // Early exit optimization
            }
        }

        return true;
    }

    // Simple test driver
    public static void main(String[] args) {
        Solution solution = new Solution();

        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(solution.minimiseMaxDistance(arr1, 10));  // Expected: ~0.5

        int[] arr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(solution.minimiseMaxDistance(arr2, 1));   // Expected: ~1.0

        int[] arr3 = {3, 6, 12, 19, 33, 44, 67, 72, 89, 95};
        System.out.println(solution.minimiseMaxDistance(arr3, 2));   // Expected: ~14.0
    }
}
