/**
 * Problem: Minimize Max Distance to Gas Station
 * Approach: Brute Force (Fixed-Step Linear Scan Over Real-Valued Distances)
 *
 * Idea:
 *  - Since the answer is a real number (not necessarily an integer),
 *    a true "brute force" here means scanning candidate distances in
 *    small fixed steps, rather than one-at-a-time integers.
 *  - The smallest possible max distance is theoretically 0 (though
 *    practically bounded by precision), and the largest is the
 *    biggest existing gap between adjacent stations (if k = 0, no
 *    new stations can be placed, so that's the answer).
 *  - Starting from a very small step size (e.g., decreasing from the
 *    max gap down to 0 in small increments), find the largest
 *    distance for which the greedy station-placement check succeeds
 *    with at most k stations.
 *  - NOTE: This approach is fundamentally imprecise and slow for
 *    real-valued answers - it's included here purely as an
 *    educational baseline showing why binary search (with a fixed
 *    iteration count) is essential for this class of problem. In
 *    practice, this brute force is NOT recommended for production
 *    use given its poor precision-to-time tradeoff.
 *
 * Time Complexity:  O(n / step) -> depends heavily on how fine a
 *                    step size is chosen; achieving 1e-6 precision
 *                    this way would be prohibitively slow
 * Space Complexity: O(1) -> no extra space used
 */
public class Bruteforce {

    public double minimiseMaxDistance(int[] arr, int k) {
        int n = arr.length;

        double maxGap = 0.0;
        for (int i = 1; i < n; i++) {
            maxGap = Math.max(maxGap, arr[i] - arr[i - 1]);
        }

        // A coarse step size, purely for demonstration - achieving
        // real precision this way is impractical (see NOTES.md).
        double step = 0.01;

        for (double candidate = maxGap; candidate > 0; candidate -= step) {
            if (canPlace(arr, k, candidate)) {
                // keep decreasing; return the last feasible one found
                // just before infeasibility (approximate)
                continue;
            } else {
                return candidate + step; // last feasible candidate
            }
        }

        return 0.0;
    }

    private boolean canPlace(int[] arr, int k, double maxDist) {
        int required = 0;

        for (int i = 1; i < arr.length; i++) {
            double gap = arr[i] - arr[i - 1];
            required += (int) Math.ceil(gap / maxDist) - 1;
            if (required > k) {
                return false;
            }
        }

        return true;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(solution.minimiseMaxDistance(arr1, 10));  // Expected: ~0.5

        int[] arr2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(solution.minimiseMaxDistance(arr2, 1));   // Expected: ~1.0

        int[] arr3 = {3, 6, 12, 19, 33, 44, 67, 72, 89, 95};
        System.out.println(solution.minimiseMaxDistance(arr3, 2));   // Expected: ~14.0
    }
}
