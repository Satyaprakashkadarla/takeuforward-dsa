/**
 * Problem: Max Min Height
 * Approach: Brute Force (Linear Scan Over the Answer, Explicit Window Updates)
 *
 * Idea:
 *  - The final minimum height is at least min(arr) (watering nothing
 *    already achieves that) and at most min(arr) + k (the shortest
 *    flower can grow by at most k).
 *  - Start at target = min(arr) and keep raising the target by 1 for
 *    as long as the next target is still achievable.
 *  - To test a target T, use the greedy sweep: go left to right, and
 *    whenever flower i is shorter than T, water the window that STARTS
 *    at i exactly (T - arr[i]) times. Do this by literally adding to
 *    every flower in the window. If the total waterings used exceeds
 *    k, the target is not achievable.
 *  - Starting the window at i is the best choice: the flowers to the
 *    left are already tall enough, so the window should reach as far
 *    right as possible.
 *
 * Time Complexity:  O(k * n * w) -> up to k + 1 targets, each check
 *                    sweeps n flowers and may update w flowers per step
 * Space Complexity: O(n)         -> a copy of the array for each check
 *
 * Far too slow for the real limits (n, k up to 10^5); it is a
 * correctness baseline for small inputs.
 */
public class Bruteforce {

    public int maxMinHeight(int[] arr, int k, int w) {
        int target = Integer.MAX_VALUE;
        for (int h : arr) {
            target = Math.min(target, h);
        }

        // Raise the target while the next one is still achievable
        while (canAchieve(arr, target + 1, k, w)) {
            target++;
        }

        return target;
    }

    private boolean canAchieve(int[] arr, int target, int k, int w) {
        int n = arr.length;
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = arr[i];
        }

        long ops = 0;

        for (int i = 0; i < n; i++) {
            if (a[i] < target) {
                long needed = target - a[i];
                ops += needed;

                if (ops > k) {
                    return false;
                }

                // Water the window starting at i, `needed` times
                for (int j = i; j < Math.min(n, i + w); j++) {
                    a[j] += needed;
                }
            }
        }

        return true;
    }

    // Simple test driver
    public static void main(String[] args) {
        Bruteforce solution = new Bruteforce();

        System.out.println(solution.maxMinHeight(new int[]{2, 3, 4, 5, 1}, 2, 2));  // Expected: 2
        System.out.println(solution.maxMinHeight(new int[]{5, 8}, 5, 1));           // Expected: 9
    }
}
